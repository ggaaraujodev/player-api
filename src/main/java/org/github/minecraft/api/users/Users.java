package org.github.minecraft.api.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nick")
public class Users {
    private String nick;
    private String uuid;
    private boolean premium;
    private String skinUrl;
    private String skinModel;
    private String signature;
    private String capeUrl;
}
