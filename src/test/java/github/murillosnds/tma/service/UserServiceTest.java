package github.murillosnds.tma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("João");
        user.setEmail("joao@email.com");
        user.setPassword("rawPassword");
    }

    @Test
    void createUser_shouldEncodePasswordAndSave() {
        
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.create(user);

        verify(passwordEncoder).encode("rawPassword");
        verify(userRepository).save(user);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void findUserById_shouldReturnUser_whenExists() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("joao@email.com", result.get().getEmail());
    }

    @Test
    void findUserById_shouldReturnEmpty_whenNotFound() {

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(99L);

        assertTrue(result.isEmpty());
    }
}