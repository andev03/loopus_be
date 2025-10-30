package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.FeedbackDto;
import com.loopus.loopus_be.dto.request.CreateFeedbackRequest;
import com.loopus.loopus_be.enums.FeedbackType;
import com.loopus.loopus_be.mapper.FeedbackMapper;
import com.loopus.loopus_be.model.Feedback;
import com.loopus.loopus_be.repository.FeedbackRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IFeedbackService;
import com.loopus.loopus_be.service.IService.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final IFileService iFileService;
    private final UserRepository userRepository;

    @Override
    public List<FeedbackDto> getFeedbacks() {

        List<Feedback> feedbacks = feedbackRepository.findAll();

        return feedbackMapper.toDtoList(feedbacks);
    }

    @Override
    public List<FeedbackDto> getFeedbacksByType(String type) {

        List<Feedback> feedbacks = feedbackRepository.findAllByType(FeedbackType.valueOf(type));

        return feedbackMapper.toDtoList(feedbacks);
    }

    @Override
    public FeedbackDto getFeedbackById(UUID feedbackId) {
        return feedbackMapper.toDto(feedbackRepository.getReferenceById(feedbackId));
    }

    @Override
    @Transactional
    public FeedbackDto createFeedback(CreateFeedbackRequest request) {

        Feedback feedback = feedbackRepository.save(
                Feedback.builder()
                        .user(userRepository.getReferenceById(request.getUserId()))
                        .content(request.getContent())
                        .type(FeedbackType.valueOf(request.getType().toUpperCase()))
                        .build()
        );

        return feedbackMapper.toDto(feedback);
    }
}
