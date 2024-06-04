package org.ais;

import com.sun.net.httpserver.HttpServer;
import org.ais.handler.AdminHandler;
import org.ais.handler.LoginHandler;
import org.ais.handler.ManagementHandler;
import org.ais.handler.RecruitHandler;
import org.ais.util.DBUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws Exception {
        DBUtil.initializeDB();
        int port = 8000;
        int poolSize = 10;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler());
        server.createContext("/recruit", new RecruitHandler());
        server.createContext("/management", new ManagementHandler());
        server.createContext("/admin", new AdminHandler());
        server.setExecutor(Executors.newFixedThreadPool(poolSize));
        server.start();
        System.out.println("Server started. Listening on port " + port + " with thread pool size of " + poolSize);
    }
}
