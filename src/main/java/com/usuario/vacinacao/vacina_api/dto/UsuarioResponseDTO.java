package com.usuario.vacinacao.vacina_api.dto;

import java.time.LocalDate;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        String cpf,
        LocalDate dataNascimento
) {}
