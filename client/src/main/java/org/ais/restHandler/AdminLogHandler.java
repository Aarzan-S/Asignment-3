package org.ais.restHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ais.exception.CustomException;
import org.ais.model.Recruit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles admin log rest call to server
 * Establish connection to server and sends request serialized request and
 * wait for the response. Deserialize the response and handles response in meaningful way
 */
public class AdminLogHandler {
    public static String addLog(String adminUsername, String recruitUsername, String action) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8000/logging/add");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            OutputStream outputStream = connection.getOutputStream();
            Map<String, String> logDetails = new HashMap<>();
            logDetails.put("user", adminUsername);
            logDetails.put("action", action);
            logDetails.put("recruit", recruitUsername);
            String jsonResponse = objectMapper.writeValueAsString(logDetails);
            outputStream.write(jsonResponse.getBytes());
            InputStream inputStream;
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                return null;
            } else {
                inputStream = connection.getErrorStream();
                Response response = objectMapper.readValue(inputStream, Response.class);
                return response.getMessage();
            }
        } catch (IOException ex) {
            System.out.println("Could not connect to server");
            throw new CustomException("Could not connect to server");
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}
