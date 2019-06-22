package com.github.peacetrue.spring.resource;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 将{@link MultipartFile}转换成{@link Resource}
 *
 * @author xiayx
 */
public class MultipartFileResource extends AbstractResource {

    private MultipartFile multipartFile;

    public MultipartFileResource(MultipartFile multipartFile) {
        this.multipartFile = Objects.requireNonNull(multipartFile);
    }

    @Override
    public String getDescription() {
        return "from MultipartFile";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    @Override
    public long contentLength() throws IOException {
        return multipartFile.getSize();
    }

    @Override
    public String getFilename() {
        return multipartFile.getOriginalFilename();
    }

}