package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DbService;
import ru.otus.crm.service.DbServiceFactory;
import ru.otus.crm.service.DbServiceUser;
import ru.otus.crm.service.DbServiceUserImpl;
import ru.otus.dao.DbUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerSimple;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        DbService<User> dbService = DbServiceFactory.create(User.class);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(dbService);
        UserDao userDao = new DbUserDao(dbServiceUser);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT, userDao,
                gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
