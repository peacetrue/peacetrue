package com.github.peacetrue.spring.abstractmodelattribute;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.annotation.*;

/**
 * supported abstract object as method argument
 *
 * @author xiayx
 * @see ModelAttribute
 * @see ServletAbstractModelAttributeMethodProcessor
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AbstractModelAttribute {

}
