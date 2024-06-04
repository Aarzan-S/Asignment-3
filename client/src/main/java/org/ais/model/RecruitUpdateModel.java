package org.ais.model;

import org.ais.restHandler.RecruitHandler;

import java.io.IOException;
import java.util.LinkedList;

public class RecruitUpdateModel implements IModel<Recruit> {
    private Recruit recruit;

    public RecruitUpdateModel() {
    }

    @Override
    public int connect() {
        return 1;
    }

    @Override
    public int disconnect() {
        return 0;
    }

    @Override
    public Recruit get(String username) {
        recruit = RecruitHandler.fetchRecruitDetails(username);
        return recruit;
    }

    @Override
    public LinkedList<Recruit> getAll() {
        return null;
    }

    @Override
    public int add(Recruit adminStaff) {
        return 1;
    }

    @Override
    public int update(Recruit recruit, int id) {
        RecruitHandler.updateDetails(recruit, id);
        return 1;
    }

    @Override
    public LinkedList<Recruit> loadData() {
        return new LinkedList<>();
    }

    @Override
    public String register() throws IOException {
        return null;
    }
}
