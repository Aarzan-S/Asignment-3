package org.ais.model;

import org.ais.Main;
import org.ais.restHandler.AdminHandler;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdminStaffModel implements IModel<AdminStaff> {
    private LinkedList<AdminStaff> adminStaffs = new LinkedList<>();

    private LinkedList<AdminStaff> admins = new LinkedList<>();

    public AdminStaffModel() {
//        this.adminStaffs.addAll(loadData());
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
    public AdminStaff get(String username) {
        Optional<AdminStaff> adminStaff = adminStaffs.stream().filter(admin -> admin.getUsername().equals(username)).findFirst();
        return adminStaff.orElse(null);
    }

    @Override
    public LinkedList<AdminStaff> getAll() {
        return admins;
    }

    @Override
    public int add(AdminStaff adminStaff) {
        admins.add(0,adminStaff);
        return 1;
    }

    @Override
    public int update(AdminStaff adminStaff, int index) {
        return 1;
    }

    @Override
    public LinkedList<AdminStaff> loadData() {
        return new LinkedList<>();
    }

    @Override
    public String register() throws IOException {
        return AdminHandler.registerAdmin(admins.get(0));
    }
}
