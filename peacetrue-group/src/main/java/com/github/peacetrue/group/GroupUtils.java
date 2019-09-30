package com.github.peacetrue.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
public abstract class GroupUtils {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Where {
        private String sql;
        private Object[] args;
    }

    public static Where buildWhere(String[] columnNames, GroupedRow groupedRow) {
        return new Where(String.format("%s=? and %s=?", columnNames), new Object[]{groupedRow.getId(), groupedRow.getValue()});
    }

    public static Where buildWhere(String[] columnNames, List<? extends GroupedRow> groupedRows) {
        List<Where> collect = groupedRows.stream().map(groupedRow -> buildWhere(columnNames, groupedRow)).collect(Collectors.toList());
        return new Where(
                collect.stream().map(Where::getSql).collect(Collectors.joining(") or (", "(", ")")),
                collect.stream().flatMap(where -> Arrays.stream(where.getArgs())).toArray()
        );
    }
}
