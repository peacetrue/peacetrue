package com.github.peacetrue.mybatis.spring;

import org.apache.ibatis.session.RowBounds;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * support for {@link com.github.peacetrue.mybatis.Pageable}
 *
 * @author xiayx
 */
public class PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String DEFAULT_PAGE_NUM_PARAMETER_NAME = "pageNum";
    public static final String DEFAULT_PAGE_SIZE_PARAMETER_NAME = "pageSize";
    public static final int DEFAULT_PAGE_NUM_PARAMETER_VALUE = 0;
    public static final int DEFAULT_PAGE_SIZE_PARAMETER_VALUE = 10;

    private String offsetParameterName = DEFAULT_PAGE_NUM_PARAMETER_NAME;
    private String limitParameterName = DEFAULT_PAGE_SIZE_PARAMETER_NAME;
    private Integer defaultOffsetParameterValue = DEFAULT_PAGE_NUM_PARAMETER_VALUE;
    private Integer defaultSizeParameterValue = DEFAULT_PAGE_SIZE_PARAMETER_VALUE;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RowBounds.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String offsetString = webRequest.getParameter(offsetParameterName);
        Integer offsetInt = StringUtils.hasText(offsetString) ? parseInt(parameter, offsetParameterName, offsetString.trim()) : defaultOffsetParameterValue;
        String limitString = webRequest.getParameter(limitParameterName);
        Integer limitInt = StringUtils.hasText(limitString) ? parseInt(parameter, limitParameterName, limitString.trim()) : defaultSizeParameterValue;
        if (offsetInt == null && limitInt == null) return null;
        return new RowBounds(offsetInt, limitInt);
    }

    protected int parseInt(MethodParameter parameter, String name, String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new MethodArgumentTypeMismatchException(value, Integer.class, name, parameter, e);
        }
    }

    public String getOffsetParameterName() {
        return offsetParameterName;
    }

    public void setOffsetParameterName(String offsetParameterName) {
        this.offsetParameterName = offsetParameterName;
    }

    public String getLimitParameterName() {
        return limitParameterName;
    }

    public void setLimitParameterName(String limitParameterName) {
        this.limitParameterName = limitParameterName;
    }

    public Integer getDefaultOffsetParameterValue() {
        return defaultOffsetParameterValue;
    }

    public void setDefaultOffsetParameterValue(Integer defaultOffsetParameterValue) {
        this.defaultOffsetParameterValue = defaultOffsetParameterValue;
    }

    public Integer getDefaultSizeParameterValue() {
        return defaultSizeParameterValue;
    }

    public void setDefaultSizeParameterValue(Integer defaultSizeParameterValue) {
        this.defaultSizeParameterValue = defaultSizeParameterValue;
    }
}
