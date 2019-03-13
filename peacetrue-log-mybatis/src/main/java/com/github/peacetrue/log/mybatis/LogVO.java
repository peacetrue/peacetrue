package com.github.peacetrue.log.mybatis;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.util.Date;

/**
 * @author xiayx
 */
@ToString(callSuper = true)
public class LogVO extends Log {

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedTime() {
        return super.getCreatedTime();
    }

}
