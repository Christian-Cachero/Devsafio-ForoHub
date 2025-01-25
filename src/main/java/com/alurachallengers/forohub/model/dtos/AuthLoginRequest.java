package com.alurachallengers.forohub.model.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String email,
                               @NotBlank String clave) {
}
