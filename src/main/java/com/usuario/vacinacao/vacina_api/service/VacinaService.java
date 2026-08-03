package com.usuario.vacinacao.vacina_api.service;

import com.usuario.vacinacao.vacina_api.dto.VacinaRequestDTO;
import com.usuario.vacinacao.vacina_api.dto.VacinaResponseDTO;
import com.usuario.vacinacao.vacina_api.exception.RegraDeNegocioException;
import com.usuario.vacinacao.vacina_api.model.Usuario;
import com.usuario.vacinacao.vacina_api.model.Vacina;
import com.usuario.vacinacao.vacina_api.repository.UsuarioRepository;
import com.usuario.vacinacao.vacina_api.repository.VacinaRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VacinaService {

    private final VacinaRepository vacinaRepository;
    private final UsuarioRepository usuarioRepository;

    //injeçao de dependencias via construtor
    public VacinaService(VacinaRepository vacinaRepository, UsuarioRepository usuarioRepository){
        this.vacinaRepository = vacinaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public VacinaResponseDTO registrarVacina(Long usuarioId, VacinaRequestDTO request) {
        // garante que o user exista no banco
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado com o ID: " + usuarioId));

        //  mapeia o DTo para entidade e vincula o usuario
        Vacina vacina = toEntity(request);
        vacina.setUsuario(usuario);

        // salva a nova vacina no banco
        Vacina vacinaSalva = vacinaRepository.save(vacina);

        //retorna dto resposta
        return toDTO(vacinaSalva);
    }

    @Transactional(readOnly = true)
    public List<VacinaResponseDTO> listarPorUsuario(Long usuarioId) {
        //garante q o user exista antes de buscar suas vacinas
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RegraDeNegocioException("Usuário não encontrado com o ID: " + usuarioId);
        }
        return vacinaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private Vacina toEntity(VacinaRequestDTO dto) {
        Vacina vacina = new Vacina();
        vacina.setNomeVacina(dto.nomeVacina());
        vacina.setDataAplicacao(dto.dataAplicacao());
        vacina.setDose(dto.dose());
        vacina.setLote(dto.lote());
        return vacina;
    }

    private VacinaResponseDTO toDTO(Vacina vacina) {
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
