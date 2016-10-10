package com.tidyjava.bp.util;

public class ExceptionUtils {

    public static <T> T rethrow(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void rethrow(ThrowingRunnable runnable) {
        try {
            runnable.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void execute() throws Exception;
    }
}
