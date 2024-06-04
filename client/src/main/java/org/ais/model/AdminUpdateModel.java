package org.ais.model;

import org.ais.restHandler.AdminHandler;

import java.io.IOException;
import java.util.LinkedList;

public class AdminUpdateModel implements IModel<AdminStaff> {
    private AdminStaff admin;

    public AdminUpdateModel() {
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
        admin = AdminHandler.fetchAdminDetails(username);
        return admin;
    }

    @Override
    public LinkedList<AdminStaff> getAll() {
        return null;
    }

    @Override
    public int add(AdminStaff adminStaff) {
        return 1;
    }

    @Override
    public int update(AdminStaff adminStaff, int id) {
        AdminHandler.updateDetails(adminStaff, id);
        return 1;
    }

    @Override
    public LinkedList<AdminStaff> loadData() {
        return new LinkedList<>();
    }

    @Override
    public String register() throws IOException {
        return null;
    }

}
