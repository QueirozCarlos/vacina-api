package com.usuario.vacinacao.vacina_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record VacinaRequestDTO(

        @NotBlank(message = "O Nome da vacina é obrigatório")
        String nomeVacina,

        @NotNull(message = "A data de aplicação é obrigatório")
        @PastOrPresent(message = "A data de aplicação não pode ser uma datra futura")
        LocalDate dataAplicacao,

        @NotNull(message = "O número da dose é obrigatório")
        @Min(value = 1, message = "O número da dose deve ser maior ou igual a 1")
        Integer dose,

        String lote
) {}
