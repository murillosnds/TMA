package github.murillosnds.tma.controller;

import java.net.URI;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;   
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import github.murillosnds.tma.dto.CreateUserRequestDTO;
import github.murillosnds.tma.dto.UpdateUserRequestDTO;
import github.murillosnds.tma.dto.UserResponseDTO;
import github.murillosnds.tma.entity.User;
import github.murillosnds.tma.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found",
                 content = @Content(schema = @Schema(implementation = User.class))),
    @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content),
    @ApiResponse(responseCode = "401", description = "Unauthorized – missing or invalid JWT token",
                 content = @Content),
    @ApiResponse(responseCode = "403", description = "Forbidden – token expired or insufficient permissions",
                 content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<UserResponseDTO> userDTO = userService.findUserById(id);
        
       if (userDTO.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ID resource with ID " + id + " not found.");
       }        

       return ResponseEntity.ok(userDTO.get());
    }

    @PostMapping
     @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input (e.g., invalid email, short password)",
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)",
                     content = @Content)
    })
    public ResponseEntity<User> createUser (@Valid @RequestBody CreateUserRequestDTO request) {

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());

        User savedUser = userService.create(user);

        return ResponseEntity
        .created(URI.create("/users/" + savedUser.getId()))
        .body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
       boolean deleted = userService.deleteById(id);
       
       if (!deleted) {
           return ResponseEntity.notFound().build();
       }

       return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO request) {
        User updatedUser = userService.patchUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }
}