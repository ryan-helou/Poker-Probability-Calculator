
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // GET /api/odds?value1=12&suit1=Spades&value2=14&suit2=Clubs&players=4&iterations=50000
        server.createContext("/api/odds", new OddsHandler());

        // Basic health check
        server.createContext("/health", exchange -> {
            String resp = "OK";
            exchange.sendResponseHeaders(200, resp.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp.getBytes());
            }
        });

        server.setExecutor(null);
        System.out.println("API listening on http://localhost:" + port);
        server.start();
    }

    static class OddsHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    sendJson(exchange, 405, "{\"error\":\"Use GET\"}");
                    return;
                }
                Map<String, String> q = queryParams(exchange.getRequestURI());

                int value1 = parseInt(q.get("value1"), 12);
                String suit1 = q.getOrDefault("suit1", "Spades");
                int value2 = parseInt(q.get("value2"), 14);
                String suit2 = q.getOrDefault("suit2", "Clubs");
                int players = parseInt(q.get("players"), 4);
                int iterations = parseInt(q.get("iterations"), 50000);

                // Call your existing method (we added earlier)
                OddsResult r = Round.calculateOdds(value1, suit1, value2, suit2, players, iterations);

                String json = String.format("{\"win\":%.4f,\"tie\":%.4f,\"lose\":%.4f}", r.win, r.tie, r.lose);
                sendJson(exchange, 200, json);
            } catch (Exception e) {
                String json = "{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}";
                sendJson(exchange, 500, json);
            }
        }

        private static int parseInt(String s, int def) {
            try {
                return s == null ? def : Integer.parseInt(s);
            } catch (Exception e) {
                return def;
            }
        }

        private static Map<String, String> queryParams(URI uri) {
            Map<String, String> map = new HashMap<>();
            String q = uri.getRawQuery();
            if (q == null || q.isEmpty()) {
                return map;
            }
            for (String pair : q.split("&")) {
                int idx = pair.indexOf('=');
                if (idx > 0) {
                    String k = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                    String v = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                    map.put(k, v);
                } else {
                    map.put(URLDecoder.decode(pair, StandardCharsets.UTF_8), "");
                }
            }
            return map;
        }

        private static void sendJson(HttpExchange exchange, int status, String body) throws IOException {
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173"); // allow Vite in dev
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}
