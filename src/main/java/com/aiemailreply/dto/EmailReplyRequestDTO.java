package com.aiemailreply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailReplyRequestDTO {

    @NotBlank(message = "Email content is required")
    @Size(min = 10, max = 5000, message = "Email content must be between 10 and 5000 characters")
    private String emailContent;

    @NotBlank(message = "Tone is required")
    private String tone;
}
