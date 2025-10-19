package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.StoryReactionDto;
import com.loopus.loopus_be.dto.request.AddReactionRequest;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;
import com.loopus.loopus_be.exception.UsersException;
import com.loopus.loopus_be.mapper.StoryReactionMapper;
import com.loopus.loopus_be.model.Story;
import com.loopus.loopus_be.model.StoryReaction;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.StoryReactionRepository;
import com.loopus.loopus_be.repository.StoryRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.INotificationService;
import com.loopus.loopus_be.service.IService.IStoryReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryReactionService implements IStoryReactionService {

    private final StoryReactionMapper storyReactionMapper;
    private final StoryReactionRepository storyReactionRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final INotificationService iNotificationService;

    @Override
    public List<StoryReactionDto> getReactions(UUID storyId) {
        return storyReactionMapper.toDtoList(storyReactionRepository.findAllByStory_StoryIdOrderByCreatedAtDesc(storyId));
    }

    @Override
    @Transactional
    public void removeReaction(UUID reactionId) {
        StoryReaction storyReaction = storyReactionRepository.findById(reactionId)
                .orElseThrow(() -> new UsersException("Story reaction không tồn tại!"));

        storyReactionRepository.delete(storyReaction);
    }

    @Override
    @Transactional
    public StoryReactionDto updateReaction(UUID reactionId, String newReaction) {

        StoryReaction storyReaction = storyReactionRepository.findById(reactionId)
                .orElseThrow(() -> new UsersException("Reaction không tồn tại!"));

        storyReaction.setEmoji(newReaction);

        return storyReactionMapper.toDto(storyReactionRepository.save(storyReaction));
    }

    @Override
    @Transactional
    public StoryReactionDto addReaction(AddReactionRequest addReactionRequest) {
        Story story = storyRepository.findById(addReactionRequest.getStoryId())
                .orElseThrow(() -> new UsersException("Story không tồn tại!"));

        StoryReaction storyReaction = StoryReaction.builder()
                .story(story)
                .user(userRepository.getReferenceById(addReactionRequest.getUserId()))
                .emoji(addReactionRequest.getEmoji())
                .build();

        notificationDebtReminderIndividual(addReactionRequest.getUserId(), story.getUser().getUserId());

        return storyReactionMapper.toDto(storyReactionRepository.save(storyReaction));
    }

    private void notificationDebtReminderIndividual(UUID senderId, UUID receiveId) {

        Users sender = userRepository.getReferenceById(senderId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(senderId)
                        .receiverId(receiveId)
                        .groupId(null)
                        .type("REACTION")
                        .title(sender.getFullName() + " đã thả cảm xúc")
                        .message(sender.getFullName() + " đã thả cảm xúc vào ảnh của bạn!")
                        .amount(null)
                        .build()
        );
    }
}
