package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user")
public class User {

    private Long id;

    private String login;

    private String password;

    private String name;

    @Override
    public User clone() {
        return this;
    }
}
