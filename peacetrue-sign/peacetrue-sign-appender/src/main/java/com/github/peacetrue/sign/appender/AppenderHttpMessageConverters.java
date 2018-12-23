package com.github.peacetrue.sign.appender;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.MultiValueMap;

/**
 * 使用该对象封装{@link HttpMessageConverter}，
 * 避免其自动注入到{@link org.springframework.boot.autoconfigure.web.HttpMessageConverters}，
 * 影响控制器对{@link org.springframework.web.bind.annotation.RequestBody} {@link org.springframework.web.bind.annotation.ResponseBody}的处理
 *
 * @author xiayx
 */
public interface AppenderHttpMessageConverters {

    /** 获取表单参数转换器 */
    HttpMessageConverter<MultiValueMap<String, ?>> getForm();

    /** 获取原始参数转换器 */
    HttpMessageConverter<Object> getRaw();
}
