package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.junit.Test;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;


/**
 * @author xiayx
 */
public class AsmUtilsTest {

    @Test
    public void run() throws Exception {
        ClassReader classReader = new ClassReader("com.github.asm.Bean1");

        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("print2")) {
                    MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                    return new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor) {
                        @Override
                        public void visitCode() {
                            loadArgs();
                            invokeStatic(Type.getType(Bean2.class), Method.getMethod("String print2(String)"));
                            returnValue();
                            endMethod();
                        }
                    };
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        byte[] bytes = classWriter.toByteArray();
        AsmUtils.printContent(bytes);
        AsmUtils.write("com.github.asm", "Generatored", bytes);
    }

    @Test
    public void name() throws Exception {
        byte[] bytes = AsmUtils.replaceStatic("com.github.asm.Bean1",
                "com.github.asm.Bean2",
                Method.getMethod("void print1(String)"),
                Method.getMethod("String print2(String)"));
        AsmUtils.printContent(bytes);
        AsmUtils.write("com.github.asm", "Generatored", bytes);
        ClassLoaderUtils.defineClass("com.github.asm.Bean1", bytes);
        Bean1.print1("name");
    }
}
