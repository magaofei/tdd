package io.github.magaofei;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    
    public static <T> T parse(Class<T> optionClass, String... args) {
        List<String> arguments = Arrays.asList(args);
        Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
        
        Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(arguments, it)).toArray();
        try {
            return (T) constructor.newInstance(values);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    
    }
    
    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Option annotation = parameter.getAnnotation(Option.class);
        
        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = arguments.contains("-" + annotation.value());
        } else if (parameter.getType() == int.class) {
            int index = arguments.indexOf("-" + annotation.value());
            value = Integer.parseInt(arguments.get(index + 1));
        } else if (parameter.getType() == String.class) {
            int index = arguments.indexOf("-" + annotation.value());
            value = arguments.get(index + 1);
        }
        return value;
    }
    
}
