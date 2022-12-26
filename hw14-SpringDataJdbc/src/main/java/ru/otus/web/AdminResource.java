package ru.otus.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.dto.UserDto;
import ru.otus.services.UserService;

import java.util.List;

@Controller
public class AdminResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    private final UserService userService;

    public AdminResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/admin/")
    @ResponseBody
    public List<UserDto> getUsers() {
        return userService.findAll()
                .stream()
                .map(u -> new UserDto(u.getId(), u.getName(), u.getLogin(), u.getPassword()))
                .toList();
    }

    @GetMapping("/api/admin/{id}")
    @ResponseBody
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserDtoById(Long.valueOf(id));
    }

    @PostMapping("/api/admin/")
    @ResponseBody
    public void saveUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @RequestMapping(path = "/admin")
    public String admin(Model model) {
        return "admin";
    }
}
