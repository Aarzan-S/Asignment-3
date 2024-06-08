package org.ais.service;

import org.ais.model.Recruit;
import org.ais.model.Response;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Represents all the operation for the recruit such as register, update, fetch
 */
public interface RecruitService {
    /**
     * Fetch all the recruits
     * @return
     * @throws SQLException
     */
    LinkedList<Recruit> getAllRecruits() throws SQLException;

    /**
     * Update recruit details
     * @param recruit
     * @param id
     * @param isStaff
     * @return
     */
    Response updateRecruit(Recruit recruit, int id, boolean isStaff);

    /**
     * Fetch recruit history
     * @return
     */
    LinkedList<Recruit> getRecruitHistory();

    /**
     * Register/Saves recruit to database
     * @param recruit
     * @return
     */
    Response registerRecruit(Recruit recruit);

    /**
     * Update recruit details
     * @param recruit
     * @param id
     * @return
     */
    Response updateDetails(Recruit recruit, int id);

    /**
     * Fetch recruit details by username
     * @param username
     * @return
     */
    Recruit getDetails(String username);
}