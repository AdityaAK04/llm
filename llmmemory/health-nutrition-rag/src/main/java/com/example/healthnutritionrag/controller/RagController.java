package com.example.healthnutritionrag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/rag")
@CrossOrigin(origins = "http://localhost:5173") // Enable CORS for React communication
public class RagController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("nutrition-", ".pdf");
            file.transferTo(tempFile.toFile());

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                    tempFile.toUri().toString(),
                    PdfDocumentReaderConfig.builder()
                            .withPageTopMargin(0)
                            .withPageBottomMargin(0)
                            .build()
            );

            List<Document> documents = pdfReader.get();
            vectorStore.add(documents);

            return "PDF successfully uploaded, embedded, and stored in Vector Store!";
        } catch (IOException e) {
            return "Failed to process PDF: " + e.getMessage();
        }
    }

    @GetMapping("/query")
    public String queryRag(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .call()
                .content();
    }
}
