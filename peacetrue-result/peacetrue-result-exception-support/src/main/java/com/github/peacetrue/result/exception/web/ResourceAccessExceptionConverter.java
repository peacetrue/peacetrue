package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.ResolveExceptionConverter;
import com.github.peacetrue.util.URLDecoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import javax.annotation.Nullable;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * for {@link ResourceAccessException}
 *
 * @author xiayx
 */
public class ResourceAccessExceptionConverter extends ResolveExceptionConverter<ResourceAccessException, String[]> {

//    public static final String PATTERN_METHOD = Arrays.stream(HttpMethod.values()).map(Enum::name).collect(Collectors.joining("|"));
//    public static final String PATTERN_URL = "(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?";

    private ExceptionConvertService exceptionConvertService;

    @Override
    public Result convert(ResourceAccessException exception, @Nullable Locale locale) {
        if (exception.getCause() instanceof UnknownHostException) {
            return exceptionConvertService.convert((Exception) exception.getCause(), locale);
        }
        if (exception.getCause() instanceof ConnectException) {
//            return exceptionConvertService.convert(new ConnectException(exception.getCause()), locale);
        }
        return super.convert(exception, locale);
    }

    @Override
    protected Pattern[] getPatterns() {
        return new Pattern[]{
                Pattern.implementation("I/O error on ([^ ]+) request for \"(.*)\"")
        };
    }

    @Override
    protected String[] convertArguments(String[] arguments) {
        return new String[]{arguments[0], URLDecoderUtils.decode(arguments[1])};
    }

    @Nullable
    @Override
    protected Object[] resolveArguments(ResourceAccessException exception, String[] data) {
        return data;
    }

    @Override
    protected String resolveCode(ResourceAccessException exception) {
        return ResourceAccessException.class.getSimpleName();
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }
}
