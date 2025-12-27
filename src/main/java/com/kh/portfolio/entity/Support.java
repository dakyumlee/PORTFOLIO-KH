package com.kh.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organizationName;

    @Column(length = 1000)
    private String supportDetail;

    private String documentUrl;
    private String documentLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmography_id")
    @JsonIgnore
    private Filmography filmography;
}
