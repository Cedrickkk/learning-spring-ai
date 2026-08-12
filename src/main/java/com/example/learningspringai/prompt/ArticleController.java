package com.example.learningspringai.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static final String systemInstructions = """
            Blog Post Generator Guidelines:
            
            1. Length & Purpose: Generate 500-word blog posts that inform and engage general audiences.

            2. Structure:
                  - Introduction: Hook readers and establish the topic's relevance.
                  - Body: Develop 3 main points with supporting evidences and examples
                  - Conclusion: Summarize key takeaways and include a call-to-action

            3. Content Requirements:
                  - Include real-world applications or case studies
                  - Incorporate relevant statistics or data points when appropriate
                  - Explain benefits/implications clearly for non-experts

            4. Tone & Style:
                  - Write in an informative yet conversational voice
                  - Use accessible language while maintaining authority
                  - Break up text with subheadings and short paragraphs

            5. Response Format: Deliver complete, ready-to-publish posts with suggested title.
            """;


    private final ChatClient chatClient;

    public ArticleController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public Flux<String> newPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") String topic) {
        return chatClient.prompt()
                .user(u -> {
                    u.text("Write me a blog post about {topic}");
                    u.param("topic", topic);
                })
                .system(systemInstructions)
                .stream()
                .content();

    }



}
