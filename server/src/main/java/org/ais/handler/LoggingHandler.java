package org.ais.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.ais.model.Response;
import org.ais.service.LoggingService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * This class handles all the logging requests
 */
public class LoggingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoggingService loggingService = LoggingService.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (exchange.getRequestMethod().equals("POST")) {
            if (exchange.getRequestURI().getPath().equals("/logging/add")) {
                InputStream inputStream = exchange.getRequestBody();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                try {
                    Map<String, String> request = objectMapper.readValue(inputStream, Map.class);
                    Response loginResponse = loggingService.addLog(request);
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
            } else {
                exchange.sendResponseHeaders(404, 0);
            }
        }
    }
}