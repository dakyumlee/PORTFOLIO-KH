package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(columnDefinition = "TEXT")
    private String description;

    private String posterUrl;

    private Integer displayOrder;
}
