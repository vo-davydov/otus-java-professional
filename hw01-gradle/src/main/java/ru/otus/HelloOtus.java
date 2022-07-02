package ru.otus;

import static com.google.common.base.MoreObjects.firstNonNull;

public class HelloOtus {

    public static Object doSomething(Object first, Object second) {
        return firstNonNull(first, second);
    }
}
