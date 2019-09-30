package com.github.peacetrue.tree.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 树节点生成器，生成一组符合条件的树节点，用作测试数据。
 * <p>
 * 生成的节点数目(等比数列)：<br>
 * S=count^0 + count^1 + count^2 + .... + count^level = (1-count^level+1)/(1-count)
 *
 * @author xiayx
 */
public class TreeNodeGenerator<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /** 树所拥有的层级数，不包含根节点 */
    private int level;
    /** 每层的数目 */
    private int count;
    /** 树节点构造器 */
    private TreeNodeBuilder<T> treeNodeBuilder;

    /**
     * @param level           大于0
     * @param count           大于0
     * @param treeNodeBuilder 不能为null
     */
    public TreeNodeGenerator(int level, int count, TreeNodeBuilder<T> treeNodeBuilder) {
        this.level = level;
        this.count = count;
        this.treeNodeBuilder = treeNodeBuilder;
    }

    public List<T> generate() {
        logger.info("生成树节点集合");
        List<T> nodes = new LinkedList<>();
        int level = 0;
        generate(nodes, treeNodeBuilder.build(null, 0, level), level);
        logger.debug("得到树节点集合：{}", nodes);
        return nodes;
    }

    /** 递归生成节点 */
    protected void generate(List<T> nodes, T node, int level) {
        logger.debug("生成第{}层节点：{}", level, node);
        nodes.add(node);

        int _level = level + 1;
        if (_level > this.level) {
            logger.trace("超出最大层级'{}'", this.level);
            return;
        }

        IntStream.range(0, count)
                .mapToObj(value -> treeNodeBuilder.build(node, value, _level))
                .forEach(t1 -> generate(nodes, t1, _level));
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
