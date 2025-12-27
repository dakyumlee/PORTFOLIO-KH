package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Filmography;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmographyRepository extends JpaRepository<Filmography, Long> {
    List<Filmography> findAllByOrderByYearDesc();
    List<Filmography> findByStatusOrderByYearDesc(String status);
    List<Filmography> findByStatusIn(List<String> statuses);
    List<Filmography> findByStatusInOrderByYearDesc(List<String> statuses);
}
