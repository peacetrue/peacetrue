package com.github.peacetrue.sign.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.sign.CachedSecretProvider;
import com.github.peacetrue.sign.SecretProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * the AutoConfiguration for server sign
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ServerSignProperties.class)
public class ServerSignAutoConfiguration {

    private ServerSignProperties properties;

    public ServerSignAutoConfiguration(ServerSignProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SecretProvider secretProvider() {
        CachedSecretProvider provider = new CachedSecretProvider();
        provider.setSecrets(properties.getApps());
        return provider;
    }

    @Bean
    public ServerSignService serverSignService() {
        JsonServerSignService serverSignService = new JsonServerSignService();
        serverSignService.setSignProperty(properties.getSignProperty());
        return serverSignService;
    }

    @Bean
    public SignCheckRequestBodyAdvice signCheckRequestBodyAdvice() {
        return new SignCheckRequestBodyAdvice();
    }

    @Bean
    public BeanPostProcessor beanPostProcessor(@Autowired SignCheckRequestBodyAdvice signRequestBodyAdvice) {
        return new BeanPostProcessor() {

            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RequestMappingHandlerAdapter) {
                    setSignRequestBodyAdvice((RequestMappingHandlerAdapter) bean);
                }
                return bean;
            }

            @SuppressWarnings("unchecked")
            private void setSignRequestBodyAdvice(RequestMappingHandlerAdapter adapter) {
                Field field = ReflectionUtils.findField(adapter.getClass(), "requestResponseBodyAdvice");
                field.setAccessible(true);
                List<Object> bodyAdvices = (List) ReflectionUtils.getField(field, adapter);
                for (int i = 0; i < bodyAdvices.size(); i++) {
                    Object requestBodyAdvice = bodyAdvices.get(i);
                    if (requestBodyAdvice instanceof JsonViewRequestBodyAdvice) {
                        bodyAdvices.set(i, signRequestBodyAdvice);
                    }
                }
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

}
