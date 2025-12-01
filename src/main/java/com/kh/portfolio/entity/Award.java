package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "awards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id", nullable = false)
    private Filmography filmography;

    private String awardName;

    private String festivalName;

    private String year;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    private Boolean isMainDisplayed = false;
}