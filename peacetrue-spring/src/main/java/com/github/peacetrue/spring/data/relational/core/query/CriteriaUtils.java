package com.github.peacetrue.spring.data.relational.core.query;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * util class for {@link Criteria}
 *
 * @author : xiayx
 * @since : 2020-05-20 12:16
 **/
public abstract class CriteriaUtils {

    /** the property name of id */
    public static final String ID_PROPERTY_NAME = "id";

    /** protected to be extends but avoid instance */
    protected CriteriaUtils() {
    }

    /** replace {@link Criteria#and(List)} */
    public static Criteria and(@Nullable Criteria... criteria) {
        return logicCalculate(CriteriaDefinition.Combinator.AND, criteria);
    }

    /** replace {@link Criteria#or(List)} */
    public static Criteria or(@Nullable Criteria... criteria) {
        return logicCalculate(CriteriaDefinition.Combinator.OR, criteria);
    }

    /** logic calculate for criteria */
    public static Criteria logicCalculate(CriteriaDefinition.Combinator combinator, @Nullable Criteria... criteria) {
        if (criteria == null || criteria.length == 0) return Criteria.empty();
        if (criteria.length == 1) return nullToEmpty(criteria[0]);
        Criteria returnCriteria = null;
        for (Criteria itemCriteria : criteria) {
            if (itemCriteria == null || itemCriteria == Criteria.empty()) continue;
            if (returnCriteria == null) returnCriteria = itemCriteria;
            else {
                switch (combinator) {
                    case AND:
                        returnCriteria = returnCriteria.and(itemCriteria);
                        break;
                    case OR:
                        returnCriteria = returnCriteria.or(itemCriteria);
                        break;
                    default:
                        throw new IllegalArgumentException("combinator must be AND or OR");
                }
            }
        }
        return nullToEmpty(returnCriteria);
    }

    public static Criteria nullToEmpty(@Nullable Criteria criteria) {
        return Optional.ofNullable(criteria).orElse(Criteria.empty());
    }

    /** when null value use empty criteria */
    public static <T> Criteria nullableCriteria(Function<T, Criteria> criteriaConverter, Supplier<T> valueSupplier) {
        return _nullableCriteria(criteriaConverter, valueSupplier.get());
    }

    /** when null value use empty criteria */
    public static <T> Criteria nullableCriteria(Function<T, Criteria> criteriaConverter, UnaryOperator<T> valueConverter, Supplier<T> valueSupplier) {
        return _nullableCriteria(criteriaConverter, valueConverter, valueSupplier.get());
    }

    private static <T> Criteria _nullableCriteria(Function<T, Criteria> criteriaConverter, T value) {
        return _nullableCriteria(criteriaConverter, UnaryOperator.identity(), value);
    }

    private static <T> Criteria _nullableCriteria(Function<T, Criteria> criteriaConverter, UnaryOperator<T> valueConverter, T valueSupplier) {
        return valueSupplier == null ? Criteria.empty() : criteriaConverter.apply(valueConverter.apply(valueSupplier));
    }

    /** when null id use empty criteria */
    public static Criteria nullableId(Supplier<?> idSupplier) {
        return nullableCriteria(Criteria.where(ID_PROPERTY_NAME)::is, idSupplier);
    }

    /** id criteria */
    public static Criteria id(Supplier<?> idSupplier) {
        return Criteria.where(ID_PROPERTY_NAME).is(idSupplier.get());
    }

    /** single value use equals, multiple value use in */
    public static Criteria smartIn(String column, Object... value) {
        return smartIn(column).apply(value);
    }

    /** single value use equals, multiple value use in */
    public static Function<Object[], Criteria> smartIn(String column) {
        return (array) -> {
            Criteria.CriteriaStep columnStep = Criteria.where(column);
            return array.length == 1 ? columnStep.is(array[0]) : columnStep.in(array);
        };
    }


}
