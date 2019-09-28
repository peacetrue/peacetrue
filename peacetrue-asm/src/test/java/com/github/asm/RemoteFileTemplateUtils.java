package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import org.springframework.core.io.Resource;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;

public abstract class RemoteFileTemplateUtils {

    public static final String REMOTE_FILE_TEMPLATE_CLASS_NAME = "org.springframework.integration.file.remote.RemoteFileTemplate";

    public static void init() {
        try {
            makeStreamHolderPublic();
            changePayloadToInputStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /** make {@link org.springframework.integration.file.remote.RemoteFileTemplate.StreamHolder} be public */
    public static byte[] makeStreamHolderPublic() throws IOException {
        String className = RemoteFileTemplateUtils.REMOTE_FILE_TEMPLATE_CLASS_NAME + "$StreamHolder";

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
                return super.visitMethod(name.equals("<init>") ? Modifier.PUBLIC : access, name, descriptor, signature, exceptions);
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        byte[] bytes = classWriter.toByteArray();

        ClassLoaderUtils.defineClass(RemoteFileTemplateUtils.class.getClassLoader(), className, bytes);
        return bytes;
    }

    /** change the method {@link org.springframework.integration.file.remote.RemoteFileTemplate#payloadToInputStream(Message)} */
    public static byte[] changePayloadToInputStream() throws IOException {
        ClassReader classReader = new ClassReader(REMOTE_FILE_TEMPLATE_CLASS_NAME);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6, classWriter) {
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                super.visitInnerClass(name, outerName, innerName, Modifier.PUBLIC);
            }

            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!name.equals("payloadToInputStream")) return methodVisitor;
                return new SupportResource(Opcodes.ASM6, methodVisitor, access, name, descriptor);
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        byte[] bytes = classWriter.toByteArray();
        ClassLoaderUtils.defineClass(RemoteFileTemplateUtils.class.getClassLoader(), REMOTE_FILE_TEMPLATE_CLASS_NAME, bytes);
        return bytes;
    }

    /** support {@link Message#getPayload()} be {@link Resource} */
    public static class SupportResource extends AdviceAdapter {
        public SupportResource(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        /**
         * <pre>
         * Object payload = message.getPayload();
         * if (payload instanceof Resource) {
         *      try {
         *          return new StreamHolder(((Resource) payload1).getInputStream(), ((Resource) payload1).getFilename());
         *      } catch (IOException e) {
         *          throw new IllegalArgumentException("Read InputStream from Resource error", e);
         *      }
         * }
         * </pre>
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

            Type streamHolderType = Type.getObjectType(ClassLoaderUtils.toPath(REMOTE_FILE_TEMPLATE_CLASS_NAME));
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
    }
}
