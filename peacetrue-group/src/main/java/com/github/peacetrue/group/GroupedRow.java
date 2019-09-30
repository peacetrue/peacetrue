package com.github.peacetrue.group;

import lombok.Data;

/**
 * 分组的行信息
 *
 * @author xiayx
 */
@Data
public class GroupedRow<GroupId, GroupValue> {
    /** 分组标识 */
    private GroupId id;
    /** 分组值 */
    private GroupValue value;
}
