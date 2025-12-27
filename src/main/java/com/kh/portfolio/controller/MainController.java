package com.kh.portfolio.controller;

import com.kh.portfolio.service.PortfolioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final PortfolioService portfolioService;

    public MainController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        portfolioService.initDefaultProcessSteps();
        
        model.addAttribute("profile", portfolioService.getProfile());
        model.addAttribute("activeProjects", portfolioService.getActiveFilmography());
        model.addAttribute("releasedProjects", portfolioService.getReleasedFilmography());
        model.addAttribute("notes", portfolioService.getAllNotes());
        model.addAttribute("processSteps", portfolioService.getAllProcessSteps());
        return "index";
    }
}
