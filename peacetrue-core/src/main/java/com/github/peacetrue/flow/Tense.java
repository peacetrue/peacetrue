package com.github.peacetrue.flow;

import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.NameCapable;

/**
 * 时态
 *
 * @author xiayx
 */
public enum Tense implements CodeCapable, NameCapable {

    TODO("todo", "待执行"),
    DOING("doing", "执行中"),
    DONE("done", "已执行"),
    SUCCESS("success", "成功"),
    FAILURE("failure", "失败"),;

    private String code;
    private String name;

    Tense(String code, String name) {
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
