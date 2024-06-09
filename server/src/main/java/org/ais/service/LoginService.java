 package org.ais.service;

import org.ais.model.Recruit;
import org.ais.model.Response;
import org.ais.model.Staff;
import org.ais.repository.RecruitRepository;
import org.ais.repository.StaffRepository;

import java.time.LocalDateTime;
import java.util.Objects;
/**
 * This class handles authentication of user
 */
public class LoginService {
    private static LoginService instance;
    /**
     * Creates the object of this class if not already exists
     * @return object
     */
    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    /**
     * Handles authentication logic for staff login
     * @param user
     * @return
     */
    public Response login(Staff user) {
        StaffRepository repository = StaffRepository.getInstance();
        String userRole = repository.doesUserExist(user);
        if (userRole != null) {
            if (repository.validatePassword(user)) {
                return new Response("Log in success", LocalDateTime.now(), "SUCCESS", userRole);
            }
            return new Response("ERROR : Incorrect Password", LocalDateTime.now(), "FAILED");
        }
        return new Response("ERROR : User does not exist", LocalDateTime.now(), "FAILED");

    }

    /**
     * Handles authentication logic for recruit login
     * @param recruit
     * @param otp
     * @return
     */
    public Response loginRecruit(Recruit recruit, String otp) {
        RecruitRepository repository = RecruitRepository.getInstance();
        int recruitId = repository.getRecruitId(recruit.getUsername());
        if (recruitId != 0) {
            if (repository.validatePassword(recruit)) {
                if(!repository.validateOTP(recruitId, Integer.parseInt(otp))){
                    return new Response("ERROR: Incorrect OTP", LocalDateTime.now(), "FAILED");
                }
                repository.invalidateOtp(recruitId, Integer.parseInt(otp));
                return new Response("Log in success", LocalDateTime.now(), "SUCCESS", "Recruit");
            }
            return new Response("ERROR :Incorrect Password", LocalDateTime.now(), "FAILED");
        }
        return new Response("ERROR :User does not exist", LocalDateTime.now(), "FAILED");

    }
}
