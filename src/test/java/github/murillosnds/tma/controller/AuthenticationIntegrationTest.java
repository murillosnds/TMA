package github.murillosnds.tma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.murillosnds.tma.dto.CreateUserRequestDTO;
import github.murillosnds.tma.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_shouldReturnToken_whenCredentialsValid() throws Exception {

        CreateUserRequestDTO createRequest = new CreateUserRequestDTO(
                "João", "joao@email.com", "123456789012"
        );
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        
        LoginDTO loginRequest = new LoginDTO("joao@email.com", "123456789012");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_shouldReturn401_whenCredentialsInvalid() throws Exception {
        LoginDTO loginRequest = new LoginDTO("inexistente@email.com", "senhaerrada");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isForbidden());
    }
}