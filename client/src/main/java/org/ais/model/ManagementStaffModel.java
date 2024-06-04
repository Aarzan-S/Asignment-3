package org.ais.model;

import org.ais.restHandler.ManagementHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public class ManagementStaffModel implements IModel<ManagementStaff> {
    private LinkedList<ManagementStaff> managementStaffs = new LinkedList<>();
    private LinkedList<ManagementStaff> mngts = new LinkedList<>();

    public ManagementStaffModel() {
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
        Optional<ManagementStaff> adminStaff = managementStaffs.stream().filter(mng -> mng.getUsername().equals(username)).findFirst();
        return adminStaff.orElse(null);
    }

    @Override
    public LinkedList<ManagementStaff> getAll() {
        return mngts;
    }

    @Override
    public int add(ManagementStaff managementStaff) {
        mngts.add(managementStaff);
        return 1;
    }

    @Override
    public int update(ManagementStaff managementStaff, int index) {
        return 1;
    }

    @Override
    public LinkedList<ManagementStaff> loadData() {
        return ManagementHandler.fetchAll();
    }

    @Override
    public String register() throws IOException {
        return ManagementHandler.registerManagement(mngts.get(0));
    }
}
