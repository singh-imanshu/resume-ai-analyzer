# Resume AI Analyzer

This is a Spring Boot app that uses Google Gemini to evaluate resumes against specific job descriptions. You upload a PDF, give it a target role, and it tells you if the candidate is a fit (and why).

It handles the PDF parsing, context extraction, and prompts Gemini to act like a recruiter—giving you a 0-100 fit score, pointing out skill gaps, and offering specific advice.

## What it does
* **PDF Analysis:** Uses Apache PDFBox to strip text from uploaded resumes (no manual copy-pasting required).
* **Job Fit Scoring:** You provide a job title or description (`targetRole`), and the AI evaluates the resume specifically for that context.
* **Structured Feedback:** Instead of a generic text blob, it returns a clean JSON object with:
    * Fit Score (0-100)
    * Key Strengths & Weaknesses
    * Missing Skills
    * Actionable Recommendations
* **Model:** Powered by **Gemini 2.5 Flash** via Spring AI.

## The Stack
* **Java 21**
* **Spring Boot 3.4.1**
* **Spring AI** (Google GenAI module)
* **Apache PDFBox** (for PDF processing)
* **Docker** (Alpine-based)

## Setup

### 1. Get an API Key
You'll need a Google Gemini API key.

### 2. Configure the App
You can either set an environment variable or create a `.env` file in the root directory:

**Option A: .env file**

```properties
GEMINI_API_KEY=your_key_here
```

**Option B: Export variable**

```properties
export GEMINI_API_KEY=your_key_here
```

### 3. Run it

**With Maven**

```properties
./mvnw clean install
./mvnw spring-boot:run
```

**With Docker**

```properties
docker build -t resume-analyzer .
docker run -p 8080:8080 -e GEMINI_API_KEY=your_key_here resume-analyzer
```

### 4. How to use it

The app runs on port ```8080```. The main endpoint is ```/api/resume/analyze```.

**Example request**

Send a **POST** request with ```multipart/form-data```:
* file: ```[your_resume.pdf]```
* targetRole: ```Senior Java Engineer```

**cURL Example**
```properties
curl -X POST "http://localhost:8080/api/resume/analyze" \
     -H "Content-Type: multipart/form-data" \
     -F "file=@/path/to/resume.pdf" \
     -F "targetRole=Senior Java Engineer"
```

**Example Output**
```properties
{
  "summary": "Solid backend engineer but lacks cloud-native experience required for this role.",
  "keySkills": ["Java", "Spring Boot", "PostgreSQL"],
  "strengths": ["Strong database design", "Clean code practices"],
  "weaknesses": ["No AWS/Azure experience", "Limited CI/CD knowledge"],
  "recommendations": ["Get certified in AWS", "Learn Docker/Kubernetes"],
  "analysisConfidence": "HIGH",
  "jobFitScore": 75
}
```
**API Docs**

If you prefer a UI to test this out, go to ```http://localhost:8080/swagger-ui.html``` once the app is running.