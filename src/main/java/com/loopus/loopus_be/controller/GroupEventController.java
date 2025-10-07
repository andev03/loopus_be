package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateEventRequest;
import com.loopus.loopus_be.dto.request.ProcessInvitationRequest;
import com.loopus.loopus_be.dto.request.UpdateEventRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IGroupEventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
@Validated
public class GroupEventController {

    private final IGroupEventService iGroupEventService;

    @GetMapping("/events")
    public ResponseDto<Object> getAllEventsByGroupId(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .data(iGroupEventService.getAllByGroupId(groupId))
                .message("Lấy thành công danh sách sự kiện")
                .build();
    }

    @PostMapping("/event")
    public ResponseDto<Object> createEvent(@RequestBody @Valid CreateEventRequest request) {
        return ResponseDto.builder()
                .data(iGroupEventService.createEvent(request))
                .message("Tạo thành công sự kiện")
                .build();
    }

    @GetMapping("/event/detail")
    public ResponseDto<Object> viewDetail(@RequestParam UUID eventId) {
        return ResponseDto.builder()
                .data(iGroupEventService.viewEventDetail(eventId))
                .message("Lấy thành công chi tiết sự kiện")
                .build();
    }

    @PutMapping("/event/process-invite")
    public ResponseDto<Object> processInvitation(@RequestBody @Valid ProcessInvitationRequest request) {
        return ResponseDto.builder()
                .data(iGroupEventService.processInvitation(request))
                .message("Xử lí thành công!")
                .build();
    }

    @PutMapping("/event/update")
    public ResponseDto<Object> updateEvent(@RequestBody @Valid UpdateEventRequest request) {
        return ResponseDto.builder()
                .data(iGroupEventService.updateEvent(request))
                .message("Xử lí thành công!")
                .build();
    }

    @DeleteMapping("/event/delete")
    public ResponseDto<Object> deleteEvent(@RequestParam UUID eventId) {
        return ResponseDto.builder()
                .data(iGroupEventService.deleteEvent(eventId))
                .message("Xử lí thành công!")
                .build();
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Lấy danh sách ai đã tham gia sự kiện theo trạng thái", description = "Trạng thái có thể là: accepted, declined, null")
    public ResponseDto<Object> getEventParticipantByStatus(
            @PathVariable UUID eventId,
            @RequestParam(required = false) String status
    ) {
        return ResponseDto.builder()
                .data(iGroupEventService.getEventParticipantByStatus(eventId, status))
                .message("Lấy danh sách ai đã tham gia sự kiện!")
                .build();
    }
}
