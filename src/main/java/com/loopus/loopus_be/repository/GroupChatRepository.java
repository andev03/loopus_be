package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupChatRepository extends JpaRepository<GroupChat, UUID> {

    List<GroupChat> findByGroup_GroupIdOrderByCreatedAtAsc(UUID groupId);
}
