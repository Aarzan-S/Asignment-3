package org.ais.service;

import org.ais.model.ManagementStaff;
import org.ais.model.Response;

import java.util.LinkedList;

public interface StaffService<T> {
    LinkedList<T> getAll();

    Response register(T staff);

    Response update(T staff, int id);

    T getDetails(String username);

}