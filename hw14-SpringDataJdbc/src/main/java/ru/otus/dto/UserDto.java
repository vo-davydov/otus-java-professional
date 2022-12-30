package ru.otus.dto;

public class UserDto {
    private String login;
    private String name;
    private String password;

    private Long id;

    public UserDto() {

    }

    public UserDto(Long id, String name, String login, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
