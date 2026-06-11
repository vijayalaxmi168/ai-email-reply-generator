package com.aiemailreply.controller;

import com.aiemailreply.dto.EmailReplyRequestDTO;
import com.aiemailreply.dto.EmailReplyResponseDTO;
import com.aiemailreply.service.EmailReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER — Exposes HTTP API endpoints.
 *
 * @RestController → combines @Controller + @ResponseBody
 *                   All methods return JSON (not HTML)
 * @RequestMapping → base URL prefix for all endpoints in this class
 * @CrossOrigin    → allows frontend (JS/React) from any origin to call these APIs
 *
 * HTTP Methods:
 *   POST   → Create a new resource
 *   GET    → Read/Retrieve data
 *   PUT    → Update a resource
 *   DELETE → Delete a resource
 */
@Slf4j
@RestController
@RequestMapping("/api/email-replies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmailReplyController {

    private final EmailReplyService emailReplyService;

    /**
     * POST /api/email-replies/generate
     *
     * Accepts email content + tone, calls AI, saves result, and returns it.
     *
     * @Valid → triggers input validation (from @NotBlank, @Size on DTO)
     * @RequestBody → reads JSON from HTTP request body and maps to DTO
     *
     * Sample Request:
     * POST http://localhost:8080/api/email-replies/generate
     * Body: { "emailContent": "Please review...", "tone": "professional" }
     */
    @PostMapping("/generate")
    public ResponseEntity<EmailReplyResponseDTO> generateReply(
            @Valid @RequestBody EmailReplyRequestDTO requestDTO) {

        log.info("Received request to generate reply. Tone: {}", requestDTO.getTone());
        EmailReplyResponseDTO response = emailReplyService.generateAndSaveReply(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/email-replies
     * Returns all saved replies (most recent 10).
     */
    @GetMapping
    public ResponseEntity<List<EmailReplyResponseDTO>> getAllReplies() {
        List<EmailReplyResponseDTO> replies = emailReplyService.getAllReplies();
        return ResponseEntity.ok(replies);
    }

    /**
     * GET /api/email-replies/{id}
     * Returns a single reply by its ID.
     *
     * @PathVariable → extracts {id} from the URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmailReplyResponseDTO> getReplyById(@PathVariable Long id) {
        EmailReplyResponseDTO reply = emailReplyService.getReplyById(id);
        return ResponseEntity.ok(reply);
    }

    /**
     * GET /api/email-replies/tone/{tone}
     * Returns all replies filtered by tone.
     * Example: GET /api/email-replies/tone/professional
     */
    @GetMapping("/tone/{tone}")
    public ResponseEntity<List<EmailReplyResponseDTO>> getRepliesByTone(
            @PathVariable String tone) {
        List<EmailReplyResponseDTO> replies = emailReplyService.getRepliesByTone(tone);
        return ResponseEntity.ok(replies);
    }
}
