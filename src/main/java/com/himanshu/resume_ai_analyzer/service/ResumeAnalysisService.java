package com.himanshu.resume_ai_analyzer.service;

import com.himanshu.resume_ai_analyzer.ai.GeminiResumeAnalyzer;
import com.himanshu.resume_ai_analyzer.model.ResumeAnalysisResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service class responsible for the core business logic.
 * It handles the orchestration of parsing the PDF file and invoking the AI analyzer.
 */
@Service
public class ResumeAnalysisService {

    private final GeminiResumeAnalyzer gemini;

    public ResumeAnalysisService(GeminiResumeAnalyzer gemini) {
        this.gemini = gemini;
    }
    /**
     * Orchestrates the analysis process:
     * 1. Extracts raw text from the uploaded PDF file.
     * 2. Logs the extracted text (for debugging).
     * 3. Sends the text to the AI model for analysis.
     */
    public ResumeAnalysisResponse analyze(MultipartFile file, String targetRole) throws IOException {
        // 1. Extract text from the PDF file
        String resumeText = extractTextFromPdf(file);

        // 2. Send the extracted text to AI Analyzer
        return gemini.analyze(resumeText, targetRole);
    }


    //Helper method to extract text content from a multipart PDF file using Apache PDFBox.
    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}