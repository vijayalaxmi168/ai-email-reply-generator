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

    @Column(name = "email_content", columnDefinition = "TEXT", nullable = false)
    private String emailContent;

    @Column(name = "tone", nullable = false, length = 50)
    private String tone;

    
    @Column(name = "generated_reply", columnDefinition = "TEXT")
    private String generatedReply;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
