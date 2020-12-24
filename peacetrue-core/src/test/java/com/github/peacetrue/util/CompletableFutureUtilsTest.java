package com.github.peacetrue.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : xiayx
 * @since : 2020-09-30 08:11
 **/
class CompletableFutureUtilsTest {

    /** 阻塞代码 */
    public static int sumBlock(int a, int b) {
        try {
            Thread.sleep(1_000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return a + b;
    }

    /** 阻塞后续代码执行，变回调写法 */
    public static void sumCallback(int a, int b, Consumer<Integer> consumer) {
        new Thread(() -> consumer.accept(sumBlock(a, b))).start();
    }

    public static class ValueWrapper<T> {
        private volatile boolean over;
        private T value;
    }

    /** 回调又希望能直接返回值 */
    public static int sumCallbackBlock(int a, int b) {
        ValueWrapper<Integer> wrapper = new ValueWrapper<>();
        Thread thread = Thread.currentThread();
        sumCallback(a, b, sum -> {
            wrapper.value = sum;
            wrapper.over = true;
            LockSupport.unpark(thread);
        });
        LockSupport.park();
        return wrapper.value;
    }

    /** 如果回调一直不执行，希望能支持超时 */
    public static int sumCallbackBlock(int a, int b, long timeout, TimeUnit timeUnit) throws TimeoutException {
        ValueWrapper<Integer> wrapper = new ValueWrapper<>();
        sumCallback(a, b, sum -> {
            wrapper.value = sum;
            wrapper.over = true;
        });
        long start = System.nanoTime();
        long timeoutNanos = timeUnit.toNanos(timeout);
        while (true) {
            if (wrapper.over) return wrapper.value;
            long pass = System.nanoTime() - start;
            if (pass > timeoutNanos) throw new TimeoutException();
        }
    }

    public static CompletableFuture<Integer> sumCompletableFuture(int a, int b) {
        return CompletableFutureUtils.supplyAsync(consumer -> {
            consumer.accept(sumBlock(a, b));
        }, executor);
    }

    public static Integer invoke(Integer integer, Consumer<Integer> consumer) {
        System.out.println("do something");

        new Thread(() -> {
            try {
                Thread.sleep(integer * 1_000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            consumer.accept(integer);
            System.out.println("do something in callback over");
        }).start();
        return null;
    }

    @Test
    void testSumBlock() {
        int sum = sumBlock(1, 1);
        Assertions.assertEquals(2, sum);
    }

    @Test
    void testSumCallback() {
        Thread thread = Thread.currentThread();
        sumCallback(1, 1, value -> {
            Assertions.assertEquals(2, value);
            LockSupport.unpark(thread);
        });
        LockSupport.park();
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Test
    void supplyAsync() {
        List<Integer> integers = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        List<Supplier<Integer>> suppliers = integers.stream()
                .map(i -> (Supplier<Integer>) () -> {
                    try {
                        Thread.sleep(500 * i);
                    } catch (InterruptedException ignored) {

                    }
                    return i;
                }).collect(Collectors.toList());
        Thread thread = Thread.currentThread();

        CompletableFutureUtils.supplyAsync(suppliers, executor)
                .whenComplete((objects, throwable) -> {
                    Assertions.assertEquals(integers, objects);
                    LockSupport.unpark(thread);
                });
        LockSupport.park();
    }


    public static CompletableFuture<Void> invokeCompletableFuture(Integer integer) {
        return CompletableFutureUtils.runAsync(consumer -> invoke(integer, i -> consumer.run()), executor);
    }

    @Test
    void toCompletableFuture() {
        Thread thread = Thread.currentThread();
        invoke(1, integer -> {
            Assertions.assertEquals(1, integer);
            LockSupport.unpark(thread);
        });
        LockSupport.park();

        invokeCompletableFuture(1)
                .whenComplete((integer, throwable) -> {
                    Assertions.assertNull(integer);
                    LockSupport.unpark(thread);
                });
        LockSupport.park();
    }

    @Test
    void testToCompletableFuture() {
    }
}
