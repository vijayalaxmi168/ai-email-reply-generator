package com.aiemailreply.repository;

import com.aiemailreply.entity.EmailReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmailReplyRepository extends JpaRepository<EmailReply, Long> {

  
    List<EmailReply> findByToneOrderByCreatedAtDesc(String tone);

    
    List<EmailReply> findTop10ByOrderByCreatedAtDesc();
}
