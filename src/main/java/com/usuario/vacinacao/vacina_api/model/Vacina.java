package com.usuario.vacinacao.vacina_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_vacinas")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeVacina;

    @Column(nullable = false)
    private LocalDate dataAplicacao;

    @Column(nullable = false)
    private Integer dose;

    @Column(length = 50)
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Vacina() {
    }

    public Vacina(Long id, Usuario usuario, String lote, Integer dose, LocalDate dataAplicacao, String nomeVacina) {
        this.id = id;
        this.usuario = usuario;
        this.lote = lote;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.nomeVacina = nomeVacina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vacina vacina = (Vacina) o;
        return Objects.equals(id, vacina.id) && Objects.equals(nomeVacina, vacina.nomeVacina) && Objects.equals(dataAplicacao, vacina.dataAplicacao) && Objects.equals(dose, vacina.dose) && Objects.equals(lote, vacina.lote) && Objects.equals(usuario, vacina.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeVacina, dataAplicacao, dose, lote, usuario);
    }
}