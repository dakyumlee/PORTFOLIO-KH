package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String status;
    private String posterUrl;

    @Column(length = 2000)
    private String synopsis;

    private String director;
    private String genre;
}
