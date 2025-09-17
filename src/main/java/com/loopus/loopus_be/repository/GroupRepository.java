package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}
