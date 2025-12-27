package com.kh.portfolio.repository;

import com.kh.portfolio.entity.FilmGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmGalleryRepository extends JpaRepository<FilmGallery, Long> {
    List<FilmGallery> findByFilmographyId(Long filmographyId);
}
