package github.murillosnds.tma.dto;

public record UpdateUserRequestDTO(
    String name,
    String email,
    String password
) {}