package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.FeedbackDto;
import com.loopus.loopus_be.dto.request.CreateFeedbackRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IFeedbackService {

    List<FeedbackDto> getFeedbacks();

    List<FeedbackDto> getFeedbacksByType(String type);

    FeedbackDto getFeedbackById(UUID feedbackId);

    FeedbackDto createFeedback(CreateFeedbackRequest request);
}
