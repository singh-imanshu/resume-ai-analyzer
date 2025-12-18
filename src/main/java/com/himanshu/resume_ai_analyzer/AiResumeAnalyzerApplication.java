package com.himanshu.resume_ai_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * The main entry point for the AI Resume Analyzer application.
 * This class bootstraps the Spring Boot application, initializing the Spring context
 * and all configured components (Controllers, Services, AI Clients).
 */
@SpringBootApplication
public class AiResumeAnalyzerApplication {
    public static void main(String[] args) {
		SpringApplication.run(AiResumeAnalyzerApplication.class, args);
	}
}
