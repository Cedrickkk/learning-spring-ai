package com.example.learningspringai.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actors")
public class ActorFilmController {

    private final ChatClient chatClient;

    public ActorFilmController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/films")
    public String unstructured() {
        return chatClient.prompt()
                .user("Give me the top 5 films of Matt Damon.")
                .call()
                .content();
    }


    @GetMapping("/films/structured")
    public ActorFilms structured(@RequestParam(value = "actor", defaultValue = "Matt Damon") String actor) {
        return chatClient.prompt()
                .user(spec -> {
                    spec.text("Give the top 5 films of {actor}");
                    spec.param("actor", actor);
                })
                .call()
                .entity(ActorFilms.class);
    }

}
