package github.murillosnds.tma.dto;

public record UpdateTaskRequestDTO(
    String title,
    String description,
    Boolean completed
) {}