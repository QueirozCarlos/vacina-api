package com.usuario.vacinacao.vacina_api.repository;

import com.usuario.vacinacao.vacina_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método derivados > query Methods) para validação de unicidade na service
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByCpf(String cpf);
}
