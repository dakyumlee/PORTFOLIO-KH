package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "filmography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filmography {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String year;

    private String role;

    private String director;

    private String runtime;

    private String genre;

    private String productionType;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(columnDefinition = "TEXT")
    private String roleDescription;

    private String posterUrl;

    private Integer displayOrder;

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("year DESC")
    @Builder.Default
    private List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Support> supports = new ArrayList<>();

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<FilmGallery> galleries = new ArrayList<>();

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<FilmVideo> videos = new ArrayList<>();
}
