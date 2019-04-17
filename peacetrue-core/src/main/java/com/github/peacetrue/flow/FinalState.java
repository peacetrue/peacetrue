package com.github.peacetrue.flow;

import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.NameCapable;

/**
 * 终态
 *
 * @author xiayx
 */
public enum FinalState implements CodeCapable, NameCapable {

    DOING("doing", "进行中"),
    SUCCESS("success", "已完成"),
    FAILURE("failure", "已关闭"),;

    private String code;
    private String name;

    FinalState(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
