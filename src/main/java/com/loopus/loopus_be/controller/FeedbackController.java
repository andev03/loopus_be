package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.FeedbackDto;
import com.loopus.loopus_be.dto.request.ChatTextRequest;
import com.loopus.loopus_be.dto.request.CreateFeedbackRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FeedbackController {

    private final IFeedbackService iFeedbackService;

    @GetMapping("/feedbacks")
    @Operation(
            summary = "Lấy tất cả feedback hoặc filter (không đưa type thì là filter)",
            description = "Có 2 type: bug (báo lỗi), suggestion (góp ý cải thiện), other (khác)"
    )
    public ResponseDto<Object> getFeedbacksByType(@RequestParam(required = false) String type) {

        List<FeedbackDto> feedbackDto = new ArrayList<>();

        if (type != null) {
            feedbackDto = iFeedbackService.getFeedbacksByType(type.toUpperCase());
        } else {
            feedbackDto = iFeedbackService.getFeedbacks();
        }

        return ResponseDto.builder()
                .data(feedbackDto)
                .message("Lấy thành công danh sách feedback theo type")
                .build();
    }

    @GetMapping("/feedback")
    @Operation(summary = "Xem chi tiết một feedback")
    public ResponseDto<Object> getFeedbacks(@RequestParam UUID feedbackId) {
        return ResponseDto.builder()
                .data(iFeedbackService.getFeedbackById(feedbackId))
                .message("Lấy thành công feedback theo ID")
                .build();
    }

    @PostMapping(value = "/feedback", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Tạo feedback", description = "Có 2 type: bug (dành cho báo lỗi ứng dũng), suggestion (dành cho góp ý cải thiện)")
    public ResponseDto<Object> createFeedbacks(
            @Parameter(description = "Request JSON", content = @Content(mediaType = "application/json"))
            @RequestPart("request") @Valid CreateFeedbackRequest request,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseDto.builder()
                .data(iFeedbackService.createFeedback(request, file))
                .message("Lấy thành công feedback theo ID")
                .build();
    }
}
