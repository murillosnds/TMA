package github.murillosnds.tma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.murillosnds.tma.dto.CreateUserRequestDTO;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  
    private UserService userService;

    @MockitoBean
    private github.murillosnds.tma.service.JwtService jwtService;

    @MockitoBean
    private github.murillosnds.tma.service.CustomUserDetailsService customUserDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser_shouldReturn201Created() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO(
                "João", "joao@email.com", "123456789012"
        );

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("João");
        savedUser.setEmail("joao@email.com");

        when(userService.create(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void createUser_shouldReturn400_whenPasswordTooShort() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO(
                "João", "joao@email.com", "123"
        );

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}