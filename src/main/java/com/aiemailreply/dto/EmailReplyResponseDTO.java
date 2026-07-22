package com.aiemailreply.dto;

import lombok.*;
import java.time.LocalDateTime;


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
