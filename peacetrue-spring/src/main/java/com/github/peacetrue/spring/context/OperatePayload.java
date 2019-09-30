package com.github.peacetrue.spring.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 操作负载。
 * 当你执行基于模块的某项操作时，
 * 会涉及到执行该操作所需要的参数以及对应的模块记录。
 *
 * @author xiayx
 */
@Data
@AllArgsConstructor
public class OperatePayload<Record, Operate> implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 模块记录信息 */
    private Record record;
    /** 操作参数信息 */
    private Operate operate;
}
