package io.github.magaofei;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    
    public static <T> T parse(Class<T> optionClass, String... args) {
        Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
        Parameter parameter = constructor.getParameters()[0];
        Option annotation = parameter.getAnnotation(Option.class);
        List<String> arguments = Arrays.asList(args);
        try {
            return (T) constructor.newInstance(arguments.contains("-" + annotation.value()));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    
    }
    
}
