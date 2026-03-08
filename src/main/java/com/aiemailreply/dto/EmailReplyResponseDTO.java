package com.aiemailreply.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * RESPONSE DTO — Sent back to the frontend after generating a reply.
 * Contains the AI-generated reply and metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailReplyResponseDTO {

    private Long id;
    private String emailContent;
    private String tone;
    private String generatedReply;
    private LocalDateTime createdAt;
}
