package org.github.minecraft.api.users;


public record UserResponseDTO(String data, String nick) {
    public UserResponseDTO(Users users){
        this(users.getNick(), users.getData());
    }
}