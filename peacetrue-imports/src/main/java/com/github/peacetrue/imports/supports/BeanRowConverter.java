package com.github.peacetrue.imports.supports;

import com.github.peacetrue.imports.ImportsSetting;
import com.github.peacetrue.imports.RowConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author xiayx
 */
@SuppressWarnings("unchecked")
public class BeanRowConverter<T> implements RowConverter<T> {
    @Override
    public T convert(Object[] cells, ImportsSetting setting) {
        Class modelClass = ((ClassImportSetting) setting).getModelClass();
        BeanWrapper beanWrapper = new BeanWrapperImpl(modelClass);
        beanWrapper.setAutoGrowNestedPaths(true);
//        for (int i = 0; i < values.length; i++) {
//            ColumnSetting columnSetting = setting.getColumnSettings().get(i);
//            beanWrapper.setPropertyValue(((PropertyColumnSetting) columnSetting).getPropertyName(), values[0]);
//        }
        return (T) beanWrapper.getWrappedInstance();
    }
}
