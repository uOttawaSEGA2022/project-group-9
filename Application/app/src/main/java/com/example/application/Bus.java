package com.example.application;

import java.util.HashMap;
import java.util.Map;

public class Bus {
    private static Map<Integer,Object> map;
    private static int counter;
    static
    {
        map=new HashMap<Integer,Object>();
        counter=0;
    }
    public static int savedata(Object o)
    {
        map.put(counter,o);
        counter++;
        return counter-1;
    }
    public static Object getdata(int a)
    {
        return map.get(a);
    }
    public static void deletedata(int a)
    {
        map.remove(a);
    }
}
