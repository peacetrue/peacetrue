package com.github.peacetrue.result.exception.converters;

/**
 * similar to {@link org.springframework.validation.FieldError}
 *
 * @author xiayx
 */
public class FieldErrorBean {

    private String propertyPath;
    private Object invalidValue;

    public FieldErrorBean() {
    }

    public FieldErrorBean(String propertyPath, Object invalidValue) {
        this.propertyPath = propertyPath;
        this.invalidValue = invalidValue;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }
}
