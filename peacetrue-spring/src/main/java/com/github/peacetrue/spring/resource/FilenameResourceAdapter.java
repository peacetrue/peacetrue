package com.github.peacetrue.spring.resource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.Objects;

/**
 * 通常用于适配{@link ByteArrayResource}，
 * {@link ByteArrayResource}缺少文件名，上传后服务端接收时会异常
 *
 * @author xiayx
 */
public class FilenameResourceAdapter extends ResourceAdapter {

    private String filename;

    public FilenameResourceAdapter(Resource resource, String filename) {
        super(resource);
        this.filename = Objects.requireNonNull(filename);
    }

    @Override
    public String getFilename() {
        return filename;
    }

}
