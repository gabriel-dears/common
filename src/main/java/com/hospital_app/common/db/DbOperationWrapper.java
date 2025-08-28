package com.hospital_app.common.db;

/**
 * Utility wrapper for executing database operations with standardized exception handling.
 * <p>
 * This class is intended for external usage by projects that depend on this common module.
 * It provides a simple abstraction for executing database operations while allowing
 * the caller to specify a custom {@link RuntimeException} type for wrapping any errors.
 */
@SuppressWarnings("unused") // Used externally by consuming projects
public class DbOperationWrapper {

    /**
     * Represents a functional interface for database operations.
     *
     * @param <T> the type of the result returned by the operation
     */
    @FunctionalInterface
    public interface DbOperation<T> {
        /**
         * Executes the database operation.
         *
         * @return the result of the operation
         */
        T execute();
    }

    /**
     * Executes a database operation and wraps any thrown exception into the provided exception type.
     * <p>
     * If the thrown exception is already assignable to the specified {@code exceptionClass},
     * it is rethrown as-is. Otherwise, the method attempts to create a new instance of the
     * provided {@code exceptionClass} using a constructor that accepts
     * {@link String} (message) and {@link Throwable} (cause).
     * <p>
     * This ensures consistent error handling across projects that use this common module.
     *
     * @param operation      the database operation to execute
     * @param exceptionClass the exception class to wrap any exception thrown by the operation;
     *                       must extend {@link RuntimeException} and provide a constructor
     *                       with parameters ({@link String}, {@link Throwable})
     * @param <T>            the result type of the operation
     * @return the result of the operation
     * @throws RuntimeException if the operation fails and cannot be wrapped in the provided exception class
     */
    @SuppressWarnings("unused") // Used externally by consuming projects
    public static <T> T execute(DbOperation<T> operation, Class<? extends RuntimeException> exceptionClass) {
        try {
            return operation.execute();
        } catch (Exception e) {
            if (exceptionClass.isAssignableFrom(e.getClass())) {
                throw (RuntimeException) e;
            }
            try {
                throw  exceptionClass
                        .getConstructor(String.class, Throwable.class)
                        .newInstance("Database operation failed", e);
            } catch (ReflectiveOperationException reflectionError) {
                throw new RuntimeException(
                        "Could not instantiate exception of type: " + exceptionClass.getName(), e
                );
            }
        }
    }
}
