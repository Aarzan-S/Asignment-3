package org.ais.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ais.model.Recruit;
import org.ais.model.Response;
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

/**
 * This class handles all the recruit requests
 * handles requests based on method type and path
 */
public class RecruitHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String path = exchange.getRequestURI().getPath();
        RecruitServiceImpl recruitService = RecruitServiceImpl.getInstance();
        if ("POST".equals(exchange.getRequestMethod()) && path.equals("/recruit/registration")) {
            try {
                InputStream inputStream = exchange.getRequestBody();
                Recruit recruit = objectMapper.readValue(inputStream, Recruit.class);
                Response response = recruitService.registerRecruit(recruit);
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
                System.out.println("Could not register recruit");
            }
        } else if ("GET".equals(exchange.getRequestMethod())) {
            if (path.equals("/recruit/getAll")) {
                try {
                    List<Recruit> allRecruits = recruitService.getAllRecruits();
                    OutputStream os = exchange.getResponseBody();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    String jsonResponse = objectMapper.writeValueAsString(allRecruits);
                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                    os.write(jsonResponse.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (path.startsWith("/recruit/details")) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = QueryParamUtil.queryToMap(query);
                String username = params.get("username");
                try {
                    Recruit response = recruitService.getDetails(username);
                    String jsonResponse = objectMapper.writeValueAsString(response);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(jsonResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("Could not fetch admin");
                }
            } else if (path.equals("recruit/history")) {
                try {
                    LinkedList<Recruit> recruitList = recruitService.getRecruitHistory();
                    OutputStream os = exchange.getResponseBody();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    String jsonResponse = objectMapper.writeValueAsString(recruitList);
                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                    os.write(jsonResponse.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            if (path.startsWith("/recruit/updateByStaff")) {
                int id = Integer.parseInt(path.split("/")[3]);
                try {
                    InputStream inputStream = exchange.getRequestBody();
                    Recruit recruit = objectMapper.readValue(inputStream, Recruit.class);
                    Response response = recruitService.updateRecruit(recruit, id, true);
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
                    System.out.println("Could not update Recruit data");
                }
            } else if (path.startsWith("/recruit/update")) {
                int id = Integer.parseInt(path.split("/")[3]);
                try {
                    InputStream inputStream = exchange.getRequestBody();
                    Recruit recruit = objectMapper.readValue(inputStream, Recruit.class);
                    Response response = recruitService.updateRecruit(recruit, id, false);
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
                    System.out.println("Could not update Recruit data");
                }
            }            
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }
}