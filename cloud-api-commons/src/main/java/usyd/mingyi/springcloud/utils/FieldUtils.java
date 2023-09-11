package usyd.mingyi.springcloud.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FieldUtils {

    //私有化构造器 不让new

    private FieldUtils() {

    }


    public static <T, S> List<S> extractField(List<T> objects, Function<T, S> fieldExtractor) {

        List<S> fieldValues = new ArrayList<>();
        for (T object : objects) {
            S value = fieldExtractor.apply(object);
            fieldValues.add(value);
        }
        return fieldValues;
    }

}
