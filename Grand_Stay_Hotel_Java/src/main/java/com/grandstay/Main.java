
package com.grandstay;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        int port = 8080;

        HttpServer server = HttpServer.create(
            new InetSocketAddress(port), 0
        );

        server.createContext("/", Main::handleRequest);

        server.start();

        System.out.println("================================");
        System.out.println("       GRAND STAY HOTEL");
        System.out.println("================================");
        System.out.println("Java Server Started Successfully!");
        System.out.println("Website: http://localhost:8080");
        System.out.println("================================");
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        String method = exchange.getRequestMethod();

        if (!method.equals("GET") && !method.equals("HEAD")) {
            sendError(exchange, 405, "Method Not Allowed");
            return;
        }

        String path = exchange.getRequestURI().getPath();

        if (path == null || path.equals("/")) {
            path = "/index.html";
        }

        if (path.contains("\\") || path.contains("..")) {
            sendError(exchange, 400, "Invalid Request");
            return;
        }

        String resource = "/public/" + path.substring(1);

        try (InputStream input =
                Main.class.getResourceAsStream(resource)) {

            if (input == null) {
                sendError(exchange, 404, "Page Not Found: " + path);
                return;
            }

            byte[] content = input.readAllBytes();

            exchange.getResponseHeaders().set(
                "Content-Type", getContentType(path)
            );

            exchange.getResponseHeaders().set(
                "Cache-Control", "no-cache"
            );

            if (method.equals("HEAD")) {
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
                return;
            }

            exchange.sendResponseHeaders(200, content.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(content);
            }
        }
    }

    private static String getContentType(String path) {

        String file = path.toLowerCase();

        if (file.endsWith(".html"))
            return "text/html; charset=UTF-8";

        if (file.endsWith(".css"))
            return "text/css; charset=UTF-8";

        if (file.endsWith(".js"))
            return "application/javascript; charset=UTF-8";

        if (file.endsWith(".json"))
            return "application/json; charset=UTF-8";

        if (file.endsWith(".png"))
            return "image/png";

        if (file.endsWith(".jpg") || file.endsWith(".jpeg"))
            return "image/jpeg";

        if (file.endsWith(".svg"))
            return "image/svg+xml";

        if (file.endsWith(".webp"))
            return "image/webp";

        if (file.endsWith(".ico"))
            return "image/x-icon";

        return "application/octet-stream";
    }

    private static void sendError(
            HttpExchange exchange,
            int status,
            String message) throws IOException {

        byte[] content = message.getBytes("UTF-8");

        exchange.getResponseHeaders().set(
            "Content-Type", "text/plain; charset=UTF-8"
        );

        exchange.sendResponseHeaders(status, content.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(content);
        }
    }
}
