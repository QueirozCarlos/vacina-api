package com.usuario.vacinacao.vacina_api.dto;



import java.time.LocalDate;

public record VacinaResponseDTO(
        Long id,
        String nomeVacina,
        LocalDate dataAplicacao,
        Integer dose,
        String lote,
        Long usuarioID
) {}
