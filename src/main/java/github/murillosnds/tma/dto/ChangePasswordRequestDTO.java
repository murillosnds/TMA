package github.murillosnds.tma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDTO(
    @NotBlank String currentPassword,
    @NotBlank @Size(min = 12) String newPassword
) {}
