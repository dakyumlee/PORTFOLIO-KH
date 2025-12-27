package com.kh.portfolio.config;

import com.kh.portfolio.entity.Profile;
import com.kh.portfolio.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProfileRepository profileRepository;

    public DataInitializer(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void run(String... args) {
        if (profileRepository.count() == 0) {
            Profile profile = new Profile();
            profile.setName("이기현");
            profile.setNameEn("LEE KI HYUN");
            profile.setPositions("Producer, Line Producer");
            profile.setTagline("프레임 안에 감정을 담다");
            profile.setBio("영화 제작자입니다.");
            profile.setEmail("contact@example.com");
            profile.setTheme("monochrome");
            profileRepository.save(profile);
        }
    }
}
