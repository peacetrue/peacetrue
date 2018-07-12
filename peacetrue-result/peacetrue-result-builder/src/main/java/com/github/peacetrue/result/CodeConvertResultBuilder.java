package com.github.peacetrue.result;

import com.github.peacetrue.core.CodeAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Locale;
import java.util.function.BiFunction;

/**
 * used to convert {@link Result#getCode()}
 *
 * @author xiayx
 */
public class CodeConvertResultBuilder extends ResultBuilderAdapter {

    private static final Field FIELD_CODE = ReflectionUtils.findField(ResultImpl.class, "code");

    static {
        FIELD_CODE.setAccessible(true);
    }

    private static Logger logger = LoggerFactory.getLogger(CodeConvertResultBuilder.class);
    private ResultCodeConverter resultCodeConverter;

    protected <T extends Result> T handleCode(T result, BiFunction<T, String, T> converter) {
        String resolve = resultCodeConverter.convert(result.getCode());
        return resolve == null || resolve.equals(result.getCode())
                ? result
                : converter.apply(result, resolve);
    }

    protected <T extends Result> T setCodeChain(T result, String code) {
        setCode(result, code);
        return handleData(result);
    }

    @SuppressWarnings("unchecked")
    public static void setCode(Result result, String code) {
        if (result instanceof CodeAware) {
            setCode((CodeAware) result, code);
            logger.debug("set code from '{}' to '{}'", result.getCode(), code);
        } else if (result instanceof ResultImpl) {
            setCode((ResultImpl) result, code);
            logger.debug("set code from '{}' to '{}'", result.getCode(), code);
        }
    }

    public static void setCode(ResultImpl result, String code) {
        ReflectionUtils.setField(FIELD_CODE, result, code);
    }

    public static void setCode(CodeAware codeAware, String code) {
        codeAware.setCode(code);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Result> T handleData(T result) {
        if (!(result instanceof DataResult)) return result;
        Object data = ((DataResult) result).getData();
        if (!(data instanceof Collection)) return result;

        ((Collection) data).forEach(item -> {
            if (item instanceof Result) handleCode((Result) item, this::setCodeChain);
        });
        return result;
    }

    @Override
    public Result build(String code) {
        return handleCode(super.build(code), this::setCodeChain);
    }

    @Override
    public Result build(String code, @Nullable Object[] arguments) {
        return handleCode(super.build(code, arguments), this::setCodeChain);
    }

    @Override
    public Result build(String code, @Nullable Locale locale) {
        return handleCode(super.build(code, locale), this::setCodeChain);
    }

    @Override
    public Result build(String code, @Nullable Object[] arguments, @Nullable Locale locale) {
        return handleCode(super.build(code, arguments, locale), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> build(String code, T data) {
        return handleCode(super.build(code, data), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> build(String code, T data, @Nullable Locale locale) {
        return handleCode(super.build(code, data, locale), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data) {
        return handleCode(super.build(code, arguments, data), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data, @Nullable Locale locale) {
        return handleCode(super.build(code, arguments, data, locale), this::setCodeChain);
    }

    @Override
    public Result success(@Nullable Locale locale) {
        return handleCode(super.success(locale, locale), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> success(T data, @Nullable Locale locale) {
        return handleCode(super.success(data, locale), this::setCodeChain);
    }

    @Override
    public Result failure(@Nullable Locale locale) {
        return handleCode(super.failure(), this::setCodeChain);
    }

    @Override
    public <T> DataResult<T> failure(T data, @Nullable Locale locale) {
        return handleCode(super.failure(data, locale), this::setCodeChain);
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        super.setResultBuilder(resultBuilder);
    }

    @Autowired
    public void setResultCodeConverter(ResultCodeConverter resultCodeConverter) {
        this.resultCodeConverter = resultCodeConverter;
    }
}
