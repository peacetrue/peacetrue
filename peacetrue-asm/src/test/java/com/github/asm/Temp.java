package com.github.asm;

/**
 * @author xiayx
 */
public class Temp {

    private String name;

    public Temp(String name) {
        this.name = name;
    }

    public Temp test(Object name) {
        A a = new A();
        System.out.println(a);
        return null;
    }

    public static class A {
        private String name;
    }

    private static class B {
        private String name;
    }
}
