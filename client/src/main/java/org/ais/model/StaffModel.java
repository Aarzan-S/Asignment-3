package org.ais.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public class StaffModel implements IModel<Staff> {
    private LinkedList<Staff> staffs = new LinkedList<>();

    public StaffModel() {
        staffs.addAll(loadData());
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
    public Staff get(String username) {
        Optional<Staff> staff = staffs.stream().filter(s -> s.getUsername().equals(username)).findFirst();
        return staff.orElse(null);
    }

    @Override
    public LinkedList<Staff> getAll() {
        return staffs;
    }

    @Override
    public int add(Staff staff) {
        return 1;
    }

    @Override
    public int update(Staff staff, int index) {
        return 1;
    }

    @Override
    public LinkedList<Staff> loadData() {
        return new LinkedList<>();
    }

    @Override
    public String register() throws IOException {
        return null;
    }
}
