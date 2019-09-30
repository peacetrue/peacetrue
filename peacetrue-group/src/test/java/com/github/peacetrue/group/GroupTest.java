package com.github.peacetrue.group;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
})
public class GroupTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static class GroupedRow extends com.github.peacetrue.group.GroupedRow<Long, Date> {}

    @Test
    public void basic() throws Exception {
        List<GroupedRow> groupRows = jdbcTemplate.query("SELECT creator_id id, max(created_time) value FROM shop_apply GROUP BY creator_id", new BeanPropertyRowMapper<>(GroupedRow.class));
        System.out.println(groupRows);
        
        GroupUtils.Where where = GroupUtils.buildWhere(new String[]{"creator_id", "created_time"}, groupRows.get(0));
        System.out.println(where);
        List<ShopApply> shopApplies = jdbcTemplate.query("select * from shop_apply where " + where.getSql(), where.getArgs(), new BeanPropertyRowMapper<>(ShopApply.class));
        System.out.println(shopApplies);

        ArrayList<com.github.peacetrue.group.GroupedRow> groupedRows = new ArrayList<>(groupRows);
        where = GroupUtils.buildWhere(new String[]{"creator_id", "created_time"}, groupedRows);
        System.out.println(where);
        shopApplies = jdbcTemplate.query("select * from shop_apply where " + where.getSql(), where.getArgs(), new BeanPropertyRowMapper<>(ShopApply.class));
        System.out.println(shopApplies);
    }
}
