package com.github.peacetrue.associate;

import com.github.peacetrue.spring.util.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * utility methods for {@code Associate}.
 *
 * @author xiayx
 */
@SuppressWarnings("unchecked")
public abstract class AssociateUtils {


    /**
     * set associated value for associate object
     *
     * @param associate            the associate object
     * @param associatedProperty   the associated property to set
     * @param associatedSource     the associated data source
     * @param associatedIdProperty the associated id property
     * @param <I>                  the type of associated object id
     * @param <D>                  the type of associated data
     */
    public static <I, D> void setAssociate(Object associate, String associatedProperty, AssociatedSource<I, D, ?> associatedSource, String associatedIdProperty) {
        PropertyDescriptor associateIdPropertyDescriptor = BeanUtils.getRequiredPropertyDescriptor(associate.getClass(), associatedIdProperty);
        I id = (I) ReflectionUtils.invokeMethod(associateIdPropertyDescriptor.getReadMethod(), associate);
        if (id == null) return;
        D associated = associatedSource.findAssociate(id);
        BeanUtils.setPropertyValue(associate, associatedProperty, associatedSource.format(associated));
    }


    /**
     * set collection associated value for associate object
     *
     * @param associate            the associate object
     * @param associatedProperty   the associated property to set
     * @param associatedSource     the associated data source
     * @param associatedIdProperty the associated id property
     * @param <I>                  the type of associated object id
     * @param <D>                  the type of associated data
     */
    public static <I, D> void setCollectionAssociate(Object associate, String associatedProperty, CollectionAssociatedSource<I, D, ?> associatedSource, String associatedIdProperty) {
        PropertyDescriptor associateIdPropertyDescriptor = BeanUtils.getRequiredPropertyDescriptor(associate.getClass(), associatedIdProperty);
        Collection<I> idCollection = (Collection<I>) ReflectionUtils.invokeMethod(associateIdPropertyDescriptor.getReadMethod(), associate);
        if (CollectionUtils.isEmpty(idCollection)) return;
        Collection<D> associates = associatedSource.findCollectionAssociate(idCollection);
        BeanUtils.setPropertyValue(associate, associatedProperty, associatedSource.format(associates));
    }

    /**
     * set associated value for collection associate object
     *
     * @param associates           the collection associate object
     * @param associatedProperty   the associated property to set
     * @param associatedMap        the associated map data
     * @param associatedIdProperty the associated id property
     */
    public static void setAssociate(Collection<?> associates, String associatedProperty, Map<?, ?> associatedMap, String associatedIdProperty) {
        Class associateClass = com.github.peacetrue.util.CollectionUtils.detectElementType(associates);
        PropertyDescriptor associatedIdDescriptor = BeanUtils.getRequiredPropertyDescriptor(associateClass, associatedIdProperty);
        PropertyDescriptor associatedDescriptor = BeanUtils.getRequiredPropertyDescriptor(associateClass, associatedProperty);
        associates.forEach(associate -> {
            Object associatedId = ReflectionUtils.invokeMethod(associatedIdDescriptor.getReadMethod(), associate);
            ReflectionUtils.invokeMethod(associatedDescriptor.getWriteMethod(), associate, associatedMap.get(associatedId));
        });
    }

    /**
     * set associated value for collection associate object
     *
     * @param associates           the collection associate object
     * @param associatedProperty   the associated property to set
     * @param associatedSource     the associated data source
     * @param associatedIdProperty the associated id property
     * @param <I>                  the type of associated object id
     * @param <D>                  the type of associated data
     */
    public static <I, D> void setAssociate(Collection<?> associates, String associatedProperty, CollectionAssociatedSource<I, D, ?> associatedSource, String associatedIdProperty) {
        Class associateClass = com.github.peacetrue.util.CollectionUtils.detectElementType(associates);
        PropertyDescriptor associateIdDescriptor = BeanUtils.getRequiredPropertyDescriptor(associateClass, associatedIdProperty);
        Set<I> associatedIds = associates.stream()
                .map(associate -> (I) ReflectionUtils.invokeMethod(associateIdDescriptor.getReadMethod(), associate))
                .filter(Objects::nonNull).collect(Collectors.toSet());
        if (associatedIds.isEmpty()) return;
        Collection<D> associatedData = associatedSource.findCollectionAssociate(associatedIds);
        Map<I, ?> associatedMap = associatedData.stream().collect(Collectors.toMap(associatedSource::resolveId, associatedSource::format));
        setAssociate(associates, associatedProperty, associatedMap, associatedIdProperty);
    }

