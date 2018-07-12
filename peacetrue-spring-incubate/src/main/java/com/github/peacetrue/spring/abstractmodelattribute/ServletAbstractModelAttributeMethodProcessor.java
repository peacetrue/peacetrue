package com.github.peacetrue.spring.abstractmodelattribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * the bean type is determined by request and is subclass of the special type,
 * similar to {@link ServletModelAttributeMethodProcessor}
 *
 * @author xiayx
 * @see AbstractModelAttribute
 * @see ServletModelAttributeMethodProcessor
 */
public class ServletAbstractModelAttributeMethodProcessor extends ModelAttributeMethodProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ConcreteTypeResolver concreteTypeResolver;

    public ServletAbstractModelAttributeMethodProcessor(ConcreteTypeResolver concreteTypeResolver) {
        super(true);
        this.concreteTypeResolver = Objects.requireNonNull(concreteTypeResolver);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        Class<?> parameterType = parameter.getParameterType();
        //the request must determine the concrete type
//        return !BeanUtils.isSimpleProperty(parameterType)
//                && (parameterType.equals(Object.class)
//                || parameterType.isInterface()
//                || Modifier.isAbstract(parameterType.getModifiers()));
        return parameter.getParameterAnnotation(AbstractModelAttribute.class) != null
                && !BeanUtils.isSimpleProperty(parameter.getParameterType());
    }

    /** similar to {@link ServletModelAttributeMethodProcessor#createAttribute(String, MethodParameter, WebDataBinderFactory, NativeWebRequest)} */
    protected Object createAttribute(String attributeName, MethodParameter methodParam,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

        String value = getRequestValueForAttribute(attributeName, request);
        if (value != null) {
            Object attribute = createAttributeFromRequestValue(
                    value, attributeName, methodParam, binderFactory, request);
            if (attribute != null) {
                return attribute;
            }
        }

        //return super.createAttribute(attributeName, methodParam, binderFactory, request);
        return createSubclassAttribute(attributeName, methodParam, binderFactory, request);
    }

    protected Object createSubclassAttribute(String attributeName, MethodParameter methodParam,
                                             WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        Class<?> resolveClass = concreteTypeResolver.resolveConcreteType(request, methodParam.getParameterType());
        if (logger.isDebugEnabled()) {
            logger.debug("use the concrete model attribute type '{}' to instance", resolveClass.getName());
        }
        return BeanUtils.instantiateClass(resolveClass);
    }


    /** same to {@link ServletModelAttributeMethodProcessor#getRequestValueForAttribute(String, NativeWebRequest)} */
    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        Map<String, String> variables = getUriTemplateVariables(request);
        String variableValue = variables.get(attributeName);
        if (StringUtils.hasText(variableValue)) {
            return variableValue;
        }
        String parameterValue = request.getParameter(attributeName);
        if (StringUtils.hasText(parameterValue)) {
            return parameterValue;
        }
        return null;
    }

    /** same to {@link ServletModelAttributeMethodProcessor#getUriTemplateVariables(NativeWebRequest)} */
    @SuppressWarnings("unchecked")
    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (variables != null ? variables : Collections.emptyMap());
    }

    /** similar to {@link ServletModelAttributeMethodProcessor#createAttributeFromRequestValue(String, String, MethodParameter, WebDataBinderFactory, NativeWebRequest)} */
    protected Object createAttributeFromRequestValue(String sourceValue, String attributeName,
                                                     MethodParameter methodParam, WebDataBinderFactory binderFactory, NativeWebRequest request)
            throws Exception {

        DataBinder binder = binderFactory.createBinder(request, null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            //TypeDescriptor target = new TypeDescriptor(methodParam);
            TypeDescriptor target = TypeDescriptor.valueOf(concreteTypeResolver.resolveConcreteType(request, methodParam.getParameterType()));
            if (conversionService.canConvert(source, target)) {
                return binder.convertIfNecessary(sourceValue, methodParam.getParameterType(), methodParam);
            }
        }
        return null;
    }

    /** same to {@link ServletModelAttributeMethodProcessor#bindRequestParameters(WebDataBinder, NativeWebRequest)} */
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }


    /**
     * get the concrete model attribute type from request
     *
     * @author xiayx
     */
    public interface ConcreteTypeResolver {

        /**
         * get the concrete model attribute type from request
         *
         * @param request   (never {@code null})
         * @param beanClass the model attribute type(never {@code null})
         * @return the concrete model attribute type(never {@code null}), is subclass of beanClass
         * @throws ResolveException when unable to resolve the concrete model attribute type
         */
        <T> Class<? extends T> resolveConcreteType(NativeWebRequest request, Class<T> beanClass) throws ResolveException;

        /** the exception for {@link #resolveConcreteType(NativeWebRequest, Class)} */
        class ResolveException extends RuntimeException {

            public ResolveException(String message) {
                super(message);
            }

            public ResolveException(String message, Throwable cause) {
                super(message, cause);
            }
        }


    }

}
