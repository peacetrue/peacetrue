package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ImportsCheckerRules;
import lombok.Data;

/**
 * @author xiayx
 */
@Data
public class ValidatorImportsCheckerRules implements ImportsCheckerRules {
    private Class<? extends ConstraintBeanList> listClass;
    private Class<?> beanClass;
}
