package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.HandleToGroupRequest;
import com.loopus.loopus_be.dto.request.CreateGroupRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final IGroupService iGroupService;

    @GetMapping("/groups")
    public ResponseDto<Object> getGroups(@RequestParam UUID userId) {
        return ResponseDto.builder()
                .data(iGroupService.getAllgroupsByUserId(userId))
                .message("Lấy thành công danh sách nhóm")
                .build();
    }

    @PostMapping("/groups")
    public ResponseDto<Object> getGroups(@RequestBody CreateGroupRequest createGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.createGroup(createGroupRequest))
                .message("Tạo nhóm thành công")
                .build();
    }

    @PutMapping("/groups/add-members")
    public ResponseDto<Object> addMember(@RequestBody HandleToGroupRequest handleToGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.addMemberToGroup(handleToGroupRequest))
                .message("Thêm thành viên vào nhóm thành công")
                .build();
    }

    @DeleteMapping("/groups/leave-group")
    public ResponseDto<Object> leaveGroup(@RequestBody HandleToGroupRequest handleToGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.leaveGroup(handleToGroupRequest))
                .message("Thoát nhóm thành công")
                .build();
    }
}
