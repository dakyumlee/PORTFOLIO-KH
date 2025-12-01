package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film_videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id", nullable = false)
    private Filmography filmography;

    private String title;

    private String youtubeUrl;

    private String youtubeId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer displayOrder;

    @PrePersist
    @PreUpdate
    public void extractYoutubeId() {
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            if (youtubeUrl.contains("v=")) {
                this.youtubeId = youtubeUrl.split("v=")[1].split("&")[0];
            } else if (youtubeUrl.contains("youtu.be/")) {
                this.youtubeId = youtubeUrl.split("youtu.be/")[1].split("\\?")[0];
            }
        }
    }
}
