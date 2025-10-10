package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.StoryCommentDto;
import com.loopus.loopus_be.dto.StoryDto;
import com.loopus.loopus_be.dto.request.CreateStoryRequest;
import com.loopus.loopus_be.enums.VisibilityType;
import com.loopus.loopus_be.exception.UsersException;
import com.loopus.loopus_be.mapper.StoryCommentMapper;
import com.loopus.loopus_be.mapper.StoryMapper;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.GroupAlbum;
import com.loopus.loopus_be.model.Story;
import com.loopus.loopus_be.model.StoryComment;
import com.loopus.loopus_be.repository.*;
import com.loopus.loopus_be.service.IService.IFileService;
import com.loopus.loopus_be.service.IService.IStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryService implements IStoryService {

    private final IFileService iFileService;
    private final UserRepository userRepository;
    private final GroupAlbumRepository groupAlbumRepository;
    private final StoryMapper storyMapper;
    private final StoryRepository storyRepository;
    private final FollowRepository followRepository;
    private final GroupRepository groupRepository;
    private final StoryCommentRepository storyCommentRepository;
    private final StoryCommentMapper storyCommentMapper;

    @Override
    @Transactional
    public StoryDto createStory(CreateStoryRequest request, MultipartFile file) {

        String imgUrl = iFileService.uploadFileUrl(file);

        GroupAlbum album = null;

        if (request.getAlbumId() != null) {
            album = groupAlbumRepository.getReferenceById(request.getAlbumId());
        }
        Story story = Story.builder()
                .user(userRepository.getReferenceById(request.getUserId()))
                .imageUrl(imgUrl)
                .caption(request.getCaption())
                .visibilityType(VisibilityType.valueOf(request.getVisibilityType().toUpperCase()))
                .album(album)
                .build();

        return storyMapper.toDto(storyRepository.save(story));
    }

    @Override
    public List<StoryDto> getStoryFeed(UUID userId) {

        List<UUID> followedUserIds = followRepository.findAllByFollower_UserId(userId)
                .stream()
                .map(f -> f.getFollowing().getUserId())
                .toList();

        List<UUID> groupIds = groupRepository.findAllGroupsByUserId(userId)
                .stream()
                .map(Group::getGroupId)
                .toList();

        List<Story> stories = storyRepository.findAllByUser_UserIdInOrAlbum_Group_GroupIdInOrderByCreatedAtDesc(
                followedUserIds, groupIds
        );

        return storyMapper.toDtoList(stories);
    }

    @Override
    public List<StoryDto> getStoryByAlbumId(UUID albumId) {

        List<Story> stories = storyRepository.findAllByAlbum_AlbumIdOrderByCreatedAtDesc(albumId);

        return storyMapper.toDtoList(stories);
    }

    @Override
    public List<StoryDto> getUserStories(UUID userId) {

        List<Story> stories = storyRepository.findAllByUser_UserIdOrderByCreatedAtDesc(userId);

        return storyMapper.toDtoList(stories);
    }

    @Override
    @Transactional
    public void deleteStory(UUID storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new UsersException("Story không tồn tại!"));

        storyRepository.delete(story);
    }

    @Override
    public List<StoryCommentDto> getCommentsByStoryId(UUID storyId) {

        return storyCommentMapper.toDtoList(storyCommentRepository.findAllByStory_StoryIdOrderByCreatedAtDesc(storyId));
    }

    @Override
    @Transactional
    public void deleteCommentOrStory(UUID storyId, UUID commentId) {
        if (commentId != null) {
            StoryComment comment = storyCommentRepository.findById(commentId)
                    .orElseThrow(() -> new UsersException("Comment không tồn tại!"));

            storyCommentRepository.delete(comment);

            return;
        }

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new UsersException("Story không tồn tại!"));

        storyRepository.delete(story);
    }

    @Override
    @Transactional
    public StoryCommentDto addComment(UUID storyId, UUID userId, String content) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new UsersException("Story không tồn tại!"));

        StoryComment comment = StoryComment.builder()
                .story(story)
                .user(userRepository.getReferenceById(userId))
                .content(content)
                .build();

        return storyCommentMapper.toDto(storyCommentRepository.save(comment));
    }

    @Override
    @Transactional
    public StoryCommentDto updateComment(UUID commentId, String content) {

        StoryComment comment = storyCommentRepository.findById(commentId)
                .orElseThrow(() -> new UsersException("Comment không tồn tại!"));

        comment.setContent(content);

        return storyCommentMapper.toDto(storyCommentRepository.save(comment));
    }
}
