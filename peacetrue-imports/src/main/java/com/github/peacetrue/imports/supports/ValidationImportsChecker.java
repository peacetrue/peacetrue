package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ImportsChecker;
import com.github.peacetrue.imports.ImportsContext;
import com.github.peacetrue.imports.ImportsResult;
import com.github.peacetrue.imports.RowNumberWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
public class ValidationImportsChecker<T> implements ImportsChecker<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Validator validator;

    @Override
    public void check(List<RowNumberWrapper<T>> records, ImportsContext importsContext) {
        logger.info("验证记录[{}]", records);
        ValidatorImportsCheckerRules rules2 = (ValidatorImportsCheckerRules) importsContext;
        @SuppressWarnings("unchecked")
        ConstraintBeanList<T> beanList = BeanUtils.instantiateClass(rules2.getListClass());
        beanList.setBeans(records.stream().map(RowNumberWrapper::getRow).collect(Collectors.toList()));
        Set<ConstraintViolation<Object>> violations = validator.validate(beanList);

        if (CollectionUtils.isEmpty(violations)) return;

        ImportsResult<T> result = importsContext.getImportsResult();
        RowNumberWrapperImpl<Object> wrapper = new RowNumberWrapperImpl<>();
        for (ConstraintViolation<Object> violation : violations) {
            wrapper.setRow(violation.getRootBean());
            int index = result.getCheckedRecords().indexOf(wrapper);
            RowNumberWrapper<T> wrapper1 = result.getCheckedRecords().remove(index);
//            result.getErrorMessages().add(new CellErrorMessage(wrapper1.getRowNumber(), 0, violation.getMessage()));
        }

    }
}
