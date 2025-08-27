package com.hospital_app.common.db;

public class DbOperationWrapper {

    @FunctionalInterface
    public interface DbOperation<T> {
        T execute();
    }

    public static <T> T execute(DbOperation<T> operation, Class<? extends RuntimeException> exceptionClass) {
        try {
            return operation.execute();
        } catch (Exception e) {
            if (exceptionClass.isAssignableFrom(e.getClass())) {
                throw (RuntimeException) e;
            }
            try {
                throw exceptionClass
                        .getConstructor(String.class, Throwable.class)
                        .newInstance("Database operation failed", e);
            } catch (ReflectiveOperationException reflectionError) {
                throw new RuntimeException("Could not instantiate exception " + exceptionClass, e);
            }
        }
    }


}
