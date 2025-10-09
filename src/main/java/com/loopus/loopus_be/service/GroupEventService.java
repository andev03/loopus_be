package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.EventParticipantDto;
import com.loopus.loopus_be.dto.GroupEventDto;
import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.CreateEventRequest;
import com.loopus.loopus_be.dto.request.ProcessInvitationRequest;
import com.loopus.loopus_be.dto.request.UpdateEventRequest;
import com.loopus.loopus_be.enums.EventStatus;
import com.loopus.loopus_be.enums.ParticipationStatus;
import com.loopus.loopus_be.mapper.EventParticipantMapper;
import com.loopus.loopus_be.mapper.GroupEventMapper;
import com.loopus.loopus_be.mapper.UserMapper;
import com.loopus.loopus_be.model.EventParticipant;
import com.loopus.loopus_be.model.GroupEvent;
import com.loopus.loopus_be.model.GroupMember;
import com.loopus.loopus_be.model.embedded_key.EventParticipantId;
import com.loopus.loopus_be.repository.EventParticipantRepository;
import com.loopus.loopus_be.repository.GroupEventRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IGroupEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupEventService implements IGroupEventService {

    private final GroupEventRepository groupEventRepository;
    private final GroupEventMapper groupEventMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final EventParticipantMapper eventParticipantMapper;
    private final EventParticipantRepository eventParticipantRepository;
    private final UserMapper userMapper;

    @Override
    public List<GroupEventDto> getAllByGroupId(UUID groupId) {
        return groupEventMapper.toDtoList(groupEventRepository.findAllByGroup_GroupIdAndStatus(groupId, EventStatus.PENDING));
    }

    @Override
    @Transactional
    public GroupEventDto createEvent(CreateEventRequest request) {
        GroupEvent groupEvent = groupEventRepository.save(
                GroupEvent.builder()
                        .group(groupRepository.getReferenceById(request.getGroupId()))
                        .creator(userRepository.getReferenceById(request.getCreatorId()))
                        .title(request.getTitle())
                        .eventDate(request.getEventDate())
                        .eventTime(request.getEventTime())
                        .repeatType(request.getRepeatType())
                        .build()
        );

        return groupEventMapper.toDto(groupEvent);
    }

    @Override
    public GroupEventDto viewEventDetail(UUID eventId) {
        return groupEventMapper.toDto(groupEventRepository.getReferenceById(eventId));
    }

    @Override
    @Transactional
    public EventParticipantDto processInvitation(ProcessInvitationRequest request) {

        boolean flag = eventParticipantRepository
                .existsById(new EventParticipantId(request.getEventId(), request.getUserId()));

        if (!flag) {
            return eventParticipantMapper.toDto(eventParticipantRepository.save(
                    EventParticipant.builder()
                            .id(new EventParticipantId(request.getEventId(), request.getUserId()))
                            .event(groupEventRepository.getReferenceById(request.getEventId()))
                            .user(userRepository.getReferenceById(request.getUserId()))
                            .status(request.getStatus())
                            .build()
            ));
        }

        EventParticipant eventParticipant = eventParticipantRepository
                .getReferenceById(new EventParticipantId(request.getEventId(), request.getUserId()));

        eventParticipant.setStatus(request.getStatus());

        return eventParticipantMapper.toDto(eventParticipantRepository.save(eventParticipant));
    }

    @Override
    @Transactional
    public GroupEventDto updateEvent(UpdateEventRequest request) {

        GroupEvent groupEvent = groupEventRepository.getReferenceById(request.getEventId());

        groupEvent.setTitle(request.getTitle());
        groupEvent.setEventDate(request.getEventDate());
        groupEvent.setEventTime(request.getEventTime());
        groupEvent.setRepeatType(request.getRepeatType());

        return groupEventMapper.toDto(groupEventRepository.save(groupEvent));
    }

    @Override
    @Transactional
    public GroupEventDto deleteEvent(UUID eventId) {
        GroupEvent groupEvent = groupEventRepository.getReferenceById(eventId);

        groupEvent.setStatus(EventStatus.DELETED);

        return groupEventMapper.toDto(groupEventRepository.save(groupEvent));
    }

    @Override
    public List<UsersDto> getEventParticipantByStatus(UUID eventId, String status) {
        List<UsersDto> result = new ArrayList<>();

        if (status != null) {

            List<EventParticipantDto> participants = eventParticipantMapper.toDtoList(
                    eventParticipantRepository.
                            findAllByEvent_EventIdAndStatus(eventId, ParticipationStatus.valueOf(status.toUpperCase()))
            );

            for (EventParticipantDto participant : participants) {
                result.add(participant.getUser());
            }

            return result;
        }

        Set<EventParticipant> eventParticipants = groupEventRepository.getReferenceById(eventId).getParticipants();

        Set<GroupMember> groupMembers =
                groupRepository.getReferenceById(
                        groupEventRepository.getReferenceById(eventId).getGroup().getGroupId()
                ).getGroupMembers();

        if (eventParticipants.isEmpty()) {
            for (GroupMember groupMember : groupMembers) {
                result.add(userMapper.toDto(groupMember.getUser()));
            }
        }

        for (GroupMember groupMember : groupMembers) {
            for (EventParticipant eventParticipant : eventParticipants) {
                if (!groupMember.getUser().getUserId().equals(eventParticipant.getUser().getUserId())) {
                    result.add(userMapper.toDto(groupMember.getUser()));
                }
            }
        }

        return result;
    }
}
