package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.GroupAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupAlbumRepository extends JpaRepository<GroupAlbum, UUID> {
}
