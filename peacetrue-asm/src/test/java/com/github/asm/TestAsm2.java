package com.github.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestAsm2 implements Opcodes {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ClassWriter cw = new ClassWriter(0);
        String className = "Example2";
        cw.visit(0x31, ACC_PUBLIC, className, null, "java/lang/Object", new String[]{"java/lang/Cloneable", ITest.class.getName().replace('.', '/')});

        String a = "a";
        Object av = 123;

        String getA = "getA";
        String setA = "setA";
        cw.visitField(ACC_PRIVATE, a, "I", null, av == null ? null : Integer.parseInt(av.toString())).visitEnd();


        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, getA, "()I", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, className, a, "I");
        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        cw.visitEnd();


        /***********************************************************/
        /*public void setA(int var1) {
        if (var1 >= 0) {
            this.a = var1;
        }

    }*/
        mv = cw.visitMethod(ACC_PUBLIC, setA, "(I)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ILOAD, 1);
        mv.visitLdcInsn(3);
        Label label = new Label();
        mv.visitJumpInsn(IF_ICMPLT, label);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitFieldInsn(PUTFIELD, className, "a", "I");
        Label end = new Label();
        mv.visitJumpInsn(GOTO, end);
        mv.visitLabel(label);
        mv.visitLabel(end);
        mv.visitInsn(RETURN);
        mv.visitMaxs(3, 2);
        mv.visitEnd();
        /***********************************************************/


        //下面产生构造方法
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();


        cw.visitEnd();

        byte[] bs = cw.toByteArray();
        File file = new File("/Users/xiayx/Documents/Projects/peacetrue/peacetrue-asm/src/test/Example.class");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(bs);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyCLassLoader loader = new MyCLassLoader();
        Class<?> c = loader.defineClass(bs);

        ITest ins = (ITest) c.newInstance();
        ins.setA(10);
        System.out.println(ins.getA());
    }

    public interface ITest {
        public int getA();

        public void setA(int a);
    }

    public static class MyCLassLoader extends ClassLoader {
        public Class<?> defineClass(byte[] data) {
            return super.defineClass(null, data, 0, data.length, null);
        }
    }
}