package github.murillosnds.tma.service;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import github.murillosnds.tma.dto.CreateTaskRequestDTO;
import github.murillosnds.tma.dto.TaskResponseDTO;
import github.murillosnds.tma.dto.UpdateTaskRequestDTO;
import github.murillosnds.tma.entity.Task;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.repository.TaskRepository;
import github.murillosnds.tma.repository.UserRepository;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Page<TaskResponseDTO> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
            .map(this::toDTO);
    }

    public Optional<TaskResponseDTO> findTaskById(UUID id) {
        return taskRepository.findById(id)
            .map(this::toDTO); 
    }

    public TaskResponseDTO createTask(CreateTaskRequestDTO request) {

    User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new RuntimeException("User not found!"));

    Task task = new Task();
    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setCompleted(false);
    task.setCreatedAt(LocalDate.now());
    task.setUser(user);

    Task savedTask = taskRepository.save(task);

    return toDTO(savedTask);
    }

    public void deleteTaskById(UUID id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found!"));

        taskRepository.delete(task);
    }

    public TaskResponseDTO updateTask(UUID id, UpdateTaskRequestDTO request) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found!"));

        if (request.title() != null) {
            task.setTitle(request.title());
        }
        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.completed() != null) {
            task.setCompleted(request.completed());
        }

        Task updatedTask = taskRepository.save(task);
        return toDTO(updatedTask);
    }

    private TaskResponseDTO toDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUser().getId()
        );
    }
}