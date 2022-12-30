package ru.otus.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.dto.UserDto;
import ru.otus.services.UserService;

@Controller
public class UserResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/{id}")
    @ResponseBody
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserDtoById(Long.valueOf(id));
    }

    @RequestMapping(path = "/users")
    public String users(Model model) {
        var optUser = userService.findRandomUser();
        model.addAttribute("user", optUser.get());
        LOGGER.info("Model map: " + model.asMap());
        return "users";
    }
}
