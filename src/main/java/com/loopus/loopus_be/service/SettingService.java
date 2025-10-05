package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.SettingDto;
import com.loopus.loopus_be.dto.request.UpdateSettingRequest;
import com.loopus.loopus_be.enums.SettingTypeEnum;
import com.loopus.loopus_be.model.Setting;
import com.loopus.loopus_be.repository.SettingRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.ISettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettingService implements ISettingService {

    private final SettingRepository settingRepository;
    private final UserRepository userRepository;

    @Override
    public List<SettingDto> getSettings() {

        List<SettingDto> result = new ArrayList<>();

        for (SettingTypeEnum type : SettingTypeEnum.values()) {
            result.add(SettingDto.builder()
                    .type(type.name())
                    .description(type.getDescription())
                    .build());
        }

        return result;
    }

    @Override
    public List<SettingDto> getSettingsByUserId(UUID userId) {

        List<SettingDto> result = new ArrayList<>();

        List<Setting> settings = settingRepository.findAllByUser_UserId(userId);

        for (Setting setting : settings) {
            result.add(
                    SettingDto.builder()
                            .id(setting.getId())
                            .type(setting.getType().name())
                            .enabled(setting.getEnabled())
                            .build()
            );
        }

        return result;
    }

    @Override
    public List<SettingDto> updateSettingsByUserId(List<UpdateSettingRequest> request) {

        List<SettingDto> result = new ArrayList<>();

        for (UpdateSettingRequest updateSettingRequest : request) {

            Setting setting = settingRepository.getReferenceById(updateSettingRequest.getSettingId());

            setting.setEnabled(updateSettingRequest.isEnabled());

            setting = settingRepository.save(setting);

            result.add(
                    SettingDto.builder()
                            .id(setting.getId())
                            .type(setting.getType().name())
                            .enabled(setting.getEnabled())
                            .build()
            );
        }

        return result;
    }

    @Override
    public void initializeSettingsForNewUser(UUID userId) {
        for (SettingTypeEnum type : SettingTypeEnum.values()) {
            settingRepository.save(
                    Setting.builder()
                            .user(userRepository.getReferenceById(userId))
                            .type(type)
                            .build()
            );
        }
    }
}
