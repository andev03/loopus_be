package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.EventParticipantDto;
import com.loopus.loopus_be.dto.GroupEventDto;
import com.loopus.loopus_be.dto.request.CreateEventRequest;
import com.loopus.loopus_be.dto.request.ProcessInvitationRequest;
import com.loopus.loopus_be.dto.request.UpdateEventRequest;

import java.util.List;
import java.util.UUID;

public interface IGroupEventService {

    List<GroupEventDto> getAllByGroupId(UUID groupId);

    GroupEventDto createEvent(CreateEventRequest request);

    GroupEventDto viewEventDetail(UUID eventId);

    EventParticipantDto processInvitation(ProcessInvitationRequest request);

    GroupEventDto updateEvent(UpdateEventRequest request);

    GroupEventDto deleteEvent(UUID eventId);
}
