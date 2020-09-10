package com.github.peacetrue.reactivefeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.reactivefeign.webclient.WebReactiveFeign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactivefeign.ReactiveFeignBuilder;
import reactivefeign.spring.config.ReactiveFeignClientsConfiguration;
import reactivefeign.webclient.WebClientFeignCustomizer;

/**
 * adapter ReactiveFeign to support
 * {@link org.springframework.data.domain.Pageable}
 * and {@link org.springframework.data.domain.Page}
 *
 * @author : xiayx
 * @since : 2020-06-28 05:29
 **/
@Configuration
@AutoConfigureBefore(ReactiveFeignClientsConfiguration.class)
@Import(FeignClientsConfiguration.class)
@PropertySource("classpath:peacetrue-reactivefeign.yml")
public class PeacetrueReactiveFeignAutoConfiguration {

    /**
     * rewrite to set {@link Encoder} and {@link Decoder}
     *
     * @see reactivefeign.spring.config.ReactiveFeignClientsConfiguration.ReactiveFeignConfiguration.ReactiveFeignWebConfiguration#reactiveFeignBuilder(WebClient.Builder, WebClientFeignCustomizer)
     */
    @Bean
    @Scope("prototype")
    public ReactiveFeignBuilder<?> reactiveFeignBuilder(
            WebClient.Builder builder,
            @Autowired(required = false) WebClientFeignCustomizer webClientCustomizer,
            Encoder encoder,
            Decoder decoder
    ) {
        WebReactiveFeign.Builder<Object> objectBuilder = webClientCustomizer != null
                ? WebReactiveFeign.builder(builder, webClientCustomizer)
                : WebReactiveFeign.builder(builder);
        objectBuilder.encoder = encoder;
        objectBuilder.decoder = decoder;
        return objectBuilder;
    }

    /**
     * support {@link org.springframework.data.domain.Page}
     *
     * @see FeignClientsConfiguration#pageJacksonModule()
     */
    @Bean
    @ConditionalOnMissingBean
    public WebClientFeignCustomizer webClientFeignCustomizer(ObjectMapper objectMapper) {
        return builder -> builder.exchangeStrategies(ExchangeStrategies.builder().codecs(codecs -> {
            codecs.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
            codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
        }).build());
    }

}
