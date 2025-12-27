package com.kh.portfolio.controller;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final PortfolioService portfolioService;

    public ApiController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("activeProjects", portfolioService.getActiveFilmography());
        data.put("upcomingProjects", portfolioService.getReleasedFilmography());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/projects")
    public ResponseEntity<Map<String, Object>> projects() {
        Map<String, Object> data = new HashMap<>();
        data.put("projects", portfolioService.getAllFilmography());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Filmography> project(@PathVariable Long id) {
        Filmography film = portfolioService.getFilmography(id);
        if (film == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(film);
    }

    @GetMapping("/process")
    public ResponseEntity<Map<String, Object>> process() {
        portfolioService.initDefaultProcessSteps();
        Map<String, Object> data = new HashMap<>();
        data.put("steps", portfolioService.getAllProcessSteps());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/notes")
    public ResponseEntity<Map<String, Object>> notes() {
        Map<String, Object> data = new HashMap<>();
        data.put("notes", portfolioService.getAllNotes());
        data.put("projects", portfolioService.getAllFilmography());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/contact")
    public ResponseEntity<Map<String, Object>> contact() {
        Map<String, Object> data = new HashMap<>();
        data.put("profile", portfolioService.getProfile());
        return ResponseEntity.ok(data);
    }
}
