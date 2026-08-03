package com.usuario.vacinacao.vacina_api.service;

import com.usuario.vacinacao.vacina_api.dto.UsuarioDetalhesResponseDTO;
import com.usuario.vacinacao.vacina_api.dto.UsuarioRequestDTO;
import com.usuario.vacinacao.vacina_api.dto.UsuarioResponseDTO;
import com.usuario.vacinacao.vacina_api.dto.VacinaResponseDTO;
import com.usuario.vacinacao.vacina_api.exception.RegraDeNegocioException;
import com.usuario.vacinacao.vacina_api.model.Usuario;
import com.usuario.vacinacao.vacina_api.model.Vacina;
import com.usuario.vacinacao.vacina_api.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository =usuarioRepository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO request) {
        // valida a regra de negocio
        validarUnicidade(request.email(), request.cpf());

        //mapeamento de DTO para entidade jpa
        Usuario usuario = toEntity(request);

        // persistência no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toDTO(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDetalhesResponseDTO buscarPorId(Long id) {
        Usuario usuario= usuarioRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Usuário não encontrado com o ID: " + id));
        return toDetalhesDTO(usuario);
    }

    // métodos Auxiliares e Mapeamentos :: Conversão DTO <-> Entidade


    private void validarUnicidade(String email, String cpf) {
        if(usuarioRepository.existsByEmail(email)) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com este e-mail.");
        }
        if (usuarioRepository.existsByCpf(cpf)) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com esse CPF.");
        }
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setCpf(dto.cpf());
        usuario.setDataNascimento(dto.dataNascimento());
        return usuario;
    }

    private UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getDataNascimento()
        );
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado com ID: " + id));

        // valida unicidade apenas se o email ou cpf foram alterados
        if (!usuario.getEmail().equalsIgnoreCase(request.email()) && usuarioRepository.existsByEmail(request.email())) {
            throw new RegraDeNegocioException("Já existe outro usuário cadastrado com este e-mail.");
        }
        if (!usuario.getCpf().equalsIgnoreCase(request.cpf()) && usuarioRepository.existsByCpf(request.cpf())) {
            throw new RegraDeNegocioException("Já existe outro usuário cadastrado com este CPF.");
        }
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setCpf(request.cpf());
        usuario.setDataNascimento(request.dataNascimento());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return toDTO(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RegraDeNegocioException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDetalhesResponseDTO toDetalhesDTO(Usuario usuario) {
        List<VacinaResponseDTO> vacinasDTO = usuario.getVacinas()
                .stream()
                .map(this::toVacinaDTO)
                .toList();
        return new UsuarioDetalhesResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                vacinasDTO
        );
    }

    private VacinaResponseDTO toVacinaDTO(Vacina vacina) {
        return new VacinaResponseDTO(
                vacina.getId(),
                vacina.getNomeVacina(),
                vacina.getDataAplicacao(),
                vacina.getDose(),
                vacina.getLote(),
                vacina.getUsuario().getId()
        );
    }
}
