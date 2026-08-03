package com.usuario.vacinacao.vacina_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura erros de regra de negocios ex: cpf/email duplicados ou recursos não encontrados
     * retorna HTTP 400 Bad request ou 422 Unprocessable entiry
     */

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroRespostaDTO> tratarRegrasDeNegocios(RegraDeNegocioException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Violação de regra de Negócio",
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    /**
     * captura falhas de validação nos DTOS anotados com @valid ex: @notBlank, @cpf, @email
     * retorna http 400 bad request com a lista detalhada de cada campo invalido
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacaoCampos(MethodArgumentNotValidException ex) {

        // mapeia os erros de campo spring validation para a estrutura dto
        List<ErroRespostaDTO.CampoInvalido> errosCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErroRespostaDTO.CampoInvalido(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();
        ErroRespostaDTO erro = new ErroRespostaDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Falha na validação dos dados",
                "Um ou mais campos estão inválidos. Faca o preenchimento correto e tente novamente.",
                LocalDateTime.now(),
                errosCampos
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     *  Fallback de segurança:: captura qualquer outra exceção não mapeada (bugs NullPointer, etc)
     *  Evita vazar s stack trace no json e retorna HTTP 500 Internet Server error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroRespostaDTO> tratarErrorInesperado(Exception ex) {

        // registrar logs
        ErroRespostaDTO erro = new ErroRespostaDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno no Servidor",
                "Ocorreu um erro inesperado no sistema. Entre em contato com o suporte.",
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}

