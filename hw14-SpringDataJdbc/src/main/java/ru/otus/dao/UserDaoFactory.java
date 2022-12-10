package ru.otus.dao;

import ru.otus.crm.model.User;
import ru.otus.crm.service.DbService;
import ru.otus.crm.service.DbServiceFactory;
import ru.otus.crm.service.DbServiceUser;
import ru.otus.crm.service.DbServiceUserImpl;

public class UserDaoFactory {

    public enum Type {
        InMemory,
        DataBase
    }

    public static UserDao create(Type type) {
        UserDao userDao = null;
        switch (type) {
            case DataBase -> userDao = createDbUserDao();
            case InMemory -> userDao = new InMemoryUserDao();
        }
        return userDao;
    }

    private static UserDao createDbUserDao() {
        DbService<User> dbService = DbServiceFactory.create(User.class);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(dbService);
        return new DbUserDao(dbServiceUser);
    }
}
