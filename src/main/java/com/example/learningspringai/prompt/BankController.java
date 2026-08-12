package com.example.learningspringai.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    private static final String systemInstructions = """
        You are a customer service assistant for a bank.
        You will answer questions about banking, including account information, transactions, and other banking services.
        If you don't know the answer to a question, you should respond with
        "I can only help with banking-related questions." and not make up an answer.
        """;

    private final ChatClient chatClient;

    public BankController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("question") String question) {
        return chatClient.prompt()
                .system(systemInstructions)
                .user(question)
                .call()
                .content();
    }

}

