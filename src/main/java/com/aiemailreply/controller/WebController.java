package com.aiemailreply.controller;

import com.aiemailreply.dto.EmailReplyRequestDTO;
import com.aiemailreply.dto.EmailReplyResponseDTO;
import com.aiemailreply.service.EmailReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {

    private final EmailReplyService emailReplyService;

    
    @GetMapping({"/", "/index"})
    public String showIndexPage(Model model) {
        model.addAttribute("request", new EmailReplyRequestDTO());
        List<EmailReplyResponseDTO> recentReplies = emailReplyService.getAllReplies();
        model.addAttribute("recentReplies", recentReplies);
        return "index"; 
    }

  
    @PostMapping("/generate")
    public String generateReply(
            @Valid @ModelAttribute("request") EmailReplyRequestDTO requestDTO,
            BindingResult bindingResult,   // Holds validation errors
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("recentReplies", emailReplyService.getAllReplies());
            return "index";
        }

        try {
            EmailReplyResponseDTO response = emailReplyService.generateAndSaveReply(requestDTO);
            model.addAttribute("generatedReply", response.getGeneratedReply());
            model.addAttribute("success", true);
        } catch (Exception e) {
            log.error("Error generating reply: {}", e.getMessage());
            model.addAttribute("errorMessage", "Failed to generate reply. Please check your API key.");
        }

        model.addAttribute("request", new EmailReplyRequestDTO());
        model.addAttribute("recentReplies", emailReplyService.getAllReplies());
        return "index";
    }

   
    @GetMapping("/history")
    public String showHistoryPage(Model model) {
        List<EmailReplyResponseDTO> replies = emailReplyService.getAllReplies();
        model.addAttribute("replies", replies);
        return "history"; 
    }
}
