package ru.otus.log.handler;

import ru.otus.log.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class LogHandler<T> implements InvocationHandler {

    private final Logger logger = Logger.getLogger(LogHandler.class.getName());
    private final T t;

    private final List<Method> logMethods;

    public LogHandler(T t) {
        this.t = t;
        logMethods = getAnnotatedMethods(t.getClass(), Log.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (hasAnnotation(method)) {
            logger.info("Executed method: " + method.getName() + ", params: " + getReadableArgs(args));
        }
        return method.invoke(t, args);
    }

    private List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(annotation)).toList();
    }

    private boolean hasAnnotation(Method method) {
        for (var m : logMethods) {
            if (isMethodSignatureEquals(method, m)) {
                return true;
            }
        }

        return false;
    }

    private boolean isMethodSignatureEquals(Method m1, Method m2) {
        return isParameterCountEquals(m1, m2) && isNameEquals(m1, m2) && isParameterTypesEquals(m1, m2);
    }

    private boolean isParameterCountEquals(Method m1, Method m2) {
        return m1.getParameterCount() == m2.getParameterCount();
    }

    private boolean isNameEquals(Method m1, Method m2) {
        return m1.getName().equals(m2.getName());
    }

    private boolean isParameterTypesEquals(Method m1, Method m2) {
        Class<?>[] m1Params = m1.getParameterTypes();
        Class<?>[] m2Params = m2.getParameterTypes();

        for (int i = 0; i < m1Params.length; i++) {
            if (!m1Params[i].equals(m2Params[i])) {
                return false;
            }
        }

        return true;
    }


    private String getReadableArgs(Object[] args) {
        StringBuilder sb = new StringBuilder();

        for (var obj : args) {
            sb.append(getReadableValue(obj));
            sb.append(" ");
        }

        return sb.toString();
    }

    private String getReadableValue(Object obj) {
        if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (obj instanceof Object[]) {
            return Arrays.toString((Object[]) obj);
        }

        return obj.toString();
    }

}
