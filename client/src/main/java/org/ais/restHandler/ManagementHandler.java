package org.ais.restHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ais.exception.CustomException;
import org.ais.model.ManagementStaff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

public class ManagementHandler {
    public static String registerManagement(ManagementStaff mngStaff) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8000/management/registration");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            OutputStream outputStream = connection.getOutputStream();
            String jsonResponse = objectMapper.writeValueAsString(mngStaff);
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

    public static ManagementStaff fetchManagementDetails(String username) {
        HttpURLConnection connection = null;
        try {
            String encodedUsername = URLEncoder.encode(username, "UTF-8");
            URL url = new URL("http://localhost:8000/management/details?username=" + encodedUsername);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            InputStream inputStream;
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                inputStream = connection.getInputStream();
                ManagementStaff managementStaff = objectMapper.readValue(inputStream, ManagementStaff.class);
                return managementStaff;
            } else {
                return null;
            }
        } catch (IOException ex) {
            System.out.println("Could not connect to server");
            throw new CustomException("Could not connect to server");
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    public static String  updateDetails(ManagementStaff adminStaff, int id) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8000/management/update/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            OutputStream outputStream = connection.getOutputStream();
            String payload = objectMapper.writeValueAsString(adminStaff);
            outputStream.write(payload.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream;
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                return null;
            } else {
                inputStream = connection.getErrorStream();
                Response response = objectMapper.readValue(inputStream, Response.class);
                inputStream.close();
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

    public static LinkedList<ManagementStaff> fetchAll() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8000/management/getAll");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            InputStream inputStream;
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                inputStream = connection.getInputStream();
                LinkedList<ManagementStaff> managementStaffs = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                return managementStaffs;
            } else {
                inputStream = connection.getErrorStream();
                Response response = objectMapper.readValue(inputStream, Response.class);
                return null;
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
