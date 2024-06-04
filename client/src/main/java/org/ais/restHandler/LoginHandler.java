package org.ais.restHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ais.exception.CustomException;
import org.ais.model.Staff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginHandler {
    public static String authenticateUser(Staff staff) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8000/login/staff");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            OutputStream outputStream = connection.getOutputStream();
            String jsonResponse = objectMapper.writeValueAsString(staff);
            outputStream.write(jsonResponse.getBytes());
            InputStream inputStream;
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                inputStream = connection.getInputStream();
                Response response = objectMapper.readValue(inputStream, Response.class);
                return response.getRole();
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
