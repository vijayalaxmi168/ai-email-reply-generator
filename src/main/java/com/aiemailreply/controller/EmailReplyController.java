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


@Slf4j
@RestController
@RequestMapping("/api/email-replies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmailReplyController {

    private final EmailReplyService emailReplyService;

   
    @PostMapping("/generate")
    public ResponseEntity<EmailReplyResponseDTO> generateReply(
            @Valid @RequestBody EmailReplyRequestDTO requestDTO) {

        log.info("Received request to generate reply. Tone: {}", requestDTO.getTone());
        EmailReplyResponseDTO response = emailReplyService.generateAndSaveReply(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   
    @GetMapping
    public ResponseEntity<List<EmailReplyResponseDTO>> getAllReplies() {
        List<EmailReplyResponseDTO> replies = emailReplyService.getAllReplies();
        return ResponseEntity.ok(replies);
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<EmailReplyResponseDTO> getReplyById(@PathVariable Long id) {
        EmailReplyResponseDTO reply = emailReplyService.getReplyById(id);
        return ResponseEntity.ok(reply);
    }

   
    @GetMapping("/tone/{tone}")
    public ResponseEntity<List<EmailReplyResponseDTO>> getRepliesByTone(
            @PathVariable String tone) {
        List<EmailReplyResponseDTO> replies = emailReplyService.getRepliesByTone(tone);
        return ResponseEntity.ok(replies);
    }
}
