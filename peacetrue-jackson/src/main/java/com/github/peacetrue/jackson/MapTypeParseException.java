package com.github.peacetrue.jackson;

/**
 * @author xiayx
 */
public class MapTypeParseException extends RuntimeException {

    private String type;

    public MapTypeParseException(String type) {
        super(String.format("the type[%s] can't be parsed to class", type));
        this.type = type;
    }

    public MapTypeParseException(String type, Throwable cause) {
        super(String.format("the type[%s] can't be parsed to class", type), cause);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
