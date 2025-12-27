package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findByFilmographyId(Long filmographyId);
}
