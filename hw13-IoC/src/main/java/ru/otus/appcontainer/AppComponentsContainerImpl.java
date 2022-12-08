package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        initComponents(configClass);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var components = appComponents.stream().filter((c) -> isInterfaceEquals(componentClass, c)).toList();
        if (components.size() > 1) {
            throw new RuntimeException("There are two same components " + components);
        }
        return (C) components.get(0);
    }

    private <C> boolean isInterfaceEquals(Class<C> componentClass, Object object) {
        var interfaces = object.getClass().getInterfaces();
        var compInterfaces = new ArrayList<>(Arrays.stream(componentClass.getInterfaces()).toList());
        compInterfaces.add(componentClass);
        for (var inter : interfaces) {
            if (compInterfaces.contains(inter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return Optional.of((C) appComponentsByName.get(componentName.toLowerCase()))
                .orElseThrow(() -> new RuntimeException("Could not find component by name " + componentName));
    }

    private void initComponents(Class<?> configClass) {
        var methods = new ArrayList<>(Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .toList());

        sortMethodsByOrder(methods);

        var config = createConfig(configClass);

        for (var method : methods) {
            createComponentByMethod(config, method);
        }
    }

    private <C> C createComponentByMethod(Object config, Method method) {
        var parameters = Arrays.stream(method.getParameterTypes()).toList();
        var name = getComponentName(method);

        C c;

        if (parameters.isEmpty()) {
            c = createNewInstance(method, config, null);
            addComponent(c, name);
        } else {
            var args = parameters.stream()
                    .map(this::getAppComponent)
                    .toList();

            c = createNewInstance(method, config, args.toArray());
            addComponent(c, name);
        }

        return c;
    }

    private Object createConfig(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void addComponent(Object obj, @Nullable String name) {
        if (appComponentsByName.get(name) != null) {
            throw new RuntimeException("It is forbidden to use two the same component name!");
        }

        appComponentsByName.put(name, obj);
        appComponents.add(obj);
    }

    /**
     * Get bean name from annotation or get name from method type.
     *
     * @param method from config class
     * @return name in lowercase
     */
    private String getComponentName(Method method) {
        return Optional.of(method.getAnnotation(AppComponent.class).name())
                .orElse(method.getReturnType().getSimpleName())
                .toLowerCase();
    }

    private <C> C createNewInstance(Method method, Object config, Object[] args) {
        try {
            return (C) method.invoke(config, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void sortMethodsByOrder(List<Method> methods) {
        methods.sort(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()));
    }
}
