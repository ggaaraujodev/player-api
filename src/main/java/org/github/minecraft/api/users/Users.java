package org.github.minecraft.api.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "accounts")
@Entity(name = "accounts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nick")
public class Users {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id @Column(name = "nick")
    private String nick;

    @Column(name = "data")
    private String data;

    public Users(UserRequestDTO data){
        this.data = data.data();
    }
}
