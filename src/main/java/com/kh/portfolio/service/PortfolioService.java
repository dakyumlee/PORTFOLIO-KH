package com.kh.portfolio.service;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            String url = cloudinaryService.upload(imageFile);
            existing.setProfileImage(url);
        }

        profileRepository.save(existing);
    }

    public List<Filmography> getAllFilmography() {
        return filmographyRepository.findAllByOrderByDisplayOrderAscYearDesc();
    }

    public Filmography getFilmography(Long id) {
        return filmographyRepository.findById(id).orElse(null);
    }

    public void saveFilmography(Filmography filmography, MultipartFile posterFile) throws IOException {
        if (posterFile != null && !posterFile.isEmpty()) {
            String url = cloudinaryService.upload(posterFile);
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
}
