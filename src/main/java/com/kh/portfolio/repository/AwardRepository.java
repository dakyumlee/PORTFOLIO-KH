package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findByFilmographyId(Long filmographyId);
}
