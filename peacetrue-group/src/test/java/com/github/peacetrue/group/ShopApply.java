package com.github.peacetrue.group;

import lombok.Data;

import java.util.Date;

/**
 * @author xiayx
 */
@Data
public class ShopApply {
    private Long id;
    private String name;
    private Long creatorId;
    private Date createdTime;
}
