package com.github.peacetrue.result.printer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a {@link ClassPrinter} implement by {@link MessageSource}
 *
 * @author xiayx
 */
public class MessageSourceClassPrinter implements ClassPrinter {

    private String prefix = "Class";
    private MessageSource messageSource;

    @Override
    public String print(Class object) throws NoSuchMessageException {
        return print(object, LocaleContextHolder.getLocale());
    }

    @Override
    public String print(Class object, Locale locale) throws NoSuchMessageException {
        String message;
        Class forClass = object;
        Set<String> codes = new LinkedHashSet<>();
        while (forClass != Object.class) {
            String code = prefix + "." + forClass.getName();
            message = print(code, locale);
            if (message != null) return message;
            forClass = forClass.getSuperclass();
            codes.add(code);
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
