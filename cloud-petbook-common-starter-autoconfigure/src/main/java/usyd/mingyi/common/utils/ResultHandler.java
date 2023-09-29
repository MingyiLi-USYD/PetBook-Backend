package usyd.mingyi.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ResultHandler {

    public static <L,S> Map<L, S> convertUserListToMap(List<S> users, Function<S, L> getUserId) {
        return users.stream()
                .collect(Collectors.toMap(getUserId, user -> user));
    }

    public static <T,U,S,L> List<U> mergeObjectLists(List<T> objects,
                                                  List<S> userIds,
                                                  BiConsumer<U, S> setObjectInfo,
                                                  Function<U, L> getFriendId,
                                                  Supplier<U> supplier,
                                                  Function<S, L> getUserId) {

        Map<L, S> userMap = convertUserListToMap(userIds, getUserId);
        List<U> objectDtos = new ArrayList<>();
        for (T object : objects) {
            U objectDto = supplier.get();
            BeanUtils.copyProperties(object, objectDto);
            setObjectInfo.accept(objectDto, userMap.get(getFriendId.apply(objectDto)));
            objectDtos.add(objectDto);
        }
        return objectDtos;
    }

}
