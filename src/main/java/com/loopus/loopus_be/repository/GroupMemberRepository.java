package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.GroupMember;
import com.loopus.loopus_be.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}
