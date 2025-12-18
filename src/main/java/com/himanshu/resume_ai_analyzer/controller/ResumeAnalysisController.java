package com.himanshu.resume_ai_analyzer.controller;

import com.himanshu.resume_ai_analyzer.model.ResumeAnalysisResponse;
import com.himanshu.resume_ai_analyzer.service.ResumeAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * REST Controller that handles incoming HTTP requests for resume analysis.
 * It serves as the interface between the external client (frontend/Postman) and the backend service.
 */
@RestController
@RequestMapping("/api/resume")
public class ResumeAnalysisController {
    private final ResumeAnalysisService service;
    public ResumeAnalysisController(ResumeAnalysisService service) {
        this.service = service;
    }
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResumeAnalysisResponse analyze(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetRole") String targetRole
    ) throws IOException {
        return service.analyze(file, targetRole);
    }
}