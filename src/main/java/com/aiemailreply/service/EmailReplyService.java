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


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailReplyService {

    private final EmailReplyRepository emailReplyRepository;
    private final OpenAiService openAiService;

   
    public EmailReplyResponseDTO generateAndSaveReply(EmailReplyRequestDTO requestDTO) {
        log.info("Generating reply for tone: {}", requestDTO.getTone());

        String generatedReply = openAiService.generateEmailReply(
                requestDTO.getEmailContent(),
                requestDTO.getTone()
        );

        EmailReply emailReply = EmailReply.builder()
                .emailContent(requestDTO.getEmailContent())
                .tone(requestDTO.getTone())
                .generatedReply(generatedReply)
                .build();

        EmailReply savedReply = emailReplyRepository.save(emailReply);
        log.info("Reply saved with ID: {}", savedReply.getId());

        return mapToResponseDTO(savedReply);
    }

    
    public List<EmailReplyResponseDTO> getAllReplies() {
        return emailReplyRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

   
    public EmailReplyResponseDTO getReplyById(Long id) {
        EmailReply reply = emailReplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found with ID: " + id));
        return mapToResponseDTO(reply);
    }

  
    public List<EmailReplyResponseDTO> getRepliesByTone(String tone) {
        return emailReplyRepository.findByToneOrderByCreatedAtDesc(tone)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

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
