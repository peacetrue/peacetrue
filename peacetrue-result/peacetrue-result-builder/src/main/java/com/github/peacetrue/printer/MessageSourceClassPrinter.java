package com.github.peacetrue.printer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a {@link ClassPrinter} implement by {@link MessageSource}.
 * <p>
 * Lookup code through the inheritance structure of the class.
 *
 * @author xiayx
 */
public class MessageSourceClassPrinter implements ClassPrinter {

    public static final String DEFAULT_PREFIX = "Class";

    private String prefix = DEFAULT_PREFIX;
    private MessageSource messageSource;

    @Override
    public String print(Class object) {
        return print(object, LocaleContextHolder.getLocale());
    }

    @Override
    public String print(Class object, Locale locale) {
        String message;
        Class forClass = object;
        String prefix = this.prefix == null ? "" : (this.prefix + ".");
        Set<String> codes = new LinkedHashSet<>();
        while (true) {
            String code = prefix + forClass.getName();
            message = print(code, locale);
            if (message != null) return message;
            codes.add(code);
            if (forClass == Object.class) break;
            forClass = forClass.getSuperclass();
        }

        throw new NoSuchMessageException(codes.stream().collect(Collectors.joining(",")), locale);
    }

    /**
     * print message
     *
     * @param code   the code
     * @param locale the locale
     * @return the message
     */
    protected String print(String code, Locale locale) {
        return messageSource.getMessage(code, null, null, locale);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
