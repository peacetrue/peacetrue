package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * the adapter of {@link ResultBuilder}
 *
 * @author xiayx
 */
public class ResultBuilderAdapter implements ResultBuilder {

    private ResultBuilder resultBuilder;

    @Override
    public Result build(String code) {
        return resultBuilder.build(code);
    }

    @Override
    public Result build(String code, @Nullable Object[] arguments) {
        return resultBuilder.build(code, arguments);
    }

    @Override
    public Result build(String code, @Nullable Locale locale) {
        return resultBuilder.build(code, locale);
    }

    @Override
    public Result build(String code, @Nullable Object[] arguments, @Nullable Locale locale) {
        return resultBuilder.build(code, arguments, locale);
    }

    @Override
    public <T> DataResult<T> build(String code, T data) {
        return resultBuilder.build(code, data);
    }

    @Override
    public <T> DataResult<T> build(String code, T data, @Nullable Locale locale) {
        return resultBuilder.build(code, data, locale);
    }

    @Override
    public <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data) {
        return resultBuilder.build(code, arguments, data);
    }

    @Override
    public <T> DataResult<T> build(String code, @Nullable Object[] arguments, T data, @Nullable Locale locale) {
        return resultBuilder.build(code, arguments, data, locale);
    }

    @Override
    public Result success() {
        return resultBuilder.success();
    }

    @Override
    public Result success(@Nullable Locale locale) {
        return resultBuilder.success(locale);
    }

    @Override
    public <T> DataResult<T> success(T data) {
        return resultBuilder.success(data);
    }

    @Override
    public <T> DataResult<T> success(T data, @Nullable Locale locale) {
        return resultBuilder.success(data, locale);
    }

    @Override
    public Result failure() {
        return resultBuilder.failure();
    }

    @Override
    public Result failure(@Nullable Locale locale) {
        return resultBuilder.failure(locale);
    }

    @Override
    public <T> DataResult<T> failure(T data) {
        return resultBuilder.failure(data);
    }

    @Override
    public <T> DataResult<T> failure(T data, @Nullable Locale locale) {
        return resultBuilder.failure(data, locale);
    }

    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }
}
