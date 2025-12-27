package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Filmography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String titleEn;
    private String year;
    private String role;
    private String runtime;
    private String genre;
    private String format;
    private String status;
    private String director;
    private String posterUrl;

    @Column(length = 3000)
    private String synopsis;

    @Column(length = 2000)
    private String highlights;

    private Integer displayOrder;

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Support> supports = new ArrayList<>();

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<FilmGallery> galleries = new ArrayList<>();

    @OneToMany(mappedBy = "filmography", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<FilmVideo> videos = new ArrayList<>();
}
