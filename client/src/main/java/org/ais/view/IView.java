package org.ais.view;

/**
    This represents the base logic for view
    handles displaying message and cleaning input fields
 */
public interface IView<T> {
    void display(String message, String type);
    void clearInputs();
}