package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByOrderByCreatedAtDesc();
}
