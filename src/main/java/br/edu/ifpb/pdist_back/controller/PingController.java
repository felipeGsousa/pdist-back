package br.edu.ifpb.pdist_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/api/posts/ping")
    public String ping() {
        return "Keep alive";
    }
}
