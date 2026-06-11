package com.aiemailreply.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ENTITY CLASS — Maps to the 'email_replies' table in MySQL.
 *
 * JPA Annotations explained:
 *   @Entity       → tells Spring this is a database table
 *   @Table        → specifies table name
 *   @Id           → marks primary key
 *   @GeneratedValue → auto-increments the ID
 *   @Column       → maps field to a specific DB column
 *   @PrePersist   → runs a method automatically before saving to DB
 *
 * Lombok Annotations:
 *   @Data         → generates getters, setters, toString, equals, hashCode
 *   @NoArgsConstructor → generates no-argument constructor
 *   @AllArgsConstructor → generates constructor with all fields
 *   @Builder      → enables builder pattern: EmailReply.builder().tone("formal").build()
 */
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
