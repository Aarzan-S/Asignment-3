package org.ais.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ais.model.AdminStaff;
import org.ais.model.Response;
import org.ais.service.AdminServiceImpl;
import org.ais.util.QueryParamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * This class handles all the admin requests
 * handles requests based on method type and path
 */
public class AdminHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String path = exchange.getRequestURI().getPath();
        AdminServiceImpl adminService = AdminServiceImpl.getInstance();
        if ("POST".equals(exchange.getRequestMethod()) && path.equals("/admin/registration")) {
            try {
                InputStream inputStream = exchange.getRequestBody();
                AdminStaff adminStaff = objectMapper.readValue(inputStream, AdminStaff.class);
                Response response = adminService.register(adminStaff);
                String jsonResponse = objectMapper.writeValueAsString(response);
                if ("SUCCESS".equals(response.getStatus())) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(jsonResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, jsonResponse.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(jsonResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                System.out.println("Could not register admin");
            }
        } else if ("GET".equals(exchange.getRequestMethod())) {
            if (path.startsWith("/admin/details")) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = QueryParamUtil.queryToMap(query);
                String username = params.get("username");
                try {
                    AdminStaff response = adminService.getDetails(username);
                    String jsonResponse = objectMapper.writeValueAsString(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(jsonResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("Could not fetch admin");
                }
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            if (path.startsWith("/admin/update")) {
                int id = Integer.parseInt(path.split("/")[3]);
                try {
                    InputStream inputStream = exchange.getRequestBody();
                    AdminStaff adminStaff = objectMapper.readValue(inputStream, AdminStaff.class);
                    Response response = adminService.update(adminStaff, id);
                    String jsonResponse = objectMapper.writeValueAsString(response);
                    if ("SUCCESS".equals(response.getStatus())) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(jsonResponse.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, jsonResponse.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(jsonResponse.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Could not update Admin data");
                }
            }
        } else {
            exchange.sendResponseHeaders(404, 0);
        }
    }

}
