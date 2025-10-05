package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.enums.EventStatus;
import com.loopus.loopus_be.model.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupEventRepository extends JpaRepository<GroupEvent, UUID> {

    List<GroupEvent> findAllByGroup_GroupIdAndStatus(UUID groupId, EventStatus status);
}
