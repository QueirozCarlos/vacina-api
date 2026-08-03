package com.usuario.vacinacao.vacina_api.dto;

import java.time.LocalDate;
import java.util.List;

public record UsuarioDetalhesResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        LocalDate dataNascimento,
        List<VacinaResponseDTO> vacinas
) {}
