package com.kh.portfolio.service;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PortfolioService {

    private final ProfileRepository profileRepository;
    private final FilmographyRepository filmographyRepository;
    private final NoteRepository noteRepository;
    private final ProcessStepRepository processStepRepository;
    private final CloudinaryService cloudinaryService;

    public PortfolioService(ProfileRepository profileRepository,
                           FilmographyRepository filmographyRepository,
                           NoteRepository noteRepository,
                           ProcessStepRepository processStepRepository,
                           CloudinaryService cloudinaryService) {
        this.profileRepository = profileRepository;
        this.filmographyRepository = filmographyRepository;
        this.noteRepository = noteRepository;
        this.processStepRepository = processStepRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Profile getProfile() {
        return profileRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Profile profile = new Profile();
                    profile.setName("이기현");
                    profile.setNameEn("LEE KI HYUN");
                    profile.setPositions("Producer");
                    profile.setTagline("프레임 안에 감정을 담다");
                    profile.setTheme("monochrome");
                    return profileRepository.save(profile);
                });
    }

    @Transactional
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile updateProfile(Profile profile, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(imageFile);
            profile.setProfileImage(imageUrl);
        }
        return profileRepository.save(profile);
    }

    public List<Filmography> getAllFilmography() {
        return filmographyRepository.findAllByOrderByYearDesc();
    }

    public List<Filmography> getActiveFilmography() {
        return filmographyRepository.findByStatusIn(List.of("PREP", "SHOOTING", "POST"));
    }

    public List<Filmography> getReleasedFilmography() {
        return filmographyRepository.findByStatusOrderByYearDesc("RELEASED");
    }

    public Filmography getFilmography(Long id) {
        return filmographyRepository.findById(id).orElse(null);
    }

    @Transactional
    public Filmography saveFilmography(Filmography filmography) {
        return filmographyRepository.save(filmography);
    }

    @Transactional
    public Filmography saveFilmography(Filmography filmography, MultipartFile posterFile) throws IOException {
        if (posterFile != null && !posterFile.isEmpty()) {
            String posterUrl = cloudinaryService.uploadImage(posterFile);
            filmography.setPosterUrl(posterUrl);
        }
        return filmographyRepository.save(filmography);
    }

    @Transactional
    public void deleteFilmography(Long id) {
        filmographyRepository.deleteById(id);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc();
    }

    public Note getNote(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public List<ProcessStep> getAllProcessSteps() {
        return processStepRepository.findAllByOrderByStepNumberAsc();
    }

    @Transactional
    public void initDefaultProcessSteps() {
        if (processStepRepository.count() == 0) {
            String[][] defaults = {
                {"기획", "아이디어 구체화 및 시나리오 개발"},
                {"프리프로덕션", "캐스팅, 로케이션, 스태프 구성"},
                {"촬영", "본 촬영 진행"},
                {"후반작업", "편집, 색보정, 사운드"},
                {"배급", "영화제 출품 및 배급"}
            };
            for (int i = 0; i < defaults.length; i++) {
                ProcessStep step = new ProcessStep();
                step.setStepNumber(i + 1);
                step.setTitle(defaults[i][0]);
                step.setDescription(defaults[i][1]);
                processStepRepository.save(step);
            }
        }
    }

    public ProcessStep getProcessStep(Long id) {
        return processStepRepository.findById(id).orElse(null);
    }

    @Transactional
    public ProcessStep saveProcessStep(ProcessStep step) {
        return processStepRepository.save(step);
    }

    @Transactional
    public void deleteProcessStep(Long id) {
        processStepRepository.deleteById(id);
    }
}
