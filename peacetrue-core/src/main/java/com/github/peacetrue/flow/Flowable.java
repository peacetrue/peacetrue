package com.github.peacetrue.flow;

import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.NameCapable;
import com.github.peacetrue.util.StructureUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.github.peacetrue.flow.Tense.*;

/**
 * 具有流程的
 *
 * @author xiayx
 */
public interface Flowable {

    /** 获取节点编码 */
    String getNodeCode();

    /** 获取状态编码，参考 {@link Tense} */
    String getStateCode();

    /** 获取终态编码，参考 {@link FinalState} */
    String getFinishCode();

    Map<Tense, Function<String, String>> MAPPERS = _buildMappers();

    static Map<Tense, Function<String, String>> _buildMappers() {
        Map<Tense, Function<String, String>> mappers = new HashMap<>(Tense.values().length);
        mappers.put(TODO, name -> "待" + name);
        mappers.put(DOING, name -> name + "中");
        mappers.put(DONE, name -> "已" + name);
        mappers.put(SUCCESS, name -> name + Tense.SUCCESS.getName());
        mappers.put(FAILURE, name -> name + Tense.FAILURE.getName());
        return mappers;
    }

    /** 流程状态人类可读化 */
    static <T extends Flowable, N extends CodeCapable & NameCapable> String humanReadable(T flowable, N[] nodes, Function<T, String> converter) {
        String nodeName = StructureUtils.findNameByCode(nodes, flowable.getNodeCode());
        String name = converter != null ? converter.apply(flowable) : null;
        if (name == null) name = MAPPERS.get(Tense.valueOf(flowable.getStateCode())).apply(nodeName);
        return name + "(" + FinalState.valueOf(flowable.getFinishCode()).getName() + ")";
    }

    /** 构建转换器 */
    static <T extends Flowable> Function<T, String> buildConverter(Map<String, String> mappers) {
        return t -> mappers.getOrDefault(t.getNodeCode() + "." + t.getStateCode(), mappers.get(t.getNodeCode()));
    }
}
