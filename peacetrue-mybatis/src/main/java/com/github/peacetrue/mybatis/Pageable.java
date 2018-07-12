package com.github.peacetrue.mybatis;

import com.github.pagehelper.ISelect;

import java.io.Serializable;

/**
 * 本来想直接使用{@link com.github.pagehelper.Page}，
 * 但存在以下问题：<br>
 * dubbo传递参数时数据丢失，推测是它的set方法不规范，导致无法设值<br>
 * spring参数绑定时，出现'Cannot generate variable name for non-typed Collection parameter type'
 * <p>
 * 所以重新定义一个分页参数类
 *
 * @author xiayx
 * @see com.github.pagehelper.Page
 */
public class Pageable implements Serializable {

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 包含count查询
     */
    private boolean count = true;
    /**
     * 分页合理化
     */
    private Boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    private Boolean pageSizeZero;
    /**
     * 进行count查询的列名
     */
    private String countColumn;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 只增加排序
     */
    private boolean orderByOnly;

    public Pageable() {
        super();
    }

    public Pageable(int pageNum, int pageSize) {
        this(pageNum, pageSize, true, null);
    }

    public Pageable(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, count, null);
    }

    private Pageable(int pageNum, int pageSize, boolean count, Boolean reasonable) {

        if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {
            pageSizeZero = true;
            pageSize = 0;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.count = count;
        calculateStartAndEndRow();
        setReasonable(reasonable);
    }

    /**
     * int[] rowBounds
     * 0 : offset
     * 1 : limit
     */
    public Pageable(int[] rowBounds, boolean count) {

        if (rowBounds[0] == 0 && rowBounds[1] == Integer.MAX_VALUE) {
            pageSizeZero = true;
            this.pageSize = 0;
        } else {
            this.pageSize = rowBounds[1];
            this.pageNum = rowBounds[1] != 0 ? (int) (Math.ceil(((double) rowBounds[0] + rowBounds[1]) / rowBounds[1])) : 0;
        }
        this.startRow = rowBounds[0];
        this.count = count;
        this.endRow = this.startRow + rowBounds[1];
    }


    public int getPages() {
        return pages;
    }


    public int getPageNum() {
        return pageNum;
    }


    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getStartRow() {
        return startRow;
    }


    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }


    public int getEndRow() {
        return endRow;
    }


    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }


    public void setPages(int pages) {
        this.pages = pages;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1) {
            pages = 1;
            return;
        }
        if (pageSize > 0) {
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            pages = 0;
        }
        //分页合理化，针对不合理的页码自动处理
        if ((reasonable != null && reasonable) && pageNum > pages) {
            pageNum = pages;
            calculateStartAndEndRow();
        }
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        if (reasonable == null) {
            return;
        }
        this.reasonable = reasonable;
        //分页合理化，针对不合理的页码自动处理
        if (this.reasonable && this.pageNum <= 0) {
            this.pageNum = 1;
            calculateStartAndEndRow();
        }
        return;
    }

    public Boolean getPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(Boolean pageSizeZero) {
        if (pageSizeZero != null) {
            this.pageSizeZero = pageSizeZero;
        }
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isOrderByOnly() {
        return orderByOnly;
    }

    public void setOrderByOnly(boolean orderByOnly) {
        this.orderByOnly = orderByOnly;
    }

    /**
     * 计算起止行号
     */
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
    }

    public boolean isCount() {
        return this.count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    /**
     * 设置页码
     *
     * @param pageNum
     * @return
     */
    public void pageNum(int pageNum) {
        //分页合理化，针对不合理的页码自动处理
        this.pageNum = ((reasonable != null && reasonable) && pageNum <= 0) ? 1 : pageNum;
    }

    /**
     * 设置页面大小
     *
     * @param pageSize
     * @return
     */
    public void pageSize(int pageSize) {
        this.pageSize = pageSize;
        calculateStartAndEndRow();
    }

    /**
     * 是否执行count查询
     *
     * @param count
     * @return
     */
    public void count(Boolean count) {
        this.count = count;
    }

    /**
     * 设置合理化
     *
     * @param reasonable
     * @return
     */
    public void reasonable(Boolean reasonable) {
        setReasonable(reasonable);

    }

    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     *
     * @param pageSizeZero
     * @return
     */
    public void pageSizeZero(Boolean pageSizeZero) {
        setPageSizeZero(pageSizeZero);

    }

    /**
     * 指定 count 查询列
     *
     * @param columnName
     * @return
     */
    public void countColumn(String columnName) {
        this.countColumn = columnName;

    }


    public long doCount(ISelect select) {
        this.pageSizeZero = true;
        this.pageSize = 0;
        select.doSelect();
        return this.total;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }


    public String toString() {
        return "Page{" +
                "count=" + count +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", reasonable=" + reasonable +
                ", pageSizeZero=" + pageSizeZero +
                '}';
    }

}
