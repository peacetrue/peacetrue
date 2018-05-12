package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Locale;

/**
 * implement by {@link MessageSource}
 *
 * @author xiayx
 */
public class MessageSourceResultBuilder implements ResultBuilder {

    public static final String DEFAULT_PREFIX = "Result";

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String prefix = DEFAULT_PREFIX;
    private MessageSource messageSource;
    private ClassPrinter classPrinter;
    private ResultCodeResolver resultCodeResolver;

    @Override
    public <T> DataResult<T> build(String code, Object[] arguments, T data, Locale locale) {
        logger.info("build DataResult for code: {}", code);
        logger.debug("arguments: {}, data: {}", Arrays.toString(arguments), data);
        if (locale == null) locale = LocaleContextHolder.getLocale();
        String fullCode = StringUtils.hasText(prefix) ? (prefix + "." + code) : code;
        if (arguments != null) arguments = resolveArguments(arguments, locale);
        String message = messageSource.getMessage(fullCode, arguments, locale);
        return new DataResultImpl<>(code, message, data);
    }

    /**
     * resolve arguments, make all argument human readable
     *
     * @param arguments the arguments
     * @param locale    the locale
     * @return the arguments
     */
    protected Object[] resolveArguments(Object[] arguments, Locale locale) {
        if (Arrays.stream(arguments).anyMatch(o -> o instanceof Class)) {
            arguments = Arrays.copyOf(arguments, arguments.length);
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] instanceof Class) {
                    arguments[i] = classPrinter.print((Class) arguments[i], locale);
                }
            }
        }
        return arguments;
    }

    @Override
    public Result build() {
        return build(resultCodeResolver.resolve(ResultType.success.name()));
    }

    @Override
    public <T> DataResult<T> build(T data) {
        return build(resultCodeResolver.resolve(ResultType.success.name()), null, data);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setClassPrinter(ClassPrinter classPrinter) {
        this.classPrinter = classPrinter;
    }

    @Autowired
    public void setResultCodeResolver(ResultCodeResolver resultCodeResolver) {
        this.resultCodeResolver = resultCodeResolver;
    }
}
