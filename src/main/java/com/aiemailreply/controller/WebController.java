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

/**
 * WEB CONTROLLER — Handles browser requests and returns HTML pages.
 *
 * Difference between @RestController and @Controller:
 *   @RestController → returns JSON (for APIs)
 *   @Controller     → returns HTML template names (for web pages)
 *
 * Thymeleaf maps the returned String (e.g., "index") to
 * src/main/resources/templates/index.html
 *
 * Model → carries data from Java to the HTML template
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {

    private final EmailReplyService emailReplyService;

    /**
     * GET /  or  GET /index
     * Displays the main page with the email reply form.
     */
    @GetMapping({"/", "/index"})
    public String showIndexPage(Model model) {
        // Pass an empty DTO to bind form fields
        model.addAttribute("request", new EmailReplyRequestDTO());
        // Load recent replies to show in history table
        List<EmailReplyResponseDTO> recentReplies = emailReplyService.getAllReplies();
        model.addAttribute("recentReplies", recentReplies);
        return "index"; // → renders templates/index.html
    }

    /**
     * POST /generate
     * Handles form submission from the UI.
     * Generates the reply and sends it back to the same page.
     */
    @PostMapping("/generate")
    public String generateReply(
            @Valid @ModelAttribute("request") EmailReplyRequestDTO requestDTO,
            BindingResult bindingResult,   // Holds validation errors
            Model model) {

        // If validation fails (e.g., empty email), go back to form with errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("recentReplies", emailReplyService.getAllReplies());
            return "index";
        }

        try {
            // Generate reply via AI + save to DB
            EmailReplyResponseDTO response = emailReplyService.generateAndSaveReply(requestDTO);
            model.addAttribute("generatedReply", response.getGeneratedReply());
            model.addAttribute("success", true);
        } catch (Exception e) {
            log.error("Error generating reply: {}", e.getMessage());
            model.addAttribute("errorMessage", "Failed to generate reply. Please check your API key.");
        }

        // Reload form and history
        model.addAttribute("request", new EmailReplyRequestDTO());
        model.addAttribute("recentReplies", emailReplyService.getAllReplies());
        return "index";
    }

    /**
     * GET /history
     * Shows the full reply history page.
     */
    @GetMapping("/history")
    public String showHistoryPage(Model model) {
        List<EmailReplyResponseDTO> replies = emailReplyService.getAllReplies();
        model.addAttribute("replies", replies);
        return "history"; // → renders templates/history.html
    }
}
