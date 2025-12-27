package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nameEn;
    private String positions;
    private String tagline;

    @Column(length = 2000)
    private String bio;

    private String profileImage;
    private String email;
    private String instagram;
    private String theme = "monochrome";
}
