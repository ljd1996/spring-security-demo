package com.example.springsecuritydemo2.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class RequestController {

    @GetMapping("/all")
    public String all(Map<String, String> map) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        map.put("name", SecurityContextHolder.getContext().getAuthentication().getName());
        return "all";
    }

    @GetMapping(value = "/home")
    public String home(@RequestParam(value = "test", required = false) String test, Map<String, String> map) {
        map.put("test", test);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "loginError";
    }

    @GetMapping("/Login401")
    public String Login401() {
        return "401";
    }

    @GetMapping("/Login403")
    public String Login403() {
        return "403";
    }

    @GetMapping("/Login404")
    public String Login404() {
        return "404";
    }

    @GetMapping("/Login500")
    public String Login500() {
        return "500";
    }
}
