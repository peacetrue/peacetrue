package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Parent {

    public void generateClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(49, Opcodes.ACC_PUBLIC, "Parent$OtherChild", null,
                Type.getInternalName(Child.class), new String[]{});
        // .. generate the class
        byte[] bytes = cw.toByteArray();
        Class<?> genClass = ClassLoaderUtils.defineClass("Parent$OtherChild", bytes);
    }


    private static class Child {
    }

}