package org.ais.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface IModel<T> {
    int connect();
    int disconnect();
    T get(String name);
    LinkedList<T> getAll();;
    int add(T subject);
    int update(T subject, int index);
    LinkedList<T> loadData();
    String register() throws IOException;
}
