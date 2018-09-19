package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

/**
 * asm工具类
 *
 * @author xiayx
 */
public abstract class AsmUtils {

    /**
     * 打印类的字节码内容
     *
     * @param className 类名称，例如：com.github.asm.AsmUtils
     */
    public static void printContent(String className) {
        try {
            ClassReader cr = new ClassReader(className);
            cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), ClassReader.EXPAND_FRAMES);
        } catch (IOException e) {
            throw new IllegalArgumentException("the class path '" + className + "' may not exists", e);
        }
    }

    /**
     * 打印字节码内容
     *
     * @param classBytecode 类的字节码，可以通过{@link ClassLoaderUtils#loadClass(ClassLoader, String)}获取
     */
    public static void printContent(byte[] classBytecode) {
        ClassReader cr = new ClassReader(classBytecode);
        cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), ClassReader.EXPAND_FRAMES);
    }

    /**
     * 将类的字节码内容写入类
     *
     * @param packageName     包名，例如：com.github.asm。
     *                        必须是一个已存在的包，否则无法获取到包的绝对路径；
     *                        最好是一个唯一的包，否则取得的绝对路径，可能不是你所预期的
     * @param classSimpleName 类的简名，例如：AsmUtils
     * @param classBytecode   类的字节码
     */
    public static void write(String packageName, String classSimpleName, byte[] classBytecode) {
        String packagePath = ClassLoaderUtils.toPath(packageName);
        packagePath = AsmUtils.class.getResource("/" + packagePath).getPath();
        Path path = Paths.get(packagePath, classSimpleName + ".class");
        try {
            Files.write(path, classBytecode);
        } catch (IOException e) {
            throw new IllegalArgumentException("the class bytecode path '" + path + "' of class name '" + (packageName + '.' + classSimpleName) + "' may not exists", e);
        }
    }

    /** 将类的字节码内容写入类 */
    public static void write(String className, byte[] classBytecode) {
        int index = className.lastIndexOf('.');
        write(className.substring(0, index), className.substring(index + 1), classBytecode);
    }


    /**
     * 使用target中的静态方法替换source中的静态方法，返回替换后的字节码
     * <p>
     * <pre>
     *  package com.github.asm;
     *  public class Bean1{
     *      public void print(String name){
     *          System.out.println(name);
     *      }
     *  }
     *
     *  Bean1.print("name"); // name
     *
     *  package com.github.asm;
     *  public class Bean2{
     *      public void print(String name){
     *          System.out.println("logger:"+name);
     *      }
     *  }
     *
     *  byte[] bytes = AmsUtils.replaceStatic("com.github.asm.Bean1",
     *                                        "com.github.asm.Bean2",
     *                                        Method.getMethod("void print(String)"));
     *  ClassLoaderUtils.defineClass("com.github.asm.Bean1", bytes);
     *  Bean1.print("name"); // logger:name
     * </pre>
     *
     * @param sourceClassName 原始类名称，例如：com.github..Bean1
     * @param targetClassName 目标类名称，例如：com.github..Bean2
     * @param methods         要替换的方法，必须是在source和target中都存在的方法
     * @return 修改后的字节码
     */
    public static byte[] replaceStatic(ClassLoader classLoader, String sourceClassName, String targetClassName, Method... methods) {
        return replaceStatic(
                ClassLoaderUtils.loadClass(classLoader, sourceClassName),
                Type.getObjectType(ClassLoaderUtils.toPath(targetClassName)),
                methods);
    }

    /** 基于{@link #replaceStatic(byte[], Type, Method...)}设置了默认的{@link ClassLoader} */
    public static byte[] replaceStatic(String sourceClassName, String targetClassName, Method... methods) {
        return replaceStatic(AsmUtils.class.getClassLoader(), sourceClassName, targetClassName, methods);
    }

    /** {@link #replaceStatic(ClassLoader, String, String, Method...)}的底层实现 */
    public static byte[] replaceStatic(byte[] sourceClassBytecode, Type target, Method... methods) {
        ClassReader classReader = new ClassReader(sourceClassBytecode);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!Modifier.isStatic(access)) return methodVisitor;

                Optional<Method> methodOptional = Arrays.stream(methods)
                        .filter(method -> method.getName().equals(name) && method.getDescriptor().equals(descriptor))
                        .findAny();
                if (methodOptional.isPresent()) {
                    Method method = methodOptional.get();
                    return new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor) {
                        @Override
                        public void visitCode() {
                            loadArgs();
                            invokeStatic(target, method);
                            returnValue();
                            endMethod();
                        }
                    };
                }
                return methodVisitor;
            }
        };
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

}
