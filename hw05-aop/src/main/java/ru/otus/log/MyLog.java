package ru.otus.log;

import ru.otus.log.error.CreateInstanceException;
import ru.otus.log.handler.LogHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyLog<T> {

    public MyLog() {

    }

    public T create(T t) {
        try {
            InvocationHandler handler = new LogHandler<>(t);
            return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), handler);
        } catch (CreateInstanceException e) {
            e.printStackTrace();
            return null;
        }
    }

}
