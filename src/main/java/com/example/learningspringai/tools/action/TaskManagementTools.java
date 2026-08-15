package com.example.learningspringai.tools.action;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TaskManagementTools {

    public record TaskResult(
            Long taskId,
            String title,
            String status,
            String assignee,
            String message
    ) {}

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED,
    }

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong taskIdGenerator = new AtomicLong(1);

    private record Task(
            Long id,
            String title,
            String description,
            String assignee,
            TaskStatus status
    ) {}


    @Tool(description = "Create a new task with title, description, and assignee")
    public TaskResult createTask(
            @ToolParam(description = "The title of the task") String title,
            @ToolParam(description = "A description of what the task involves") String description,
            @ToolParam(description = "The person the task should be assigned to") String assignee) {
        Long taskId = taskIdGenerator.getAndIncrement();
        Task task = new Task(taskId, title, description, assignee, TaskStatus.PENDING);
        tasks.put(taskId, task);
        return new TaskResult(taskId, title, "PENDING", assignee, "Task created successfullyb and assigned to " + assignee);
    }

    public List<TaskResult> getAllTasks() {
        System.out.println(tasks);
        return tasks.values().stream()
                .map(t -> new TaskResult(t.id(), t.title(), t.status().name(), t.assignee(), null))
                .toList();
    }

}
