package org.ais.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ais.model.Recruit;
import org.ais.model.Response;
import org.ais.model.Staff;
import org.ais.service.LoginService;
import org.ais.util.QueryParamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginService loginService = LoginService.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (exchange.getRequestMethod().equals("POST")) {
            if (exchange.getRequestURI().getPath().equals("/login/staff")) {
                InputStream inputStream = exchange.getRequestBody();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                try {
                    Staff staff = objectMapper.readValue(inputStream, Staff.class);
                    Response loginResponse = loginService.login(staff);
                    String response = objectMapper.writeValueAsString(loginResponse);
                    if (loginResponse.getStatus().equals("SUCCESS")) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, response.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (exchange.getRequestURI().getPath().equals("/login/recruit")) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = QueryParamUtil.queryToMap(query);
                String otp = params.get("otp");
                InputStream inputStream = exchange.getRequestBody();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                try {
                    Recruit recruit = objectMapper.readValue(inputStream, Recruit.class);
                    Response loginResponse = loginService.loginRecruit(recruit, otp);
                    String response = objectMapper.writeValueAsString(loginResponse);
                    if (loginResponse.getStatus().equals("SUCCESS")) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, response.getBytes().length);
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else {
            exchange.sendResponseHeaders(404, 0);
        }
    }
}
