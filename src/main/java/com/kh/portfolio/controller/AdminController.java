package com.kh.portfolio.controller;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PortfolioService portfolioService;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("profile", portfolioService.getProfile());
        model.addAttribute("filmography", portfolioService.getAllFilmography());
        model.addAttribute("gallery", portfolioService.getAllGallery());
        model.addAttribute("videos", portfolioService.getAllVideos());
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String editProfile(Model model) {
        model.addAttribute("profile", portfolioService.getProfile());
        return "admin/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Profile profile,
                               @RequestParam(required = false) MultipartFile profileImage,
                               RedirectAttributes redirectAttributes) throws IOException {
        portfolioService.updateProfile(profile, profileImage);
        redirectAttributes.addFlashAttribute("message", "프로필이 업데이트되었습니다.");
        return "redirect:/admin/profile";
    }

    @GetMapping("/filmography")
    public String filmographyList(Model model) {
        model.addAttribute("filmography", portfolioService.getAllFilmography());
        return "admin/filmography-list";
    }

    @GetMapping("/filmography/new")
    public String newFilmography(Model model) {
        model.addAttribute("filmography", new Filmography());
        return "admin/filmography-form";
    }

    @GetMapping("/filmography/{id}")
    public String editFilmography(@PathVariable Long id, Model model) {
        model.addAttribute("filmography", portfolioService.getFilmography(id));
        return "admin/filmography-form";
    }

    @PostMapping("/filmography")
    public String saveFilmography(@ModelAttribute Filmography filmography,
                                 @RequestParam(required = false) MultipartFile posterImage,
                                 RedirectAttributes redirectAttributes) throws IOException {
        portfolioService.saveFilmography(filmography, posterImage);
        redirectAttributes.addFlashAttribute("message", "필모그래피가 저장되었습니다.");
        return "redirect:/admin/filmography";
    }

    @PostMapping("/filmography/{id}/delete")
    public String deleteFilmography(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteFilmography(id);
        redirectAttributes.addFlashAttribute("message", "필모그래피가 삭제되었습니다.");
        return "redirect:/admin/filmography";
    }

    @GetMapping("/gallery")
    public String galleryList(Model model) {
        model.addAttribute("gallery", portfolioService.getAllGallery());
        return "admin/gallery-list";
    }

    @GetMapping("/gallery/new")
    public String newGallery(Model model) {
        model.addAttribute("gallery", new Gallery());
        return "admin/gallery-form";
    }

    @GetMapping("/gallery/{id}")
    public String editGallery(@PathVariable Long id, Model model) {
        model.addAttribute("gallery", portfolioService.getGalleryItem(id));
        return "admin/gallery-form";
    }

    @PostMapping("/gallery")
    public String saveGallery(@ModelAttribute Gallery gallery,
                             @RequestParam(required = false) MultipartFile galleryImage,
                             RedirectAttributes redirectAttributes) throws IOException {
        portfolioService.saveGallery(gallery, galleryImage);
        redirectAttributes.addFlashAttribute("message", "갤러리 항목이 저장되었습니다.");
        return "redirect:/admin/gallery";
    }

    @PostMapping("/gallery/{id}/delete")
    public String deleteGallery(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteGallery(id);
        redirectAttributes.addFlashAttribute("message", "갤러리 항목이 삭제되었습니다.");
        return "redirect:/admin/gallery";
    }

    @GetMapping("/videos")
    public String videoList(Model model) {
        model.addAttribute("videos", portfolioService.getAllVideos());
        return "admin/video-list";
    }

    @GetMapping("/videos/new")
    public String newVideo(Model model) {
        model.addAttribute("video", new Video());
        return "admin/video-form";
    }

    @GetMapping("/videos/{id}")
    public String editVideo(@PathVariable Long id, Model model) {
        model.addAttribute("video", portfolioService.getVideo(id));
        return "admin/video-form";
    }

    @PostMapping("/videos")
    public String saveVideo(@ModelAttribute Video video, RedirectAttributes redirectAttributes) {
        portfolioService.saveVideo(video);
        redirectAttributes.addFlashAttribute("message", "영상이 저장되었습니다.");
        return "redirect:/admin/videos";
    }

    @PostMapping("/videos/{id}/delete")
    public String deleteVideo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteVideo(id);
        redirectAttributes.addFlashAttribute("message", "영상이 삭제되었습니다.");
        return "redirect:/admin/videos";
    }
}
