package com.loopus.loopus_be.enums;

import lombok.Getter;

@Getter
public enum SettingTypeEnum {

    SOUND("Âm thông báo"),
    DEVICE_NOTIFICATION("Nhận thông báo trên thiết bị"),
    GROUP_TRANSACTION("Giao dịch nhóm"),
    REMINDER("Nhắc nhở"),
    SECURITY_ALERT("Cảnh báo bảo mật"),
    SERVICE_PROMO("Ưu đãi dịch vụ"),
    VOUCHER("Voucher & mã giảm giá"),
    ADVERTISING("Quảng cáo"),
    FRIENDS_AND_GROUPS("Bạn bè & nhóm"),
    GROUP_CHANGE("Thay đổi nhóm"),
    SURVEY_FEEDBACK("Khảo sát & phản hồi");

    private final String description;

    SettingTypeEnum(String description) {
        this.description = description;
    }
}
