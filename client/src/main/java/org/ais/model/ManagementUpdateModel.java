package org.ais.model;

import org.ais.restHandler.ManagementHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ManagementUpdateModel implements IModel<ManagementStaff> {
    private ManagementStaff managementStaff;

    public ManagementUpdateModel() {
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
    public ManagementStaff get(String username) {
        managementStaff = ManagementHandler.fetchManagementDetails(username);
        return managementStaff;
    }

    @Override
    public LinkedList<ManagementStaff> getAll() {
        return null;
    }

    @Override
    public int add(ManagementStaff adminStaff) {
        return 1;
    }

    @Override
    public int update(ManagementStaff adminStaff, int id) {
        ManagementHandler.updateDetails(adminStaff, id);
        return 1;
    }

    @Override
    public LinkedList<ManagementStaff> loadData() {
        return new LinkedList<>();
    }

    @Override
    public String register() throws IOException {
        return null;
    }

}