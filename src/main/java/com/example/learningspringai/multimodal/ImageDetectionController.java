package com.example.learningspringai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/images")
public class ImageDetectionController {

    private final ChatClient chatClient;

    @Value("classpath:/images/clayton-cardinalli-LpCnRYK6U_k-unsplash.jpg")
    private Resource resource;

    public ImageDetectionController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @GetMapping("/image-to-text")
    public Flux<String> imageToText() {
        return chatClient.prompt()
                .user(spec -> {
                    spec.text("Can you describe what you see in the following image?");
                    spec.media(MimeTypeUtils.IMAGE_JPEG, resource);
                })
                .stream()
                .content();
    }


}
