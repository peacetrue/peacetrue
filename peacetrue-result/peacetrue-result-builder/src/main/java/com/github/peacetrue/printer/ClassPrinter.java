package com.github.peacetrue.printer;

import org.springframework.format.Printer;

/**
 * a {@link Printer} for {@link Class}
 *
 * @author xiayx
 */
public interface ClassPrinter extends Printer<Class> {

    /**
     * Print the object of type T for display.
     *
     * @param object the instance to print
     * @return the printed text string
     */
    String print(Class object);


}
