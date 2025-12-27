package com.kh.portfolio.repository;

import com.kh.portfolio.entity.FilmVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmVideoRepository extends JpaRepository<FilmVideo, Long> {
    List<FilmVideo> findByFilmographyId(Long filmographyId);
}
