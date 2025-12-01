package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film_galleries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id", nullable = false)
    private Filmography filmography;

    private String imageUrl;

    private String thumbnailUrl;

    private String caption;

    private Integer displayOrder;
}
