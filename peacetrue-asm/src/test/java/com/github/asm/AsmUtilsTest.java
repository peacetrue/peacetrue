package com.github.asm;

import com.github.peacetrue.util.ClassLoaderUtils;
import org.junit.Test;
import org.objectweb.asm.commons.Method;


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
        AsmUtils.write("com.github.asm.Generated",
                ClassLoaderUtils.loadClass("com.github.asm.AsmUtils"));
        AsmUtils.printContent("com.github.asm.Generated");
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

}
