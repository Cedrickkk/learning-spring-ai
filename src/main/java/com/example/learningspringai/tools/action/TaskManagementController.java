package com.example.learningspringai.tools.action;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskManagementController {

    private final ChatClient chatClient;
    private final TaskManagementTools taskManagementTools;

    public TaskManagementController(ChatClient.Builder builder, TaskManagementTools taskManagementTools) {
        this.chatClient = builder.build();
        this.taskManagementTools = taskManagementTools;
    }

    @GetMapping("/tools")
    public String createTask(@RequestParam String message) {
        return chatClient.prompt()
                .tools(taskManagementTools)
                .user(message)
                .call()
                .content();
    }

}
