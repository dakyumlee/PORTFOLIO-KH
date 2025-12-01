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
        Filmography film = portfolioService.getFilmography(id);
        model.addAttribute("filmography", film);
        model.addAttribute("awards", portfolioService.getAwardsByFilmography(id));
        model.addAttribute("supports", portfolioService.getSupportsByFilmography(id));
        model.addAttribute("galleries", portfolioService.getGalleriesByFilmography(id));
        model.addAttribute("videos", portfolioService.getVideosByFilmography(id));
        return "admin/filmography-form";
    }

    @PostMapping("/filmography")
    public String saveFilmography(@ModelAttribute Filmography filmography,
                                 @RequestParam(required = false) MultipartFile posterImage,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Filmography saved = portfolioService.saveFilmography(filmography, posterImage);
        redirectAttributes.addFlashAttribute("message", "필모그래피가 저장되었습니다.");
        return "redirect:/admin/filmography/" + saved.getId();
    }

    @PostMapping("/filmography/{id}/delete")
    public String deleteFilmography(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteFilmography(id);
        redirectAttributes.addFlashAttribute("message", "필모그래피가 삭제되었습니다.");
        return "redirect:/admin/filmography";
    }

    @PostMapping("/filmography/{filmId}/awards")
    public String saveAward(@PathVariable Long filmId, @ModelAttribute Award award, RedirectAttributes redirectAttributes) {
        Filmography film = portfolioService.getFilmography(filmId);
        award.setFilmography(film);
        portfolioService.saveAward(award);
        redirectAttributes.addFlashAttribute("message", "수상내역이 추가되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/awards/{awardId}/delete")
    public String deleteAward(@PathVariable Long filmId, @PathVariable Long awardId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteAward(awardId);
        redirectAttributes.addFlashAttribute("message", "수상내역이 삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/supports")
    public String saveSupport(@PathVariable Long filmId, @ModelAttribute Support support,
                             @RequestParam(required = false) MultipartFile documentFile,
                             RedirectAttributes redirectAttributes) throws IOException {
        Filmography film = portfolioService.getFilmography(filmId);
        support.setFilmography(film);
        portfolioService.saveSupport(support, documentFile);
        redirectAttributes.addFlashAttribute("message", "협조기관이 추가되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/supports/{supportId}/delete")
    public String deleteSupport(@PathVariable Long filmId, @PathVariable Long supportId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteSupport(supportId);
        redirectAttributes.addFlashAttribute("message", "협조기관이 삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/galleries")
    public String saveFilmGallery(@PathVariable Long filmId,
                                  @RequestParam(required = false) String caption,
                                  @RequestParam MultipartFile galleryImage,
                                  RedirectAttributes redirectAttributes) throws IOException {
        Filmography film = portfolioService.getFilmography(filmId);
        FilmGallery gallery = FilmGallery.builder().filmography(film).caption(caption).build();
        portfolioService.saveFilmGallery(gallery, galleryImage);
        redirectAttributes.addFlashAttribute("message", "스틸컷이 추가되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/galleries/{galleryId}/delete")
    public String deleteFilmGallery(@PathVariable Long filmId, @PathVariable Long galleryId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteFilmGallery(galleryId);
        redirectAttributes.addFlashAttribute("message", "스틸컷이 삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/videos")
    public String saveFilmVideo(@PathVariable Long filmId, @ModelAttribute FilmVideo video, RedirectAttributes redirectAttributes) {
        Filmography film = portfolioService.getFilmography(filmId);
        video.setFilmography(film);
        portfolioService.saveFilmVideo(video);
        redirectAttributes.addFlashAttribute("message", "비디오가 추가되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }

    @PostMapping("/filmography/{filmId}/videos/{videoId}/delete")
    public String deleteFilmVideo(@PathVariable Long filmId, @PathVariable Long videoId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteFilmVideo(videoId);
        redirectAttributes.addFlashAttribute("message", "비디오가 삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId;
    }
}
