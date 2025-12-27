package com.kh.portfolio.repository;

import com.kh.portfolio.entity.ProcessStep;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProcessStepRepository extends JpaRepository<ProcessStep, Long> {
    List<ProcessStep> findAllByOrderByStepNumberAsc();
}
