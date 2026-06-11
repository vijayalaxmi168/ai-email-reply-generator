package com.aiemailreply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO = Data Transfer Object
 *
 * WHY USE DTOs?
 * We never expose Entity classes directly to the outside world.
 * DTOs act as a contract between the frontend and backend.
 * They also allow input validation using annotations.
 *
 * This DTO carries the user's request: email content + tone.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailReplyRequestDTO {

    // @NotBlank → field cannot be null or empty or just spaces
    @NotBlank(message = "Email content is required")
    @Size(min = 10, max = 5000, message = "Email content must be between 10 and 5000 characters")
    private String emailContent;

    // Tone values: "professional", "friendly", "short"
    @NotBlank(message = "Tone is required")
    private String tone;
}
