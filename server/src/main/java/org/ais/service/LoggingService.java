package org.ais.service;

import org.ais.model.Response;
import org.ais.repository.AdminLogRepository;

import java.time.LocalDateTime;
import java.util.Map;

public class LoggingService {
    private static LoggingService instance;

    public static LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }

    public Response addLog(Map<String, String> request) {
        AdminLogRepository repository = AdminLogRepository.getInstance();
        repository.logAction(request);
        return new Response("Log added", LocalDateTime.now(), "SUCCESS");
    }
}
