package com.yunze.vehiclemanagement.util;

import java.util.*;

public class MapUtil {

    /**
     * 获取匹配value的keys
      * @param map
     * @param value
     * @return
     */
    public static List<Object> getKeysOfValueMatch(Map map , Object value){
        // 值相同的keys
        List<Object> keys = new ArrayList<>();
        Set<Map.Entry> entrys = map.entrySet();
        for (Map.Entry entry : entrys){
            if(entry.getValue().equals(value)){
                keys.add(entry.getKey());
            }
        }
        return  keys;
    }

    /**
     * 按value将key分组
     * @param map
     * @return
     */
    public static Map<Object,List<Object>> GroupOfSameValue(Map map){
        Map<Object,List<Object>> inversionMap = new HashMap<>();
        SortedSet<Object> distinctValue = new TreeSet<>();
        Iterator iterator = map.values().iterator();
        while (iterator.hasNext()){
            distinctValue.add(iterator.next());
        }
        for (Object value : distinctValue){
            inversionMap.put(value,getKeysOfValueMatch(map,value));
        }
        return inversionMap;
    }
}
