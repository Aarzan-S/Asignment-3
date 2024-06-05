package org.ais.model;

import org.ais.restHandler.RecruitHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public class RecruitModel implements IModel<Recruit> {
    private LinkedList<Recruit> recruitList = new LinkedList<>();
    private LinkedList<Recruit> recruits = new LinkedList<>();
    public RecruitModel() {
//        recruitList = loadData();
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
        Optional<Recruit> recruit = recruitList.stream().filter(r -> r.getUsername().equals(username)).findFirst();
        return recruit.orElse(null);
    }

    @Override
    public LinkedList<Recruit> getAll() {
        return recruits;
    }

    @Override
    public int add(Recruit recruit) {
        recruits.add(recruit);
        return 1;
    }

    @Override
    public int update(Recruit recruit, int id) {
        RecruitHandler.updateDetailsByStaff(recruit, id);
        return 1;
    }

    @Override
    public LinkedList<Recruit> loadData() {
        if (recruitList.isEmpty())
            recruitList = RecruitHandler.fetchRecruits();
        return recruitList;
    }

    @Override
    public String register() throws IOException {
        return RecruitHandler.registerRecruit(recruits.get(0));
    }
}
