package usyd.mingyi.common.utils;

import usyd.mingyi.common.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class UserUtils {
    private static final Map<String,Integer> weightMap = new HashMap<>();
    static {
        weightMap.put("User",0);
        weightMap.put("Admin",1);
        weightMap.put("SuperAdmin",2);
        weightMap.put("Root",3);
    }

    public static boolean canModify(User current, User target,String role){

          if(weightMap.containsKey(current.getRole())
          &&weightMap.containsKey(target.getRole())
          &&weightMap.containsKey(role)){

              return weightMap.get(current.getRole())>weightMap.get(target.getRole())
                      &&weightMap.get(current.getRole())>weightMap.get(role);
          }
          return false;

    }


    public static boolean isRightStatus(byte status){

        return status >= 0 && status <= 3;

    }



}
