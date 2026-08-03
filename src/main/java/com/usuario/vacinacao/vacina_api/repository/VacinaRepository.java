package com.usuario.vacinacao.vacina_api.repository;

import com.usuario.vacinacao.vacina_api.model.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    //query method spring data jpa para buscar todas as vaians de um usuario pelo id
    List<Vacina> findByUsuarioId(Long usuarioId);
}
