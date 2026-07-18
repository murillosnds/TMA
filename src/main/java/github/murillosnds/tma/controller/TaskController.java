package github.murillosnds.tma.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import github.murillosnds.tma.dto.CreateTaskRequestDTO;
import github.murillosnds.tma.dto.TaskResponseDTO;
import github.murillosnds.tma.service.TaskService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                 content = @Content(mediaType = "application/json",
                                   schema = @Schema(implementation = TaskResponseDTO.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized – missing or invalid JWT token",
                 content = @Content),
    @ApiResponse(responseCode = "403", description = "Forbidden – token expired or insufficient permissions",
                 content = @Content)
    })
    public ResponseEntity<List<TaskResponseDTO>> listTasks() {
        return ResponseEntity.ok(taskService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable UUID id) {

        return taskService.findTaskById(id)
        .<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Task not found!"));
    }

    @PostMapping
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Task created successfully",
                 content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
    @ApiResponse(responseCode = "400", description = "Invalid task data (validation error)",
                 content = @Content),
    @ApiResponse(responseCode = "401", description = "Unauthorized – missing or invalid JWT token",
                 content = @Content),
    @ApiResponse(responseCode = "403", description = "Forbidden – token expired or insufficient permissions",
                 content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found (invalid userId)",
                 content = @Content)
    })
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskRequestDTO request) {
        TaskResponseDTO task = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
}