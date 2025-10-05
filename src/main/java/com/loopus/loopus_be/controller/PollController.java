package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.AddOptionRequest;
import com.loopus.loopus_be.dto.request.CreatePollRequest;
import com.loopus.loopus_be.dto.request.VotePollRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IPollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PollController {

    private final IPollService iPollService;

    @GetMapping("/polls")
    public ResponseDto<Object> getAllPollsByGroup(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách bình chọn thành công!")
                .data(iPollService.getPollsByGroupId(groupId))
                .build();
    }

    @GetMapping("/poll")
    public ResponseDto<Object> getPollByPollId(@RequestParam UUID pollId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy bình chọn thành công!")
                .data(iPollService.getPollById(pollId))
                .build();
    }

    @PostMapping("/poll/create")
    public ResponseDto<Object> createPoll(@RequestBody @Valid CreatePollRequest request) {
        return ResponseDto.builder()
                .data(iPollService.createPoll(request))
                .status(HttpStatus.OK.value())
                .message("Thêm cuộc bình chọn thành công!")
                .build();
    }

    @PostMapping("/poll/add-option")
    public ResponseDto<Object> addOption(@RequestBody @Valid AddOptionRequest request) {
        return ResponseDto.builder()
                .data(iPollService.addOptionToPoll(request))
                .status(HttpStatus.OK.value())
                .message("Thêm lựa thành công!")
                .build();
    }

    @PostMapping("/poll/vote")
    public ResponseDto<Object> votePollBy(@RequestBody @Valid VotePollRequest request) {
        return ResponseDto.builder()
                .data(iPollService.votePoll(request))
                .status(HttpStatus.OK.value())
                .message("Bình chọn thành công!")
                .build();
    }

    @DeleteMapping("/poll/delete")
    public ResponseDto<Object> deletePollByPollId(@RequestParam UUID pollId) {
        iPollService.deletePollById(pollId);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Xoá bình chọn thành công!")
                .build();
    }
}
