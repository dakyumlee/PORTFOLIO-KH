package com.kh.portfolio.controller;

import com.kh.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PortfolioService portfolioService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("profile", portfolioService.getProfile());
        model.addAttribute("filmography", portfolioService.getAllFilmography());
        model.addAttribute("gallery", portfolioService.getAllGallery());
        model.addAttribute("videos", portfolioService.getAllVideos());
        return "index";
    }
}
