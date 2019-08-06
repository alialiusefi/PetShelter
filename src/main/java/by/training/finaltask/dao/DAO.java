package by.training.finaltask.dao;

import by.training.finaltask.exception.PersistentException;


public interface DAO<T> {

    String UNSUPPORTEDOPERATION = "unsupportedOperation";

    default int add(T element) throws PersistentException {
        throw new PersistentException(UNSUPPORTEDOPERATION);
    }

    default T get() throws PersistentException {
        throw new PersistentException(UNSUPPORTEDOPERATION);
    }

    default boolean update(T element) throws PersistentException {
        throw new PersistentException(UNSUPPORTEDOPERATION);
    }

    default boolean delete(T element) throws PersistentException {
        throw new PersistentException(UNSUPPORTEDOPERATION);
    }

}
