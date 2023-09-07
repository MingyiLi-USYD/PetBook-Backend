package usyd.mingyi.springcloud.utils;

import usyd.mingyi.springcloud.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FieldUtils {

    //私有化构造器 不让new

    private FieldUtils(){

    }


    public static <T, S> List<S> extractField(List<T> objects, Function<T, S> fieldExtractor) {

        List<S> fieldValues = new ArrayList<>();
        for (T object : objects) {
            S value = fieldExtractor.apply(object);
            fieldValues.add(value);
        }
        return fieldValues;
    }

    public static <T,S> List<S> extractField(R<List<T>> response, Function<T, S> fieldExtractor) {
        if (response.getCode().equals(1)) {
            return extractField(response.getData(),fieldExtractor);
        }
        return new ArrayList<>();
    }
}
