package com.github.peacetrue.imports.supports;

import java.util.List;

public interface ConstraintBeanList<T> {

    String PROPERTY_NAME = "beans";

    void setBeans(List<T> beans);

}