package com.kh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id", nullable = false)
    private Filmography filmography;

    private String organizationName;

    private String supportType;

    private String documentUrl;

    @Builder.Default
    private Boolean hasDocument = false;
}
