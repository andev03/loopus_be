package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.SettingDto;
import com.loopus.loopus_be.dto.request.UpdateSettingRequest;

import java.util.List;
import java.util.UUID;

public interface ISettingService {
    List<SettingDto> getSettings();

    List<SettingDto> getSettingsByUserId(UUID userId);

    List<SettingDto> updateSettingsByUserId(List<UpdateSettingRequest> request);

    void initializeSettingsForNewUser(UUID userId);
}
