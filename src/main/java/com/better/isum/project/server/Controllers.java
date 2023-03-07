package com.better.isum.project.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class Controllers {
    private final Gson gson = new GsonBuilder().create();

    @GetMapping(value = "/")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping(value = "/login")
    public String handleLogin(@RequestBody String body) {

        Student user = gson.fromJson(body, Student.class);
        if ("test".equals(user.getUsername()) && "test".equals(user.getPassword())) {
            return gson.toJson(new LoginResponse(true));
        } else {
            return gson.toJson(new LoginResponse(false));
        }
    }

    @RestController
    @RequestMapping(value = "/auth0")
    public class Auth0 {
        @GetMapping(value = "/print")
        public String printt() {
            return "something to print";
        }

        @GetMapping(value = "/validate/{id}")
        public String validateUser(@PathVariable String id) {
            return "something to print " + id;
        }
    }

    private class LoginResponse {
        private final boolean success;

        public LoginResponse(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
