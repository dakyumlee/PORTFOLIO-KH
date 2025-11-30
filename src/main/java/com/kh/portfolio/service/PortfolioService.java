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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final ProfileRepository profileRepository;
    private final FilmographyRepository filmographyRepository;
    private final GalleryRepository galleryRepository;
    private final VideoRepository videoRepository;
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

    public List<Gallery> getAllGallery() {
        return galleryRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Gallery getGalleryItem(Long id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public Gallery saveGallery(Gallery gallery, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            Map<String, Object> result = cloudinaryService.upload(image, "gallery");
            String imageUrl = (String) result.get("secure_url");
            gallery.setImageUrl(imageUrl);
            gallery.setThumbnailUrl(cloudinaryService.getThumbnailUrl(imageUrl, 400, 400));
        }
        if (gallery.getDisplayOrder() == null) {
            gallery.setDisplayOrder((int) galleryRepository.count());
        }
        return galleryRepository.save(gallery);
    }

    public void deleteGallery(Long id) {
        galleryRepository.deleteById(id);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Video getVideo(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    public Video saveVideo(Video video) {
        if (video.getDisplayOrder() == null) {
            video.setDisplayOrder((int) videoRepository.count());
        }
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
