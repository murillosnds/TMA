package github.murillosnds.tma.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import github.murillosnds.tma.dto.CreateTaskRequestDTO;
import github.murillosnds.tma.dto.TaskResponseDTO;
import github.murillosnds.tma.entity.Task;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.repository.TaskRepository;
import github.murillosnds.tma.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private CreateTaskRequestDTO request;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("joao@email.com");

        request = new CreateTaskRequestDTO("Título", "Descrição", 1L);
    }

    @Test
    void createTask_shouldCreateAndReturnTask() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTitle("Título");
        savedTask.setDescription("Descrição");
        savedTask.setCompleted(false);
        savedTask.setCreatedAt(LocalDate.now());
        savedTask.setUser(user);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        
        TaskResponseDTO result = taskService.createTask(request);

        assertNotNull(result);
        assertEquals("Título", result.title());
        assertEquals(1L, result.userId());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_shouldThrowException_whenUserNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        
        assertThrows(RuntimeException.class, () -> taskService.createTask(request));
    }
}