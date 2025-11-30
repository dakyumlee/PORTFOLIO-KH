package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String youtubeUrl;

    private String youtubeId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer displayOrder;

    @PrePersist
    @PreUpdate
    protected void extractYoutubeId() {
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            String url = youtubeUrl;
            if (url.contains("youtu.be/")) {
                youtubeId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
            } else if (url.contains("watch?v=")) {
                youtubeId = url.split("v=")[1].split("&")[0];
            } else if (url.contains("/embed/")) {
                youtubeId = url.substring(url.lastIndexOf("/embed/") + 7).split("\\?")[0];
            }
        }
    }
}
