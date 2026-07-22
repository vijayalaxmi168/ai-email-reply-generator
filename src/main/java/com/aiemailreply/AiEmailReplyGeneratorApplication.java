package com.aiemailreply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AiEmailReplyGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiEmailReplyGeneratorApplication.class, args);
        System.out.println("✅ AI Email Reply Generator is running at http://localhost:5000");
    }
}
