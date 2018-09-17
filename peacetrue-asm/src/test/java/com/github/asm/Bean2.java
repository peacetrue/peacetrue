package com.github.asm;

/**
 * @author xiayx
 */
public class Bean2 {

    public static void print1(String name) {
        System.out.println("logger:" + name);
    }
    
    public static String print2(String name) {
        System.out.println("logger:" + name);
        return name;
    }
}
