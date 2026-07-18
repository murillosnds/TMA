package github.murillosnds.tma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskRequestDTO(

    @NotBlank
    String title,

    @NotBlank
    String description,

    @NotNull
    Long userId
) {}