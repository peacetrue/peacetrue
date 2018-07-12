package com.github.peacetrue.spring.registercontroller;

import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

/**
 * used to register controller
 *
 * @author xiayx
 */
public class ControllerRegistrableWebMvcConfiguration extends DelegatingWebMvcConfiguration {

    public ControllerRegistrableRMHM createRequestMappingHandlerMapping() {
        return new ControllerRegistrableRMHM();
    }

}
