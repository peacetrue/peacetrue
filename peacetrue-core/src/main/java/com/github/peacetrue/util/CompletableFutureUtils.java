package com.github.peacetrue.util;

import lombok.Data;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : xiayx
 * @since : 2020-09-20 16:09
 **/
public abstract class CompletableFutureUtils {

    /** @see CompletableFuture#supplyAsync(Supplier, Executor) */
    public static <T> CompletableFuture<List<T>> supplyAsync(List<Supplier<T>> suppliers, @Nullable Executor executor) {
        List<CompletableFuture<T>> futures = suppliers.stream()
                .map(supplier -> executor == null
                        ? CompletableFuture.supplyAsync(supplier)
                        : CompletableFuture.supplyAsync(supplier, executor))
                .collect(Collectors.toList());
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply((voids) -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    /** change a callback mode to CompletableFuture */
    public static <T> CompletableFuture<T> supplyAsync(Consumer<Consumer<T>> consumer, @Nullable Executor executor) {
        ValueWrapper<T> wrapper = new ValueWrapper<>();
        consumer.accept(value -> {
            wrapper.setValue(value);
            wrapper.setOver(true);
        });
        return getCompletableFuture(wrapper, executor);
    }

    private static <T> CompletableFuture<T> getCompletableFuture(ValueWrapper<T> wrapper, @Nullable Executor executor) {
        return executor == null
                ? CompletableFuture.supplyAsync(wrapper::getValue)
                : CompletableFuture.supplyAsync(wrapper::getValue, executor);
    }

    public static <T> CompletableFuture<T> supplyAsync(Function<Runnable, T> function, @Nullable Executor executor) {
        ValueWrapper<T> wrapper = new ValueWrapper<>();
        //return now but not usable until callback invoke, e.g. ZooKeeper
        wrapper.setValue(function.apply(() -> wrapper.setOver(true)));
        return getCompletableFuture(wrapper, executor);
    }

    private static <T> Consumer<Consumer<T>> runnableToConsumer(Consumer<Runnable> runnableConsumer) {
        return tConsumer -> runnableConsumer.accept(() -> tConsumer.accept(null));
    }

    public static CompletableFuture<Void> runAsync(Consumer<Runnable> consumer, @Nullable Executor executor) {
        return supplyAsync(runnableToConsumer(consumer), executor);
    }


    /**
     * @author : xiayx
     * @since : 2020-09-30 14:10
     **/
    @Data
    public static class ValueWrapper<T> {
        private volatile boolean over;//default to false
        private T value;//default to null

        public T getValue() {
            while (true) if (over) return value;
        }

        public T getValue(long timeout, TimeUnit timeUnit) throws TimeoutException {
            long start = System.nanoTime();
            long timeoutNanos = timeUnit.toNanos(timeout);
            while (true) {
                if (over) return value;
                long pass = System.nanoTime() - start;
                if (pass > timeoutNanos) throw new TimeoutException();
            }
        }
    }
}
