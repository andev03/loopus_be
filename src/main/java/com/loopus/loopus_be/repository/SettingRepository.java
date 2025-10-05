package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {
    List<Setting> findAllByUser_UserId(UUID userId);
}
