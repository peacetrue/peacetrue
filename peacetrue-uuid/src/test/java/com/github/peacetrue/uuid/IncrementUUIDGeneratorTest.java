package com.github.peacetrue.uuid;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * tests for {@link IncreaseUUIDGenerator}
 *
 * @author xiayx
 */
public class IncrementUUIDGeneratorTest {

    private IncreaseUUIDGenerator generator = new IncreaseUUIDGenerator();

    @Test
    public void next() throws Exception {
        Stream.iterate(0, integer -> integer + 1).limit(1000).forEach(integer -> {
            List<Long> values = Stream.generate(() -> generator.next()).limit(1000).collect(Collectors.toList());
            Assert.assertEquals(values.size(), new HashSet<>(values).size());
        });
    }

    @Test
    public void get() throws Exception {
        System.out.println(generator.next());
        //yyMMddHHmmssSSS
        //1531541635737
        Assert.assertEquals(generator.get(), generator.get());
    }

    @Test
    public void reset() throws Exception {
        Long value = generator.next();
        Assert.assertNotEquals(value, generator.next());
        generator.reset();
        Assert.assertEquals(value, generator.next());

    }

    @Test
    public void name() throws Exception {
        Stream.generate(() -> new Thread(() -> {
            while (true) {
                List<String> names = Arrays.asList("李国华", "李清照", "王志刚", "王天昊", "张雪松", "张婷婷");
                Map<String, List<String>> groupedNames = names.stream().collect(Collectors.groupingBy(name -> name.substring(0, 1)));
                System.out.println(groupedNames);
            }
        })).limit(100).forEach(Thread::start);
    }

    @Test
    public void name2() throws Exception {
//        List<Long> currentTimeMillis = Stream.generate(System::currentTimeMillis).limit(1000).collect(Collectors.toList());
//        System.out.println(currentTimeMillis);
//
//        List<Long> collect = Stream.generate(System::nanoTime).limit(1000).collect(Collectors.toList());
//        System.out.println(collect);

        System.out.println(new Object().hashCode());
        System.out.println(new Object().hashCode());
    }

    public static void main(String[] args) throws Exception {
        List<String> list = Stream.generate(() -> UUID.randomUUID().toString()).limit(100).collect(Collectors.toList());
        List<String> sortList = new ArrayList<>(list);
        System.out.println(list.equals(sortList));
        sortList.sort(String::compareTo);
        System.out.println(list.equals(sortList));
    }
}