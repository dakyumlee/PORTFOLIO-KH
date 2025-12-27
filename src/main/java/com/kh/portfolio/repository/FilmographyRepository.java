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

    @Query("SELECT f FROM Filmography f LEFT JOIN FETCH f.supports LEFT JOIN FETCH f.galleries LEFT JOIN FETCH f.videos WHERE f.id = :id")
    Optional<Filmography> findByIdWithDetails(@Param("id") Long id);
}
