package github.murillosnds.tma.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(  
    
    @NotBlank
    String name, 
    
    @Email
    @NotBlank
    String email, 
    
    @NotBlank
    @Size(min = 12, max = 128)
    String password

) {}