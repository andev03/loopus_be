package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateGroupRequest;
import com.loopus.loopus_be.dto.request.HandleToGroupRequest;
import com.loopus.loopus_be.dto.request.UpdateGroupRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IGroupService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/groups/view-member")
    public ResponseDto<Object> viewMembersInGroup(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .data(iGroupService.viewMembersInGroup(groupId))
                .message("Lấy thành công danh sách nhóm")
                .build();
    }

    @PostMapping("/groups")
    public ResponseDto<Object> createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.createGroup(createGroupRequest))
                .message("Tạo nhóm thành công")
                .build();
    }

    @PutMapping("/groups/add-members")
    public ResponseDto<Object> addMember(@RequestBody @Valid HandleToGroupRequest handleToGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.addMemberToGroup(handleToGroupRequest))
                .message("Thêm thành viên vào nhóm thành công")
                .build();
    }

    @DeleteMapping("/groups/leave-group")
    public ResponseDto<Object> leaveGroup(@RequestBody @Valid HandleToGroupRequest handleToGroupRequest) {
        return ResponseDto.builder()
                .data(iGroupService.leaveGroup(handleToGroupRequest))
                .message("Thoát nhóm thành công")
                .build();
    }

    @GetMapping("/group")
    public ResponseDto<Object> findGroupByName(@RequestParam String groupName, @RequestParam UUID userId) {
        return ResponseDto.builder()
                .data(iGroupService.findUserByName(groupName, userId))
                .message("Tìm nhóm thành công")
                .build();
    }

    @PutMapping(value = "/group/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Object> updateAvatar(
            @Parameter(description = "File to update", required = true)
            @RequestPart("file") MultipartFile file, @RequestParam UUID groupId) {

        return ResponseDto.builder()
                .data(iGroupService.updateAvatar(groupId, file))
                .status(HttpStatus.OK.value())
                .message("Cập nhật avatar thành công")
                .build();
    }

    @PutMapping(value = "/group/update-information")
    public ResponseDto<Object> updateInformation(@RequestBody @Valid UpdateGroupRequest request) {

        return ResponseDto.builder()
                .data(iGroupService.updateInformation(request))
                .status(HttpStatus.OK.value())
                .message("Cập nhật avatar thành công")
                .build();
    }
}
