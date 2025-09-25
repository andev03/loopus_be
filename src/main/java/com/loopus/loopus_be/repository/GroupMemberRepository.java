package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.GroupMember;
import com.loopus.loopus_be.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findAllById_UserId(UUID userId);
    List<GroupMember> findAllById_GroupId(UUID groupId);
}
