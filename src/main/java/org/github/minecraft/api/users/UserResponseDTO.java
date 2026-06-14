package org.github.minecraft.api.users;

public record UserResponseDTO(
    String nick,
    String uuid,
    boolean premium,
    String skinUrl,
    String skinModel,
    String signature,
    String capeUrl
) {
    public UserResponseDTO(Users users) {
        this(
            users.getNick(),
            users.getUuid(),
            users.isPremium(),
            users.getSkinUrl(),
            users.getSkinModel(),
            users.getSignature(),
            users.getCapeUrl()
        );
    }
}
