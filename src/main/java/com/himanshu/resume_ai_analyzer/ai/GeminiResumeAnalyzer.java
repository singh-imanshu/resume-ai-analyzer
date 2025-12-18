package com.himanshu.resume_ai_analyzer.ai;

import com.himanshu.resume_ai_analyzer.model.ResumeAnalysisResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

/**
 * Component responsible for interacting with the Google Gemini AI Model.
 * It constructs the prompt using the resume data and handles the AI response.
 */

@Component
public class GeminiResumeAnalyzer {

    private final ChatClient chatClient;

    public GeminiResumeAnalyzer(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public ResumeAnalysisResponse analyze(String resumeText, String targetRole) {
        return chatClient.prompt()
                .user(u -> u.text("""
                You are a senior professional recruiter with extensive experience hiring
                across technical, non-technical, managerial, creative, academic,
                and business roles.

                Your task is to evaluate the candidate’s resume specifically
                against the given target role.

                --------------------
                TARGET ROLE
                --------------------
                {targetRole}

                --------------------
                RESUME CONTENT
                --------------------
                {resumeText}

                --------------------
                EVALUATION GUIDELINES
                --------------------

                1. Base all conclusions strictly on the resume content.
                   Do NOT invent skills, experience, or achievements.

                2. Judge relevance only with respect to the target role.
                   Consider transferable skills where applicable.

                3. Be honest and critical. Clearly identify gaps if present.
                
                4. Minimize subjectivity.
                   Ensure your scoring is deterministic, based on provided evidence, not assumptions.

                --------------------
                CONFIDENCE SCORING RULES
                --------------------

                Set analysisConfidence using the following rules:

                - HIGH:
                  The resume clearly aligns with the target role,
                  contains sufficient relevant details,
                  and allows confident evaluation.

                - MEDIUM:
                  The resume shows partial alignment,
                  but has missing details, ambiguity,
                  or limited evidence for some key areas.

                - LOW:
                  The resume lacks sufficient information,
                  is highly generic,
                  or has weak alignment with the target role,
                  making evaluation uncertain.

                --------------------
                OUTPUT REQUIREMENTS
                --------------------

                Return a structured analysis with exactly these fields:

                - summary
                - keySkills
                - strengths
                - weaknesses
                - recommendations
                - jobFitScore (0–100 integer)
                - analysisConfidence (HIGH | MEDIUM | LOW)

                IMPORTANT:
                - The output MUST strictly match ResumeAnalysisResponse.
                - Do NOT include markdown, headings, or extra commentary.
                - Do NOT repeat the resume text.
                """)
                        .param("targetRole", targetRole)
                        .param("resumeText", resumeText))
                .call()
                .entity(ResumeAnalysisResponse.class);
    }
}