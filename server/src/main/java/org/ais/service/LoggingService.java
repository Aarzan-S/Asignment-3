package org.ais.service;

import org.ais.model.Response;
import org.ais.repository.AdminLogRepository;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * This class handles logging mechanism for admin activity
 */
public class LoggingService {
    private static LoggingService instance;
    /**
     * Creates the object of this class if not already exists
     * @return object
     */
    public static LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }

    /**
     * Handles logic to add admin activity
     * @param request
     * @return
     */
    public Response addLog(Map<String, String> request) {
        AdminLogRepository repository = AdminLogRepository.getInstance();
        repository.logAction(request);
        return new Response("Log added", LocalDateTime.now(), "SUCCESS");
    }
}
