package com.github.peacetrue.reactivefeign.webclient;

import com.github.peacetrue.reactivefeign.methodhandler.ReactiveMethodHandlerFactory;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactivefeign.ReactiveFeign;
import reactivefeign.ReactiveOptions;
import reactivefeign.methodhandler.MethodHandlerFactory;
import reactivefeign.methodhandler.fallback.FallbackMethodHandlerFactory;
import reactivefeign.publisher.PublisherClientFactory;
import reactivefeign.webclient.CustomizableWebClientBuilder;
import reactivefeign.webclient.WebClientFeignCustomizer;
import reactivefeign.webclient.WebReactiveOptions;

import java.util.function.Function;

import static reactivefeign.webclient.client.WebReactiveHttpClient.webClient;

/**
 * copy from {@link reactivefeign.webclient.WebReactiveFeign}
 * add {@link Builder#encoder} and {@link Builder#decoder}
 *
 * @see reactivefeign.webclient.WebReactiveFeign
 */
public class WebReactiveFeign {

    public static <T> Builder<T> builder() {
        return builder(WebClient.builder());
    }

    public static <T> Builder<T> builder(WebClient.Builder webClientBuilder) {
        return new Builder<>(webClientBuilder);
    }

    public static <T> Builder<T> builder(WebClient.Builder webClientBuilder,
                                         WebClientFeignCustomizer webClientCustomizer) {
        return new Builder<>(webClientBuilder, webClientCustomizer);
    }

    public static class Builder<T> extends ReactiveFeign.Builder<T> {

        public Encoder encoder;
        public Decoder decoder;

        protected CustomizableWebClientBuilder webClientBuilder;

        protected Builder(WebClient.Builder webClientBuilder) {
            this.webClientBuilder = new CustomizableWebClientBuilder(webClientBuilder);
            updateClientFactory();
        }

        protected Builder(WebClient.Builder webClientBuilder, WebClientFeignCustomizer webClientCustomizer) {
            this.webClientBuilder = new CustomizableWebClientBuilder(webClientBuilder);
            webClientCustomizer.accept(this.webClientBuilder);
            updateClientFactory();
        }

        @Override
        public Builder<T> options(ReactiveOptions options) {
            webClientBuilder.setWebOptions((WebReactiveOptions) options);
            updateClientFactory();
            return this;
        }

        protected void updateClientFactory() {
            clientFactory(methodMetadata -> webClient(methodMetadata, webClientBuilder.build()));
        }

        @Override
        public MethodHandlerFactory buildReactiveMethodHandlerFactory(PublisherClientFactory reactiveClientFactory) {
            ReactiveMethodHandlerFactory methodHandlerFactory = new ReactiveMethodHandlerFactory(reactiveClientFactory);
            methodHandlerFactory.encoder = encoder;
            methodHandlerFactory.decoder = decoder;
            return fallbackFactory != null
                    ? new FallbackMethodHandlerFactory(methodHandlerFactory, (Function<Throwable, Object>) fallbackFactory)
                    : methodHandlerFactory;
        }
    }


}