    /**
     * set collection associated value for collection associate object
     *
     * @param associates           the associate object
     * @param associatedProperty   the associated property to set
     * @param associatedSource     the associated data source
     * @param associatedIdProperty the associated id property
     * @param <I>                  the type of associated object id
     * @param <D>                  the type of associated data
     */
    public static <I, D> void setCollectionAssociate(Collection<?> associates, String associatedProperty, CollectionAssociatedSource<I, D, ?> associatedSource, String associatedIdProperty) {
        Class associateClass = com.github.peacetrue.util.CollectionUtils.detectElementType(associates);
        PropertyDescriptor associateIdDescriptor = BeanUtils.getRequiredPropertyDescriptor(associateClass, associatedIdProperty);
        Set<Collection<I>> associatedCollectionIds = associates.stream()
                .map(associate -> ((Collection<I>) ReflectionUtils.invokeMethod(associateIdDescriptor.getReadMethod(), associate)))
                .filter(Objects::nonNull).collect(Collectors.toSet());
        if (associatedCollectionIds.isEmpty()) return;
        Set<I> associatedIds = associatedCollectionIds.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        Collection<D> associateds = associatedSource.findCollectionAssociate(associatedIds);
        Map<I, ?> associatedMap = associateds.stream().collect(Collectors.toMap(associatedSource::resolveId, associatedSource::format));
        Map<Collection<I>, Collection<?>> collectionAssociatedMap = associatedCollectionIds.stream().collect(Collectors.toMap(Function.identity(), _associateIds -> _associateIds.stream().map(associatedMap::get).collect(Collectors.toList())));
        setAssociate(associates, associatedProperty, collectionAssociatedMap, associatedIdProperty);
    }


    public interface PropertyService {

        void setCollectionPropertyValue(Object bean, String property);

        void setCollectionPropertyValue(Object bean, String property, String collectionProperty);

        void setCollectionPropertyValue(Collection<?> beans, String property);

        void setCollectionPropertyValue(Collection<?> beans, String property, String collectionProperty);
    }

    public static class PropertyServiceImpl implements PropertyService {

        private Function<String, Collection> converter;

        public PropertyServiceImpl(Function<String, Collection> converter) {
            this.converter = Objects.requireNonNull(converter);
        }

        @Override
        public void setCollectionPropertyValue(Object bean, String property) {
            this.setCollectionPropertyValue(bean, property, property + "s");
        }

        @Override
        public void setCollectionPropertyValue(Object bean, String property, String collectionProperty) {
            String propertyValue = (String) BeanUtils.getPropertyValue(bean, property);
            if (StringUtils.isEmpty(propertyValue)) return;
            BeanUtils.setPropertyValue(bean, collectionProperty, converter.apply(propertyValue));
        }

        @Override
        public void setCollectionPropertyValue(Collection<?> beans, String property) {
            this.setCollectionPropertyValue(beans, property, property + "s");
        }

        @Override
        public void setCollectionPropertyValue(Collection<?> beans, String property, String collectionProperty) {
            beans.forEach(associate -> this.setCollectionPropertyValue(associate, property, collectionProperty));
        }
    }

    public static final PropertyService STRING_LIST = new PropertyServiceImpl(propertyValue -> Arrays.stream(propertyValue.split(",")).collect(Collectors.toList()));


}
