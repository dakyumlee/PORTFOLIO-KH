package com.kh.portfolio.service;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final ProfileRepository profileRepository;
    private final FilmographyRepository filmographyRepository;
    private final AwardRepository awardRepository;
    private final SupportRepository supportRepository;
    private final FilmGalleryRepository filmGalleryRepository;
    private final FilmVideoRepository filmVideoRepository;
    private final CloudinaryService cloudinaryService;

    public Profile getProfile() {
        return profileRepository.findFirstByOrderByIdAsc()
            .orElseGet(() -> {
                Profile profile = Profile.builder()
                    .name("이기현")
                    .tagline("프레임 안에 감정을 담다")
                    .bio("영화를 만드는 사람입니다.")
                    .build();
                return profileRepository.save(profile);
            });
    }

    public Profile updateProfile(Profile profile, MultipartFile image) throws IOException {
        Profile existing = getProfile();
        existing.setName(profile.getName());
        existing.setTagline(profile.getTagline());
        existing.setBio(profile.getBio());
        existing.setEmail(profile.getEmail());
        existing.setInstagram(profile.getInstagram());

        if (image != null && !image.isEmpty()) {
            Map<String, Object> result = cloudinaryService.upload(image, "profile");
            existing.setProfileImageUrl((String) result.get("secure_url"));
        }

        return profileRepository.save(existing);
    }

    public List<Filmography> getAllFilmography() {
        return filmographyRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Filmography getFilmography(Long id) {
        return filmographyRepository.findById(id).orElse(null);
    }

    public Filmography saveFilmography(Filmography filmography, MultipartFile poster) throws IOException {
        if (poster != null && !poster.isEmpty()) {
            Map<String, Object> result = cloudinaryService.upload(poster, "filmography");
            filmography.setPosterUrl((String) result.get("secure_url"));
        }
        if (filmography.getDisplayOrder() == null) {
            filmography.setDisplayOrder((int) filmographyRepository.count());
        }
        return filmographyRepository.save(filmography);
    }

    public void deleteFilmography(Long id) {
        filmographyRepository.deleteById(id);
    }

    public List<Award> getAwardsByFilmography(Long filmographyId) {
        return awardRepository.findByFilmographyIdOrderByYearDesc(filmographyId);
    }

    public List<Award> getMainAwards() {
        return awardRepository.findByIsMainDisplayedTrueOrderByYearDesc();
    }

    public Award saveAward(Award award) {
        if (award.getIsMainDisplayed() == null) {
            award.setIsMainDisplayed(false);
        }
        return awardRepository.save(award);
    }

    public void deleteAward(Long id) {
        awardRepository.deleteById(id);
    }

    public List<Support> getSupportsByFilmography(Long filmographyId) {
        return supportRepository.findByFilmographyId(filmographyId);
    }

    public Support saveSupport(Support support, MultipartFile document) throws IOException {
        if (document != null && !document.isEmpty()) {
            Map<String, Object> result = cloudinaryService.upload(document, "documents");
            support.setDocumentUrl((String) result.get("secure_url"));
            support.setHasDocument(true);
        }
        return supportRepository.save(support);
    }

    public void deleteSupport(Long id) {
        supportRepository.deleteById(id);
    }

    public List<FilmGallery> getAllGalleries() {
        return filmGalleryRepository.findAll();
    }

    public List<FilmVideo> getAllVideos() {
        return filmVideoRepository.findAll();
    }

    public List<FilmGallery> getGalleriesByFilmography(Long filmographyId) {
        return filmGalleryRepository.findByFilmographyIdOrderByDisplayOrderAsc(filmographyId);
    }

    public FilmGallery saveFilmGallery(FilmGallery gallery, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            Map<String, Object> result = cloudinaryService.upload(image, "gallery");
            String imageUrl = (String) result.get("secure_url");
            gallery.setImageUrl(imageUrl);
            gallery.setThumbnailUrl(cloudinaryService.getThumbnailUrl(imageUrl, 400, 400));
        }
        if (gallery.getDisplayOrder() == null) {
            gallery.setDisplayOrder((int) filmGalleryRepository.countByFilmographyId(gallery.getFilmography().getId()));
        }
        return filmGalleryRepository.save(gallery);
    }

    public void deleteFilmGallery(Long id) {
        filmGalleryRepository.deleteById(id);
    }

    public List<FilmVideo> getVideosByFilmography(Long filmographyId) {
        return filmVideoRepository.findByFilmographyIdOrderByDisplayOrderAsc(filmographyId);
    }

    public FilmVideo saveFilmVideo(FilmVideo video) {
        if (video.getDisplayOrder() == null) {
            video.setDisplayOrder((int) filmVideoRepository.countByFilmographyId(video.getFilmography().getId()));
        }
        return filmVideoRepository.save(video);
    }

    public void deleteFilmVideo(Long id) {
        filmVideoRepository.deleteById(id);
    }
}