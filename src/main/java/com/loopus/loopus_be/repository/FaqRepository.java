package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FaqRepository extends JpaRepository<Faq, UUID> {
}
