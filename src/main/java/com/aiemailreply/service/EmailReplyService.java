package com.aiemailreply.service;

import com.aiemailreply.dto.EmailReplyRequestDTO;
import com.aiemailreply.dto.EmailReplyResponseDTO;
import com.aiemailreply.entity.EmailReply;
import com.aiemailreply.repository.EmailReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICE LAYER — Contains all the business logic.
 *
 * This layer sits between the Controller (API) and Repository (DB).
 * The Controller delegates work here. This class:
 *   1. Calls OpenAI to generate the reply
 *   2. Saves the result to the database
 *   3. Returns data to the controller
 *
 * We never let controllers directly touch the repository.
 * This keeps code clean, testable, and maintainable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailReplyService {

    private final EmailReplyRepository emailReplyRepository;
    private final OpenAiService openAiService;

    /**
     * Main method: Generate an AI reply and save it to DB.
     *
     * Flow:
     *   1. Take input from controller (DTO)
     *   2. Call OpenAI service to get reply
     *   3. Build entity object
     *   4. Save to DB via repository
     *   5. Convert entity to response DTO and return
     */
    public EmailReplyResponseDTO generateAndSaveReply(EmailReplyRequestDTO requestDTO) {
        log.info("Generating reply for tone: {}", requestDTO.getTone());

        // Step 1: Call OpenAI to generate the reply
        String generatedReply = openAiService.generateEmailReply(
                requestDTO.getEmailContent(),
                requestDTO.getTone()
        );

        // Step 2: Build the entity using Lombok's @Builder pattern
        EmailReply emailReply = EmailReply.builder()
                .emailContent(requestDTO.getEmailContent())
                .tone(requestDTO.getTone())
                .generatedReply(generatedReply)
                .build();

        // Step 3: Save to MySQL database
        EmailReply savedReply = emailReplyRepository.save(emailReply);
        log.info("Reply saved with ID: {}", savedReply.getId());

        // Step 4: Convert entity to response DTO and return
        return mapToResponseDTO(savedReply);
    }

    /**
     * Fetch all saved replies, newest first.
     */
    public List<EmailReplyResponseDTO> getAllReplies() {
        return emailReplyRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a single reply by its ID.
     */
    public EmailReplyResponseDTO getReplyById(Long id) {
        EmailReply reply = emailReplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found with ID: " + id));
        return mapToResponseDTO(reply);
    }

    /**
     * Get replies filtered by tone.
     */
    public List<EmailReplyResponseDTO> getRepliesByTone(String tone) {
        return emailReplyRepository.findByToneOrderByCreatedAtDesc(tone)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper: Maps Entity → Response DTO.
     * We don't expose the entity directly to the outside world.
     */
    private EmailReplyResponseDTO mapToResponseDTO(EmailReply entity) {
        return EmailReplyResponseDTO.builder()
                .id(entity.getId())
                .emailContent(entity.getEmailContent())
                .tone(entity.getTone())
                .generatedReply(entity.getGeneratedReply())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
