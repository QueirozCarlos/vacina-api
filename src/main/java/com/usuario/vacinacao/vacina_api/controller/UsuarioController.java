package com.usuario.vacinacao.vacina_api.controller;

import com.usuario.vacinacao.vacina_api.dto.UsuarioDetalhesResponseDTO;
import com.usuario.vacinacao.vacina_api.dto.UsuarioRequestDTO;
import com.usuario.vacinacao.vacina_api.dto.UsuarioResponseDTO;
import com.usuario.vacinacao.vacina_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO request) {
        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrar(request);

        // Constroi o cabeçalho HTTP location -> http://localhost:8080/api/usuarios/{id}
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioCriado.id())
                .toUri();
        return ResponseEntity.created(uri).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalhesResponseDTO> buscarPorID(@PathVariable Long id) {
        UsuarioDetalhesResponseDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar (@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO request) {
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build(); //return HTTP 204 no Content
    }






}
