package org.ais.service;

import org.ais.model.AdminStaff;
import org.ais.model.Response;
import org.ais.repository.AdminRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class AdminServiceImpl implements StaffService<AdminStaff> {

    private static AdminServiceImpl instance;

    public static AdminServiceImpl getInstance() {
        if (instance == null) {
            instance = new AdminServiceImpl();
        }
        return instance;
    }

    @Override
    public LinkedList<AdminStaff> getAll() {
        return null;
    }

    @Override
    public Response register(AdminStaff staffs) {
        AdminRepository repository = AdminRepository.getInstance();
        if (repository.doesUserExists(staffs)) {
            return new Response("ERROR: user already registered", LocalDateTime.now(), "FAILED", "");
        }
        repository.register(staffs);
        return new Response("Admin successfully registered", LocalDateTime.now(), "SUCCESS", "");
    }

    @Override
    public Response update(AdminStaff staff, int id) {
        AdminRepository repository = AdminRepository.getInstance();
        repository.update(staff, id);
        return new Response("Admin staff data updated", LocalDateTime.now(), "SUCCESS", "");
    }

    @Override
    public AdminStaff getDetails(String username) {
        AdminRepository repository = AdminRepository.getInstance();
        return repository.getAdminDetails(username);
    }
}