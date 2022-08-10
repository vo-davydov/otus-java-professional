package ru.otus.mytest;

import ru.otus.mytest.annotations.After;
import ru.otus.mytest.annotations.Before;
import ru.otus.mytest.annotations.Test;
import ru.otus.mytest.utils.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.mytest.utils.ReflectionHelper.callMethod;
import static ru.otus.mytest.utils.ReflectionHelper.instantiate;

public final class MyTest {

    private MyTest() {

    }

    public static void startTest(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Map<Method, Boolean> tasks = new HashMap<>();

            List<Method> beforeList = Arrays.stream(clazz.getMethods())
                    .filter(MyTest::hasBefore).toList();

            List<Method> afterList = Arrays.stream(clazz.getMethods())
                    .filter(MyTest::hasAfter).toList();

            Arrays.stream(clazz.getMethods())
                    .filter(MyTest::hasTest)
                    .forEach(tm -> {
                        Object obj = createNewByObject(clazz, null);

                        beforeList.forEach(bm -> runMethod(obj, bm.getName()));
                        tasks.put(tm, runMethod(obj, tm.getName()));

                        afterList.forEach(am -> runMethod(obj, am.getName()));
                    });

            status(tasks);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void status(Map<Method, Boolean> tasks) {
        System.out.println("There were " + tasks.keySet().size() + " tests");
        int completed = 0;
        int notCompleted = 0;
        for (Map.Entry<Method, Boolean> entry : tasks.entrySet()) {
            if (entry.getValue()) {
                completed++;
                System.out.println("The " + entry.getKey().getName() + " has been completed");
            } else {
                notCompleted++;
                System.out.println("The " + entry.getKey().getName() + " has not been completed");
            }
        }

        System.out.println("Completed: " + completed);
        System.out.println("Not completed: " + notCompleted);
    }

    private static boolean runMethod(Object object, String name) {
        try {
            callMethod(object, name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean hasBefore(Method method) {
        return method.isAnnotationPresent(Before.class);
    }

    private static boolean hasTest(Method method) {
        return method.isAnnotationPresent(Test.class);
    }

    private static boolean hasAfter(Method method) {
        return method.isAnnotationPresent(After.class);
    }

    /**
     * @param clazz create an instance of a class
     * @param old   get field values and set them to the new instance
     * @return new instance
     */
    private static Object createNewByObject(Class<?> clazz, Object old) {
        Object newInstance = instantiate(clazz);
        setField(newInstance, old);
        return newInstance;
    }

    private static void setField(Object newObj, Object oldObj) {
        if (oldObj == null) {
            return;
        }

        for (var filed : newObj.getClass().getDeclaredFields()) {
            var oldValue = ReflectionHelper.getFieldValue(oldObj, filed.getName());
            ReflectionHelper.setFieldValue(newObj, filed.getName(), oldValue);
        }
    }

}
