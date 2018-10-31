package com.github.peacetrue.tree.menu;

/**
 * 菜单类，基于{@link #parentId}关联，仅保留关键字段
 *
 * @author xiayx
 * @see NodeMenu
 */
public class ParentIdMenu {

    private Long id;
    private Long parentId;
    private Integer sort;

    public ParentIdMenu(Long id, Integer sort) {
        this.id = id;
        this.sort = sort;
    }

    public ParentIdMenu(Long parentId, Long id, Integer sort) {
        this(id, sort);
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParentIdMenu && ((ParentIdMenu) obj).getId().equals(id);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", sort=" + sort +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
