package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;


/**
 * @author xiayx
 */
public class AsmUtilsTest {

    @Test
    public void write() throws Exception {
        AsmUtils.write("com.github.asm", "Generated",
                ClassLoaderUtils.loadClass("com.github.asm.AsmUtils"));
        AsmUtils.printContent("com.github.asm.Generated");
    }

    @Test
    public void write1() throws Exception {
        AsmUtils.printContent(Temp.class.getName());
    }

    @Test
    public void replaceStatic() throws Exception {
        String bean1 = "com.github.asm.Bean1";
        byte[] bytes = AsmUtils.replaceStatic(
                bean1,
                "com.github.asm.Bean2",
                Method.getMethod("void print1(String)"),
                Method.getMethod("String print2(String)"));
        ClassLoaderUtils.defineClass(bean1, bytes);
        Bean1.print1("i have changed");
        Bean1.print2("i have changed");
    }

    public static byte[] process(String className, Class<AdviceAdapter> adapterClass) throws IOException {
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        new ClassVisitor(Opcodes.ASM6, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!name.equals("payloadToInputStream")) return methodVisitor;
                return (AdviceAdapter) BeanUtils.instantiateClass(adapterClass.getConstructors()[0], Opcodes.ASM6, methodVisitor, access, name, descriptor);
            }
        };
        return classWriter.toByteArray();
    }

    @Test
    public void addCondition() throws Exception {
        String className = "org.springframework.integration.file.remote.RemoteFileTemplate";
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6, classWriter) {

            @Override
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                super.visitInnerClass(name, outerName, innerName, access);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!name.equals("payloadToInputStream")) return methodVisitor;


                return new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor) {


                    /**
                     * Object payload = message.getPayload();
                     * if (payload instanceof Resource) {
                     *     try {
                     *          return new StreamHolder(((Resource) payload1).getInputStream(), ((Resource) payload1).getFilename());
                     *     } catch (IOException e) {
                     *          throw new IllegalArgumentException("Read InputStream from Resource error", e);
                     *     }
                     * }
                     */
                    @Override
                    public void visitCode() {
                        //Object payload = message.getPayload();
                        mark();
                        loadArgs();
                        invokeInterface(Type.getType(Message.class), Method.getMethod("java.lang.Object getPayload()"));
                        int payloadLocal = newLocal(Type.getType(Object.class));
                        storeLocal(payloadLocal);

                        //if (payload instanceof Resource) {
                        mark();
                        loadLocal(payloadLocal);
                        instanceOf(Type.getType(Resource.class));
                        Label resourceLabel = newLabel();
                        visitJumpInsn(Opcodes.IFEQ, resourceLabel);

                        //return new StreamHolder(((Resource) payload1).getInputStream(), ((Resource) payload1).getFilename());
                        Label startLabel = mark();
                        loadLocal(payloadLocal);
                        invokeInterface(Type.getType(Resource.class), Method.getMethod("java.io.InputStream getInputStream()"));
                        int inputStreamLocal = newLocal(Type.getType(InputStream.class));
                        storeLocal(inputStreamLocal);

                        loadLocal(payloadLocal);
                        invokeInterface(Type.getType(Resource.class), Method.getMethod("java.lang.String getFilename()"));
                        int filenameLocal = newLocal(Type.getType(String.class));
                        storeLocal(filenameLocal);

                        Type streamHolderType = Type.getObjectType(ClassLoaderUtils.toPath("org.springframework.integration.file.remote.RemoteFileTemplate$StreamHolder"));
                        newInstance(streamHolderType);
                        dup();
                        loadLocal(inputStreamLocal);
                        loadLocal(filenameLocal);
                        invokeConstructor(streamHolderType, Method.getMethod("void <init>(java.io.InputStream,java.lang.String)"));
                        returnValue();

                        //try{}catch(IOException e){}
                        Label endLabel = mark();
                        catchException(startLabel, endLabel, Type.getType(IOException.class));

                        //throw new IllegalArgumentException("Read InputStream from Resource error", e);
                        int ioExceptionLocal = newLocal(Type.getType(IOException.class));
                        storeLocal(ioExceptionLocal);
                        Type illegalType = Type.getType(IllegalArgumentException.class);
                        newInstance(illegalType);
                        dup();
                        visitLdcInsn("Read InputStream from Resource error");
                        loadLocal(ioExceptionLocal);
                        invokeConstructor(illegalType, Method.getMethod("void <init>(java.lang.String,java.lang.Throwable)"));
                        throwException();

                        //else
                        visitLabel(resourceLabel);
                    }
                };
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        byte[] bytes = classWriter.toByteArray();
        AsmUtils.printContent(bytes);
        AsmUtils.write("com.github.asm.Generated", bytes);
    }

    @Test
    public void name3() throws Exception{
        RemoteFileTemplateUtils.makeStreamHolderPublic();
        byte[] bytes = RemoteFileTemplateUtils.changePayloadToInputStream();
        AsmUtils.write("com.github.asm.Generated", bytes);
    }

    @Test
    public void name2() {
        System.out.println(Type.getType("Lcom/github/asm/AsmUtilsTest$A$B;"));//Lcom/github/asm/AsmUtilsTest$A$B;
        System.out.println(Type.getType("Lcom/github/asm/AsmUtilsTest$A$B"));//Lcom/github/asm/AsmUtilsTest$A$B;
    }

    @Test
    public void changeToPublic() throws Exception {
        String className = "com.github.asm.Parent$Child";
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6, classWriter) {

            @Override
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                super.visitInnerClass(name, outerName, innerName, Modifier.PUBLIC);
            }

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, Modifier.PUBLIC, name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return super.visitMethod(Modifier.PUBLIC, name, descriptor, signature, exceptions);
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        byte[] bytes = classWriter.toByteArray();
        ClassLoaderUtils.defineClass(getClass().getClassLoader(), className, bytes);
        new Parent().generateClass();
    }
}
