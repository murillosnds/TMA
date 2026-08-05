package github.murillosnds.tma.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import github.murillosnds.tma.dto.CreateTaskRequestDTO;
import github.murillosnds.tma.dto.TaskResponseDTO;
import github.murillosnds.tma.entity.Task;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.repository.TaskRepository;
import github.murillosnds.tma.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> listAll() {
        return taskRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();  

    }

    public Optional<TaskResponseDTO> findTaskById(java.util.UUID id) {
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

    public void deleteTaskById(java.util.UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found!");
        }
        taskRepository.deleteById(id);
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