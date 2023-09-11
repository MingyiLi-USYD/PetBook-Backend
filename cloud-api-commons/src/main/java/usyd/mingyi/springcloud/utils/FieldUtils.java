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
    public static <T, S> List<S> extractField(List<T> objects, Function<T, S> fieldExtractor,boolean distinct) {
        if(!distinct){
            return extractField(objects,fieldExtractor);
        }
        //Set<T> ts = new HashSet<>(objects); 可以通过set的特性去重
        List<T> distinctObjects = objects.stream()
                .distinct()
                .toList();
       return   extractField(distinctObjects,fieldExtractor);
    }

}
