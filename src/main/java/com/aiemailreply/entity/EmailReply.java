package com.aiemailreply.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "email_replies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The original email content the user entered
    @Column(name = "email_content", columnDefinition = "TEXT", nullable = false)
    private String emailContent;

    // Tone selected by user: PROFESSIONAL, FRIENDLY, SHORT
    @Column(name = "tone", nullable = false, length = 50)
    private String tone;

    // The AI-generated reply stored here
    @Column(name = "generated_reply", columnDefinition = "TEXT")
    private String generatedReply;

    // Automatically set when record is saved
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * @PrePersist runs automatically before INSERT into DB.
     * We use it to auto-set the createdAt timestamp.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
