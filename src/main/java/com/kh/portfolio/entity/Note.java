package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private String tags;

    private String noteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id")
    private Filmography filmography;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
