package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Filmography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FilmographyRepository extends JpaRepository<Filmography, Long> {
    List<Filmography> findAllByOrderByYearDesc();
    List<Filmography> findByStatusOrderByYearDesc(String status);
    List<Filmography> findByStatusIn(List<String> statuses);
    List<Filmography> findByStatusInOrderByYearDesc(List<String> statuses);

    @Query("SELECT DISTINCT f FROM Filmography f LEFT JOIN FETCH f.supports WHERE f.id = :id")
    Optional<Filmography> findByIdWithSupports(@Param("id") Long id);

    @Query("SELECT DISTINCT f FROM Filmography f LEFT JOIN FETCH f.galleries WHERE f.id = :id")
    Optional<Filmography> findByIdWithGalleries(@Param("id") Long id);

    @Query("SELECT DISTINCT f FROM Filmography f LEFT JOIN FETCH f.videos WHERE f.id = :id")
    Optional<Filmography> findByIdWithVideos(@Param("id") Long id);
}
