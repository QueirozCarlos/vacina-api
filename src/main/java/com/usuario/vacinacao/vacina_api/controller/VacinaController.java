package com.usuario.vacinacao.vacina_api.controller;

import com.usuario.vacinacao.vacina_api.dto.VacinaRequestDTO;
import com.usuario.vacinacao.vacina_api.dto.VacinaResponseDTO;
import com.usuario.vacinacao.vacina_api.service.VacinaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/usuarios/{usuarioId}/vacinas")
public class VacinaController {

    private final VacinaService vacinaService;

    public VacinaController(VacinaService vacinaService) {
        this.vacinaService = vacinaService;
    }

    @PostMapping
    public ResponseEntity<VacinaResponseDTO> registrarVacina(@PathVariable Long usuarioId, @RequestBody @Valid VacinaRequestDTO request) {
        VacinaResponseDTO vacinaCriada = vacinaService.registrarVacina(usuarioId, request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vacinaCriada.id())
                .toUri();
        return ResponseEntity.created(uri).body(vacinaCriada);
    }

    @GetMapping
    public ResponseEntity<List<VacinaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<VacinaResponseDTO> vacinas = vacinaService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(vacinas);
    }

}
