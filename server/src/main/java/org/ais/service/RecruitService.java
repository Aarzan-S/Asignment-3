package org.ais.service;

import org.ais.model.Recruit;
import org.ais.model.Response;

import java.sql.SQLException;
import java.util.LinkedList;

public interface RecruitService {
    LinkedList<Recruit> getAllRecruits() throws SQLException;
    Response updateRecruit(Recruit recruit, int id);
    LinkedList<Recruit> getRecruitHistory();
    Response registerRecruit(Recruit recruit);
    Response updateDetails(Recruit recruit, int id);
    Recruit getDetails(String username);
}