package com.kh.portfolio.repository;

import com.kh.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findFirstByOrderByIdAsc();
}
