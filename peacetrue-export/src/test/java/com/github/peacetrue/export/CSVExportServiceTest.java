package com.github.peacetrue.export;

import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVExportServiceTest {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString(exclude = "parent")
    @EqualsAndHashCode(exclude = "parent")
    public static class Person {
        private String name;
        private Integer age;
        private Boolean isMan;
        private Date birthday;
        private Person parent;

        public Person(String name, Integer age, Boolean isMan, Date birthday) {
            this.name = name;
            this.age = age;
            this.isMan = isMan;
            this.birthday = birthday;
        }
    }

    //tag::csv[]
    @Autowired
    private ExportService exportService;

    @Test
    public void export() throws Exception {
        String path = "/Users/xiayx/Documents/Projects/peacetrue/peacetrue-export/src/test/resources/TW2019010397.csv";
        FileOutputStream outputStream = new FileOutputStream(path);

        List<Person> peoples = Arrays.asList(
                new Person("张三", 17, true, new Date()),
                new Person("李四", 18, true, new Date()),
                new Person("王五", 19, false, new Date())
        );
        IntStream.range(0, 2).forEach(i -> peoples.get(i).setParent(peoples.get(i)));

        Header header = HeaderBuilder.builder()
                .appendRaw("姓名", "name")
                .appendRaw("年龄", "age")
                .appendBoolean("是否男性", "isMan")
                .appendDate("生日", "birthday").prependPrefix()
                .appendRaw("父-姓名", "parent.name")
                .appendRaw("父-年龄", "parent.age")
                .appendBoolean("父-是否男性", "parent.isMan")
                .appendDate("父-生日", "parent.birthday").prependPrefix()
                .<Person>append("自定义内容", p -> Boolean.TRUE.equals(p.getIsMan()) ? ("肌肉发达的" + p.getName()) : ("善解人意的" + p.getName()))
                .build();
        exportService.export(new OutputStreamWriter(outputStream, "GBK"), header, peoples);
    }
    //end::csv[]
}
