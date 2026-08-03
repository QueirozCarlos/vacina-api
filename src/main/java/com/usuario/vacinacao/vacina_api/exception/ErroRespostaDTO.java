package com.usuario.vacinacao.vacina_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroRespostaDTO(

        int status,
        String titulo,
        String mensagem,
        LocalDateTime timestamp,
        List<CampoInvalido> campoInvalidos
) {
    public record CampoInvalido(
            String campo,
            String mensagem
    ) {}
}
