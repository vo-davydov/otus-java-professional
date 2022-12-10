package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.dto.UserDto;
import ru.otus.crm.model.User;
import ru.otus.dao.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class AdminApiServlet extends HttpServlet {

    private final UserDao userDao;
    private final Gson gson;

    public AdminApiServlet(UserDao userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userDao.findAll();

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(users));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        var userDto = gson.fromJson(sb.toString(), UserDto.class);
        userDao.save(userDto);
    }

}
