package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.StoryCommentDto;
import com.loopus.loopus_be.dto.StoryDto;
import com.loopus.loopus_be.dto.request.CreateStoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IStoryService {
    StoryDto createStory(@Valid CreateStoryRequest request, MultipartFile file);

    List<StoryDto> getStoryFeed(UUID userId);

    List<StoryDto> getStoryByAlbumId(UUID albumId);

    List<StoryDto> getUserStories(UUID userId);

    void deleteStory(UUID storyId);

    List<StoryCommentDto> getCommentsByStoryId(UUID storyId);

    void deleteCommentOrStory(UUID storyId, UUID commentId);

    StoryCommentDto addComment(UUID storyId, UUID userId, String content);

    StoryCommentDto updateComment(UUID commentId, String content);

    StoryDto getStoryDetail(UUID storyId);
}
