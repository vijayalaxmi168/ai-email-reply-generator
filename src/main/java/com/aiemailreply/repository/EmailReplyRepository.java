package com.aiemailreply.repository;

import com.aiemailreply.entity.EmailReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY LAYER — Handles all database operations.
 *
 * Spring Data JPA magic:
 * By extending JpaRepository<EmailReply, Long>, Spring automatically
 * provides these methods WITHOUT writing any SQL:
 *   - save(entity)        → INSERT or UPDATE
 *   - findById(id)        → SELECT by ID
 *   - findAll()           → SELECT all records
 *   - deleteById(id)      → DELETE by ID
 *   - count()             → COUNT(*)
 *
 * You can also write custom queries using method naming conventions
 * or @Query annotation (see examples below).
 */
@Repository
public interface EmailReplyRepository extends JpaRepository<EmailReply, Long> {

    /**
     * Spring auto-generates SQL for this based on method name:
     * SELECT * FROM email_replies WHERE tone = ? ORDER BY created_at DESC
     */
    List<EmailReply> findByToneOrderByCreatedAtDesc(String tone);

    /**
     * Find latest 10 replies ordered by newest first
     * SELECT * FROM email_replies ORDER BY created_at DESC LIMIT 10
     */
    List<EmailReply> findTop10ByOrderByCreatedAtDesc();
}
