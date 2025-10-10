package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.StoryReactionDto;
import com.loopus.loopus_be.dto.request.AddReactionRequest;

import java.util.List;
import java.util.UUID;

public interface IStoryReactionService {
    List<StoryReactionDto> getReactions(UUID storyId);

    void removeReaction(UUID reactionId);

    StoryReactionDto updateReaction(UUID reactionId, String newReaction);

    StoryReactionDto addReaction(AddReactionRequest addReactionRequest);
}
