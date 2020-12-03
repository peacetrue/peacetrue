package com.github.peacetrue.tree.menu;

import com.github.peacetrue.tree.GenericTree;
import com.github.peacetrue.tree.generator.TreeNodeBuilder;
import com.github.peacetrue.tree.generator.TreeNodeGenerator;
import org.junit.jupiter.api.Test;

/**
 * 讲述树节点在菜单中的应用
 *
 * @author xiayx
 */
public class MenuTest {

    @Test
    public void node() throws Exception {
        NodeMenu menu = new NodeMenu(1L);
        NodeMenu menu1 = new NodeMenu(menu, 2L);
//        NodeMenu clone1 = (NodeMenu) clone.invoke(menu1);
//        System.out.println(clone1.getChildren() == menu1.getChildren());
//        System.out.println(menu);
    }

    @Test
    public void parentId() throws Exception {
        TreeNodeGenerator<ParentIdMenu> generator = new TreeNodeGenerator<>(3, 3, new TreeNodeBuilder<ParentIdMenu>() {
            private long i = 0;

            public ParentIdMenu build(ParentIdMenu parent, int index, int level) {
                return new ParentIdMenu(parent == null ? null : parent.getId(), ++i, index);
            }
        });
        GenericTree<ParentIdMenu> menuTree = new GenericTree<>(
                node -> node.getParentId() == null,
                (parent, child) -> parent.getId().equals(child.getParentId()),
                generator.generate());
        System.out.println(menuTree);
    }
}
