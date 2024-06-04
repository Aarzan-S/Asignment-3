package org.ais.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ais.model.AdminStaff;
import org.ais.model.ManagementStaff;
import org.ais.model.Recruit;
import org.ais.model.Response;
import org.ais.service.ManagementServiceImpl;
import org.ais.service.RecruitServiceImpl;
import org.ais.util.QueryParamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManagementHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String path = exchange.getRequestURI().getPath();
        ManagementServiceImpl mngService = ManagementServiceImpl.getInstance();
        if ("POST".equals(exchange.getRequestMethod()) && path.equals("/management/registration")) {
            try {
                InputStream inputStream = exchange.getRequestBody();
                ManagementStaff adminStaff = objectMapper.readValue(inputStream, ManagementStaff.class);
                Response response = mngService.register(adminStaff);
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
            if (path.equals("/management/getAll")) {
                try {
                    LinkedList<ManagementStaff> response = mngService.getAll();
                    String jsonResponse = objectMapper.writeValueAsString(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(jsonResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("Could not fetch management staffs");
                }
            } else if (path.startsWith("/management/details")) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = QueryParamUtil.queryToMap(query);
                String username = params.get("username");
                try {
                    ManagementStaff response = mngService.getDetails(username);
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
//            if (path.equals("/recruit/getAllStaff")) {
//                try {
//                    List<Recruit> allRecruits = recruitService.getAllRecruits();
//                    OutputStream os = exchange.getResponseBody();
//                    exchange.getResponseHeaders().set("Content-Type", "application/json");
//                    String jsonResponse = objectMapper.writeValueAsString(allRecruits);
//                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
//                    os.write(jsonResponse.getBytes());
//                    os.flush();
//                    os.close();
//                } catch (IOException | SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            } else if (path.equals("recruit/history")) {
//                try {
//                    List<Recruit> recruitList = recruitService.getRecruitHistory();
//                    OutputStream os = exchange.getResponseBody();
//                    exchange.getResponseHeaders().set("Content-Type", "application/json");
//                    String jsonResponse = objectMapper.writeValueAsString(recruitList);
//                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
//                    os.write(jsonResponse.getBytes());
//                    os.flush();
//                    os.close();
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//
//            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            if (path.startsWith("/management/update")) {
                int id = Integer.parseInt(path.split("/")[3]);
                InputStream inputStream = exchange.getRequestBody();
                ManagementStaff mngStaff = objectMapper.readValue(inputStream, ManagementStaff.class);
                Response response = mngService.update(mngStaff, id);
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
            }
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }
}