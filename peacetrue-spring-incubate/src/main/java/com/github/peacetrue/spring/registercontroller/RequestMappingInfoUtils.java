package com.github.peacetrue.spring.registercontroller;

import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * the utils for {@link RequestMappingInfo}
 *
 * @author xiayx
 */
public abstract class RequestMappingInfoUtils {

    public static RequestMappingInfo from(PatternsRequestCondition patternsRequestCondition) {
        return from(patternsRequestCondition, null);
    }

    public static RequestMappingInfo from(PatternsRequestCondition patterns, ParamsRequestCondition params) {
        return new RequestMappingInfo(patterns, null, params, null, null, null, null);
    }
}
