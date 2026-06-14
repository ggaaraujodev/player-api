package org.github.minecraft.api.users;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MOJANG_UUID_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String MOJANG_PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final Duration CACHE_TTL = Duration.ofMinutes(5);

    private record CachedEntry(UserResponseDTO data, Instant cachedAt) {}
    private final Map<String, CachedEntry> cache = new ConcurrentHashMap<>();

    public Optional<UserResponseDTO> findByNick(String nick) {
        String key = nick.toLowerCase();

        CachedEntry cached = cache.get(key);
        if (cached != null && Duration.between(cached.cachedAt(), Instant.now()).compareTo(CACHE_TTL) < 0) {
            return Optional.of(cached.data());
        }

        try {
            HttpRequest uuidRequest = HttpRequest.newBuilder()
                .uri(URI.create(MOJANG_UUID_URL + nick))
                .GET()
                .build();

            HttpResponse<String> uuidResponse = httpClient.send(uuidRequest, HttpResponse.BodyHandlers.ofString());
            if (uuidResponse.statusCode() != 200) return Optional.empty();

            JsonNode uuidJson = objectMapper.readTree(uuidResponse.body());
            String rawUuid = uuidJson.get("id").asText();

            String uuid = rawUuid.replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5"
            );

            HttpRequest profileRequest = HttpRequest.newBuilder()
                .uri(URI.create(MOJANG_PROFILE_URL + rawUuid + "?unsigned=false"))
                .GET()
                .build();

            HttpResponse<String> profileResponse = httpClient.send(profileRequest, HttpResponse.BodyHandlers.ofString());
            if (profileResponse.statusCode() != 200) return Optional.empty();

            JsonNode profileJson = objectMapper.readTree(profileResponse.body());
            JsonNode property = profileJson.get("properties").get(0);

            String valueB64 = property.get("value").asText();
            String signature = property.get("signature").asText();

            String decoded = new String(Base64.getDecoder().decode(valueB64));
            JsonNode textures = objectMapper.readTree(decoded).get("textures");

            String skinUrl = null;
            String skinModel = "classic";
            String capeUrl = null;

            if (textures.has("SKIN")) {
                JsonNode skinNode = textures.get("SKIN");
                skinUrl = skinNode.get("url").asText();
                if (skinNode.has("metadata") && skinNode.get("metadata").has("model")) {
                    skinModel = skinNode.get("metadata").get("model").asText(); // "slim"
                }
            }

            if (textures.has("CAPE")) {
                capeUrl = textures.get("CAPE").get("url").asText();
            }

            Users user = new Users(nick, uuid, true, skinUrl, skinModel, signature, capeUrl);
            UserResponseDTO dto = new UserResponseDTO(user);

            cache.put(key, new CachedEntry(dto, Instant.now()));
            return Optional.of(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
