package com.kh.portfolio.service;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PortfolioService {

    private final ProfileRepository profileRepository;
    private final FilmographyRepository filmographyRepository;
    private final NoteRepository noteRepository;
    private final ProcessStepRepository processStepRepository;
    private final SupportRepository supportRepository;
    private final FilmGalleryRepository filmGalleryRepository;
    private final FilmVideoRepository filmVideoRepository;
    private final CloudinaryService cloudinaryService;

    public PortfolioService(ProfileRepository profileRepository,
                           FilmographyRepository filmographyRepository,
                           NoteRepository noteRepository,
                           ProcessStepRepository processStepRepository,
                           SupportRepository supportRepository,
                           FilmGalleryRepository filmGalleryRepository,
                           FilmVideoRepository filmVideoRepository,
                           CloudinaryService cloudinaryService) {
        this.profileRepository = profileRepository;
        this.filmographyRepository = filmographyRepository;
        this.noteRepository = noteRepository;
        this.processStepRepository = processStepRepository;
        this.supportRepository = supportRepository;
        this.filmGalleryRepository = filmGalleryRepository;
        this.filmVideoRepository = filmVideoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Profile getProfile() {
        return profileRepository.findAll().stream().findFirst().orElse(new Profile());
    }

    public void updateProfile(Profile profile, MultipartFile imageFile) throws IOException {
        Profile existing = getProfile();
        existing.setName(profile.getName());
        existing.setNameEn(profile.getNameEn());
        existing.setPositions(profile.getPositions());
        existing.setTagline(profile.getTagline());
        existing.setBio(profile.getBio());
        existing.setEmail(profile.getEmail());
        existing.setInstagram(profile.getInstagram());
        existing.setTheme(profile.getTheme());

        if (imageFile != null && !imageFile.isEmpty()) {
            String url = cloudinaryService.uploadImage(imageFile);
            existing.setProfileImage(url);
        }

        profileRepository.save(existing);
    }

    public List<Filmography> getAllFilmography() {
        return filmographyRepository.findAllByOrderByYearDesc();
    }

    public List<Filmography> getActiveFilmography() {
        return filmographyRepository.findByStatusInOrderByYearDesc(Arrays.asList("PREP", "SHOOTING", "POST"));
    }

    public List<Filmography> getReleasedFilmography() {
        return filmographyRepository.findByStatusOrderByYearDesc("RELEASED");
    }

    public Filmography getFilmography(Long id) {
        return filmographyRepository.findById(id).orElse(null);
    }

    public Filmography getFilmographyWithDetails(Long id) {
        return filmographyRepository.findByIdWithDetails(id).orElse(null);
    }

    public void saveFilmography(Filmography filmography, MultipartFile posterFile) throws IOException {
        if (posterFile != null && !posterFile.isEmpty()) {
            String url = cloudinaryService.uploadImage(posterFile);
            filmography.setPosterUrl(url);
        } else if (filmography.getId() != null) {
            Filmography existing = getFilmography(filmography.getId());
            if (existing != null) {
                filmography.setPosterUrl(existing.getPosterUrl());
            }
        }
        filmographyRepository.save(filmography);
    }

    public void deleteFilmography(Long id) {
        filmographyRepository.deleteById(id);
    }

    public void saveSupport(Support support) {
        supportRepository.save(support);
    }

    public void deleteSupport(Long id) {
        supportRepository.deleteById(id);
    }

    public void saveGallery(FilmGallery gallery) {
        filmGalleryRepository.save(gallery);
    }

    public void deleteGallery(Long id) {
        filmGalleryRepository.deleteById(id);
    }

    public void saveVideo(FilmVideo video) {
        filmVideoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        filmVideoRepository.deleteById(id);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNote(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public List<ProcessStep> getAllProcessSteps() {
        return processStepRepository.findAllByOrderByStepNumberAsc();
    }

    public ProcessStep getProcessStep(Long id) {
        return processStepRepository.findById(id).orElse(null);
    }

    public void saveProcessStep(ProcessStep step) {
        processStepRepository.save(step);
    }

    public void deleteProcessStep(Long id) {
        processStepRepository.deleteById(id);
    }

    public void initDefaultProcessSteps() {
        if (processStepRepository.count() == 0) {
            String[][] defaults = {
                {"1", "기획", "아이디어 구체화, 시나리오 개발, 예산 및 일정 수립"},
                {"2", "프리프로덕션", "캐스팅, 로케이션 헌팅, 스태프 구성, 촬영 준비"},
                {"3", "프로덕션", "본 촬영 진행, 현장 관리, 일일 리뷰"},
                {"4", "포스트프로덕션", "편집, 색보정, 사운드 믹싱, VFX"},
                {"5", "배급", "마케팅, 영화제 출품, 극장 개봉, 스트리밍"}
            };
            for (String[] d : defaults) {
                ProcessStep step = new ProcessStep();
                step.setStepNumber(Integer.parseInt(d[0]));
                step.setTitle(d[1]);
                step.setDescription(d[2]);
                processStepRepository.save(step);
            }
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return cloudinaryService.uploadImage(file);
    }
}
