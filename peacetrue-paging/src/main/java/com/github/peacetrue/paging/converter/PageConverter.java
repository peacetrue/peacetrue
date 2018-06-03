package com.github.peacetrue.paging.converter;

/**
 * convert source page to target page
 *
 * @param <T> the type of source page
 * @author xiayx
 */
public interface PageConverter<T> {

    /**
     * convert source page to target page
     *
     * @param page the source page
     * @return the target page
     */
    Object convert(T page);

}
