package com.aiemailreply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the AI Email Reply Generator application.
 *
 * @SpringBootApplication combines:
 *   - @Configuration (app config)
 *   - @EnableAutoConfiguration (auto-setup Spring beans)
 *   - @ComponentScan (scans all classes in this package)
 */
@SpringBootApplication
public class AiEmailReplyGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiEmailReplyGeneratorApplication.class, args);
        System.out.println("✅ AI Email Reply Generator is running at http://localhost:8080");
    }
}
