package org.ais.view;

public interface IView<T> {
    void display(T object);
    void display(String message, String type);
    void clearInputs();
}