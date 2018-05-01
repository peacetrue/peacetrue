package com.github.peacetrue.associate.jpa;

import com.github.peacetrue.associate.CollectionAssociatedSource;

import javax.persistence.EntityManager;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * for associated template property
 *
 * @author xiayx
 */
public class JpaTemplateCollectionAssociatedSource<I> extends JpaCollectionAssociatedSource<I, Object[], String> implements CollectionAssociatedSource<I, Object[], String> {

    private String[] propertyNames;
    private int idIndex = 0;
    private String template;

    public JpaTemplateCollectionAssociatedSource() {
    }

    public JpaTemplateCollectionAssociatedSource(Class<?> entityClass) {
        super(entityClass);
    }

    public JpaTemplateCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass) {
        super(entityManager, entityClass);
    }

    public JpaTemplateCollectionAssociatedSource(EntityManager entityManager, Class<?> entityClass, String[] propertyNames, int idIndex, String template) {
        super(entityManager, entityClass);
        this.propertyNames = propertyNames;
        this.idIndex = idIndex;
        this.template = template;
    }

    @Override
    protected String getSelect(String entityAlias) {
        return Arrays.stream(propertyNames).map(s -> entityAlias + "." + s).collect(Collectors.joining(","));
    }

    @SuppressWarnings("unchecked")
    public I resolveId(Object[] data) {
        return (I) data[idIndex];
    }

    public String format(Object[] data) {
        return MessageFormat.format(template, data);
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    public int getIdIndex() {
        return idIndex;
    }

    public void setIdIndex(int idIndex) {
        this.idIndex = idIndex;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
