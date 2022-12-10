package ru.otus.crm.service;

import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;

import java.util.Arrays;

import static ru.otus.demo.DbServiceDemo.HIBERNATE_CFG_FILE;

public class DbServiceFactory {

    public static DbService create(Class mainClass, Class<?>... classes) {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        var migrationsExecutor = new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword);

        migrationsExecutor.executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, getClasses(mainClass, classes));

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(mainClass);
        return new DbServiceImpl<>(transactionManager, clientTemplate);
    }


    private static Class<?>[] getClasses(Class<?> mainClass, Class<?>... classes) {
        Class<?>[] result;
        if (classes != null && classes.length > 0) {
            result = Arrays.asList(mainClass, classes).toArray(Class[]::new);
        } else {
            result = new Class[]{mainClass};
        }

        return result;
    }
}
