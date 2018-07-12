package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

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

    @Override
    public Result build(String code, @Nullable Object[] arguments, @Nullable Locale locale) {
        return buildInternal(code, arguments, null, locale);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data, @Nullable Locale locale) {
        return (DataResult<T>) buildInternal(code, arguments, Objects.requireNonNull(data), locale);
    }

    /**
     * build {@link Result} from code, arguments, data and locale
     *
     * @param code      the custom result code
     * @param arguments the arguments
     * @param data      the data
     * @param locale    the locale
     * @return the {@link Result}
     */
    protected Result buildInternal(String code, @Nullable Object[] arguments, @Nullable Object data, @Nullable Locale locale) {
        logger.info("build Result for code '{}' with arguments {}", code, Arrays.toString(arguments));
        if (locale == null) locale = LocaleContextHolder.getLocale();
        if (arguments != null) arguments = resolveArguments(arguments, locale);
        String message = this.resolveMessage(code, arguments, locale);
        if (message == null) message = "No message found under code '" + code + "' for locale '" + locale + "'.";
        return data == null ? new ResultImpl(code, message) : new DataResultImpl<>(code, message, data);
    }

    protected String resolveMessage(String code, @Nullable Object[] arguments, @Nullable Locale locale) {
        String[] codes = resolveCode(code);
        logger.debug("extend code '{}' to codes {}", code, Arrays.toString(codes));
        String prefix = StringUtils.hasText(this.prefix) ? (this.prefix + ".") : "";
        CollectionUtils.map(codes, s -> prefix + s);
        return getMessage(codes, arguments, locale);
    }

    protected String[] resolveCode(String code) {
        //com...Exception -> [com...Exception, Exception]
        int index = code.lastIndexOf(".");
        return index == -1 ? new String[]{code} : new String[]{code, code.substring(index + 1)};
    }

    /** find message for multiple code */
    @Nullable
    private String getMessage(String[] codes, Object[] arguments, Locale locale) {
        for (String code : codes) {
            String message = getMessage(code, arguments, locale);
            if (message != null) {
                logger.debug("got message '{}' from code '{}'", message, code);
                return message;
            }
        }
        return null;
    }

    /** avoid {@link NoSuchMessageException} */
    @Nullable
    private String getMessage(String code, Object[] arguments, Locale locale) {
        return messageSource.getMessage(code, arguments, null, locale);
    }

    /**
     * convert arguments, make all argument human readable
     *
     * @param arguments the arguments
     * @param locale    the locale
     * @return the arguments
     */
    protected Object[] resolveArguments(Object[] arguments, Locale locale) {
        Object[] resolved = null;
        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            if (!(argument instanceof Class)) continue;
            if (resolved == null) resolved = Arrays.copyOf(arguments, arguments.length);
            resolved[i] = classPrinter.print((Class) argument, locale);
            logger.debug("print argument '{}' to '{}'", argument, resolved[i]);
        }
        return resolved == null ? arguments : resolved;
    }

    @Override
    public Result success(@Nullable Locale locale) {
        return build(ResultType.success.name(), locale);
    }

    @Override
    public <T> DataResult<T> success(T data, @Nullable Locale locale) {
        return build(ResultType.success.name(), null, data, locale);
    }

    @Override
    public Result failure(@Nullable Locale locale) {
        return build(ResultType.failure.name(), locale);
    }

    @Override
    public <T> DataResult<T> failure(T data, @Nullable Locale locale) {
        return build(ResultType.failure.name(), null, data, locale);
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

}
