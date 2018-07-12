package com.github.peacetrue.security;

import java.util.Objects;
import java.util.function.Function;

/**
 * 代理编码器
 *
 * @author xiayx
 */
public class ProxyEncoder implements Encoder {

    private Encoder encoder;
    private Function<String, String> converter;

    public ProxyEncoder(Encoder encoder, Function<String, String> converter) {
        this.encoder = Objects.requireNonNull(encoder);
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public String encode(String string) {
        return encoder.encode(converter.apply(string));
    }
}
