package org.ais.service;

import org.ais.model.Response;

import java.util.LinkedList;
/**
 * Represents all the operation for the staffs(Admin and Management) such as register, update, fetch
 */
public interface StaffService<T> {
    /**
     * Fetch all staff details
     * @return
     */
    LinkedList<T> getAll();

    /**
     * Register/Add staff details
     * @param staff
     * @return
     */

    Response register(T staff);

    /**
     * Update staff details
     * @param staff
     * @param id
     * @return
     */
    Response update(T staff, int id);

    /**
     * Fetch staff details
     * @param username
     * @return
     */

    T getDetails(String username);

}