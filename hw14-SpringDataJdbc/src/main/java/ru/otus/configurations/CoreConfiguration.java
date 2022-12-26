package ru.otus.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cache.Cache;
import ru.otus.cache.LocalWeakRefCache;
import ru.otus.model.User;

@Configuration
public class CoreConfiguration {

    @Bean
    @Qualifier("userCache")
    public Cache<Long, User> cache() {
        return new LocalWeakRefCache<>();
    }

}
