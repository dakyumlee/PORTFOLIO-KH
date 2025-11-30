package com.kh.portfolio.config;

import com.kh.portfolio.entity.*;
import com.kh.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProfileRepository profileRepository;

    @Override
    public void run(String... args) {
        if (profileRepository.count() == 0) {
            Profile profile = Profile.builder()
                .name("이기현")
                .tagline("프레임 안에 감정을 담다")
                .bio("영화를 통해 이야기를 전하는 사람입니다.")
                .email("contact@example.com")
                .instagram("instagram_id")
                .build();
            profileRepository.save(profile);
        }
    }
}
