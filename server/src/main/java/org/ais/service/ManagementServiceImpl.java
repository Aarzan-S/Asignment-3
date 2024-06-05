package org.ais.service;

import org.ais.model.ManagementStaff;
import org.ais.model.Response;
import org.ais.repository.AdminRepository;
import org.ais.repository.ManagementRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ManagementServiceImpl implements StaffService<ManagementStaff> {

    private static ManagementServiceImpl instance;

    public static ManagementServiceImpl getInstance() {
        if (instance == null) {
            instance = new ManagementServiceImpl();
        }
        return instance;
    }
    @Override
    public LinkedList<ManagementStaff> getAll() {
        ManagementRepository repository = ManagementRepository.getInstance();
        return repository.fetchManagementStaff();
    }

    @Override
    public Response register(ManagementStaff staffs) {
        ManagementRepository repository = ManagementRepository.getInstance();;
        if (repository.doesUserExists(staffs)){
            return new Response("ERROR: user already registered", LocalDateTime.now(), "FAILED");
        }
        repository.register(staffs);
        return new Response("Management staff successfully registered", LocalDateTime.now(), "SUCCESS");
    }
    @Override
    public Response update(ManagementStaff staff, int id) {
        ManagementRepository repository = ManagementRepository.getInstance();;
        repository.update(staff, id);
        return new Response("Management staff data updated", LocalDateTime.now(), "SUCCESS");
    }

    @Override
    public ManagementStaff getDetails(String username) {
        ManagementRepository repository = ManagementRepository.getInstance();;
        return repository.getManagementDetails(username);
    }

}