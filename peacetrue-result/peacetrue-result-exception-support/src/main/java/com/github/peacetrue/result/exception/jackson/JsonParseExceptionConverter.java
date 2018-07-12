package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.annotation.Nullable;

/**
 * for {@link JsonParseException} in {@link HttpMessageNotReadableException}
 *
 * @author xiayx
 */
public class JsonParseExceptionConverter extends AbstractExceptionConverter<JsonParseException, Object[]> {

    @Nullable
    @Override
    protected Object[] resolveData(JsonParseException exception) {
        //Unexpected character ('{' (code 123))
        //was expecting double-quote to start field name
        return new Object[]{extractErrorChar(exception.getMessage()), exception.getLocation().getLineNr(), exception.getLocation().getColumnNr() - 1};
    }

    @Nullable
    @Override
    protected Object[] resolveArguments(JsonParseException exception, Object[] data) {
        return data;
    }

    protected String extractErrorChar(String message) {
        return message.substring(message.indexOf("'") + 1, message.lastIndexOf("'"));
    }

//    @Override
//    protected String resolveCode(JsonParseException exception) {
//        return ErrorType.argument_format_mismatch.name();
//    }

}
