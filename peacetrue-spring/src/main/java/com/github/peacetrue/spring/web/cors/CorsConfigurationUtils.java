package com.github.peacetrue.spring.web.cors;

import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * utils for {@link CorsConfiguration}
 *
 * @author : xiayx
 * @since : 2020-06-26 16:23
 **/
public abstract class CorsConfigurationUtils {

    protected CorsConfigurationUtils() {
    }

    /** support all */
    public static CorsConfiguration supportAll() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
}
