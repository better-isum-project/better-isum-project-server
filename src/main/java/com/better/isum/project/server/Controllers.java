package com.better.isum.project.server;

import java.util.Map;
import java.util.logging.Level;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RestController;

// import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class Controllers {
    private final Gson gson = new GsonBuilder().create();
    private final MetLogger LOGGER = MetLogger.getInstance();

    @GetMapping(value = "/")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleLogin(@RequestBody String body) {

        User user = gson.fromJson(body, User.class);
        if ("test".equals(user.getUsername()) && "test".equals(user.getPassword())) {
            return gson.toJson(Map.of("success", "true")).toString();
        } else {
            return gson.toJson(Map.of("success", "false")).toString();
        }
    }

    @RestController
    @RequestMapping(value = "/auth")
    public class Auth0 {
        @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public String validateToken(@RequestHeader Map<String, String> headers) {
            JWTUtils jwtUtils = JWTUtils.getInstance();

            LOGGER.LogMessage(headers.toString(), Level.CONFIG);

            String authHeader = headers.get("authorization");
            return gson.toJson(jwtUtils.validateToken(authHeader)).toString();
        }
    }
}
