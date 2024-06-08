package org.ais.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents common operation on the model classes such as get, getAll, update, add, register
 * @param <T>
 */
public interface IModel<T> {
    int connect();
    int disconnect();

    /**
     * GFetches user by username
     * @param name
     * @return
     */
    T get(String name);

    /**
     * Fetches all user
     * @return
     */
    LinkedList<T> getAll();;
    int add(T subject);

    /**
     * Update user
     * @param subject
     * @param index
     * @return
     */
    int update(T subject, int index);
    LinkedList<T> loadData();

    /**
     * Register user
     * @return
     * @throws IOException
     */
    String register() throws IOException;
}
