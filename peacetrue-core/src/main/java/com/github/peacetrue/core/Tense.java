package com.github.peacetrue.core;

/**
 * 时态
 *
 * @author xiayx
 * @deprecated 更改了目录，请使用 {@link com.github.peacetrue.flow.Tense}
 */
@Deprecated
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
