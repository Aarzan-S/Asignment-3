package org.ais.service;

import org.ais.model.Recruit;
import org.ais.model.Response;
import org.ais.model.Staff;
import org.ais.repository.RecruitRepository;
import org.ais.repository.StaffRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoginService {
    private static LoginService instance;

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public Response login(Staff user) {
        StaffRepository repository = StaffRepository.getInstance();
        String userRole = repository.doesUserExist(user);
        if (userRole != null) {
            if (repository.validatePassword(user)) {
                return new Response("Log in success", LocalDateTime.now(), "SUCCESS", userRole);
            }
            return new Response("ERROR : Incorrect Password", LocalDateTime.now(), "FAILED", "");
        }
        return new Response("ERROR : User does not exist", LocalDateTime.now(), "FAILED", "");

    }

    public Response loginRecruit(Recruit recruit, String otp) {
        if (!Objects.equals(otp, "ADMIN001")){
            return new Response("Incorrect OTP", LocalDateTime.now(), "FAILED", "");
        }
        RecruitRepository repository = RecruitRepository.getInstance();
        String userRole = repository.doesUserExist(recruit);
        if (userRole != null) {
            if (repository.validatePassword(recruit)) {
                return new Response("Log in success", LocalDateTime.now(), "SUCCESS", userRole);
            }
            return new Response("ERROR : Incorrect Password", LocalDateTime.now(), "FAILED", "");
        }
        return new Response("ERROR : User does not exist", LocalDateTime.now(), "FAILED", "");
    }
}
