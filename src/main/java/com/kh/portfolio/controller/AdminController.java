package com.kh.portfolio.controller;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.service.PortfolioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PortfolioService portfolioService;

    public AdminController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    private void addProfileToModel(Model model) {
        model.addAttribute("profile", portfolioService.getProfile());
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping
    public String dashboard(Model model) {
        addProfileToModel(model);
        model.addAttribute("filmographyCount", portfolioService.getAllFilmography().size());
        model.addAttribute("noteCount", portfolioService.getAllNotes().size());
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String editProfile(Model model) {
        addProfileToModel(model);
        return "admin/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute Profile profile,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {
        try {
            portfolioService.updateProfile(profile, imageFile);
            redirectAttributes.addFlashAttribute("message", "프로필이 저장되었습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 실패");
        }
        return "redirect:/admin/profile";
    }

    @GetMapping("/filmography")
    public String filmographyList(Model model) {
        addProfileToModel(model);
        model.addAttribute("filmographies", portfolioService.getAllFilmography());
        return "admin/filmography-list";
    }

    @GetMapping("/filmography/new")
    public String newFilmography(Model model) {
        addProfileToModel(model);
        model.addAttribute("filmography", new Filmography());
        return "admin/filmography-form";
    }

    @GetMapping("/filmography/{id}")
    public String editFilmography(@PathVariable Long id, Model model) {
        addProfileToModel(model);
        Filmography filmography = portfolioService.getFilmography(id);
        if (filmography == null) {
            return "redirect:/admin/filmography";
        }
        model.addAttribute("filmography", filmography);
        return "admin/filmography-form";
    }

    @PostMapping("/filmography")
    public String saveFilmography(@ModelAttribute Filmography filmography,
                                  @RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
                                  RedirectAttributes redirectAttributes) {
        try {
            portfolioService.saveFilmography(filmography, posterFile);
            redirectAttributes.addFlashAttribute("message", "필모그래피가 저장되었습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 실패");
        }
        return "redirect:/admin/filmography";
    }

    @PostMapping("/filmography/{id}/delete")
    public String deleteFilmography(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteFilmography(id);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/filmography";
    }

    @GetMapping("/filmography/{id}/detail")
    public String filmographyDetail(@PathVariable Long id, Model model) {
        addProfileToModel(model);
        Filmography filmography = portfolioService.getFilmography(id);
        if (filmography == null) {
            return "redirect:/admin/filmography";
        }
        model.addAttribute("filmography", filmography);
        return "admin/filmography-detail";
    }

    @PostMapping("/filmography/{id}/support")
    public String addSupport(@PathVariable Long id,
                            @RequestParam String organizationName,
                            @RequestParam(required = false) String supportDetail,
                            @RequestParam(required = false) String documentLink,
                            @RequestParam(required = false) MultipartFile documentFile,
                            RedirectAttributes redirectAttributes) {
        try {
            Filmography filmography = portfolioService.getFilmography(id);
            Support support = new Support();
            support.setOrganizationName(organizationName);
            support.setSupportDetail(supportDetail);
            support.setDocumentLink(documentLink);
            support.setFilmography(filmography);

            if (documentFile != null && !documentFile.isEmpty()) {
                String url = portfolioService.uploadFile(documentFile);
                support.setDocumentUrl(url);
            }

            portfolioService.saveSupport(support);
            redirectAttributes.addFlashAttribute("message", "협조 기관이 추가되었습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "파일 업로드 실패");
        }
        return "redirect:/admin/filmography/" + id + "/detail";
    }

    @PostMapping("/filmography/{filmId}/support/{supportId}/delete")
    public String deleteSupport(@PathVariable Long filmId, @PathVariable Long supportId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteSupport(supportId);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId + "/detail";
    }

    @PostMapping("/filmography/{id}/gallery")
    public String addGallery(@PathVariable Long id,
                            @RequestParam(required = false) String caption,
                            @RequestParam MultipartFile imageFile,
                            RedirectAttributes redirectAttributes) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                Filmography filmography = portfolioService.getFilmography(id);
                FilmGallery gallery = new FilmGallery();
                gallery.setCaption(caption);
                gallery.setFilmography(filmography);
                gallery.setDisplayOrder(filmography.getGalleries().size() + 1);

                String url = portfolioService.uploadFile(imageFile);
                gallery.setImageUrl(url);

                portfolioService.saveGallery(gallery);
                redirectAttributes.addFlashAttribute("message", "이미지가 추가되었습니다.");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 실패");
        }
        return "redirect:/admin/filmography/" + id + "/detail";
    }

    @PostMapping("/filmography/{filmId}/gallery/{galleryId}/delete")
    public String deleteGallery(@PathVariable Long filmId, @PathVariable Long galleryId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteGallery(galleryId);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId + "/detail";
    }

    @PostMapping("/filmography/{id}/video")
    public String addVideo(@PathVariable Long id,
                          @RequestParam(required = false) String title,
                          @RequestParam String videoUrl,
                          @RequestParam(required = false) String videoType,
                          RedirectAttributes redirectAttributes) {
        Filmography filmography = portfolioService.getFilmography(id);
        FilmVideo video = new FilmVideo();
        video.setTitle(title);
        video.setVideoUrl(videoUrl);
        video.setVideoType(videoType != null ? videoType : "youtube");
        video.setFilmography(filmography);
        video.setDisplayOrder(filmography.getVideos().size() + 1);

        portfolioService.saveVideo(video);
        redirectAttributes.addFlashAttribute("message", "비디오가 추가되었습니다.");
        return "redirect:/admin/filmography/" + id + "/detail";
    }

    @PostMapping("/filmography/{filmId}/video/{videoId}/delete")
    public String deleteVideo(@PathVariable Long filmId, @PathVariable Long videoId, RedirectAttributes redirectAttributes) {
        portfolioService.deleteVideo(videoId);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/filmography/" + filmId + "/detail";
    }

    @GetMapping("/notes")
    public String noteList(Model model) {
        addProfileToModel(model);
        model.addAttribute("notes", portfolioService.getAllNotes());
        return "admin/note-list";
    }

    @GetMapping("/notes/new")
    public String newNote(Model model) {
        addProfileToModel(model);
        model.addAttribute("note", new Note());
        return "admin/note-form";
    }

    @GetMapping("/notes/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        addProfileToModel(model);
        Note note = portfolioService.getNote(id);
        if (note == null) {
            return "redirect:/admin/notes";
        }
        model.addAttribute("note", note);
        return "admin/note-form";
    }

    @PostMapping("/notes")
    public String saveNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        portfolioService.saveNote(note);
        redirectAttributes.addFlashAttribute("message", "노트가 저장되었습니다.");
        return "redirect:/admin/notes";
    }

    @PostMapping("/notes/{id}/delete")
    public String deleteNote(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteNote(id);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/notes";
    }

    @GetMapping("/process")
    public String processList(Model model) {
        addProfileToModel(model);
        model.addAttribute("steps", portfolioService.getAllProcessSteps());
        return "admin/process-list";
    }

    @GetMapping("/process/new")
    public String newProcess(Model model) {
        addProfileToModel(model);
        model.addAttribute("step", new ProcessStep());
        return "admin/process-form";
    }

    @GetMapping("/process/{id}")
    public String editProcess(@PathVariable Long id, Model model) {
        addProfileToModel(model);
        ProcessStep step = portfolioService.getProcessStep(id);
        if (step == null) {
            return "redirect:/admin/process";
        }
        model.addAttribute("step", step);
        return "admin/process-form";
    }

    @PostMapping("/process")
    public String saveProcess(@ModelAttribute ProcessStep step, RedirectAttributes redirectAttributes) {
        portfolioService.saveProcessStep(step);
        redirectAttributes.addFlashAttribute("message", "저장되었습니다.");
        return "redirect:/admin/process";
    }

    @PostMapping("/process/{id}/delete")
    public String deleteProcess(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteProcessStep(id);
        redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
        return "redirect:/admin/process";
    }
}
