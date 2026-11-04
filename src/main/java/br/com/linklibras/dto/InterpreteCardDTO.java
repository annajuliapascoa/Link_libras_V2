package br.com.linklibras.dto;

import java.math.BigDecimal;
import java.util.List;

public class InterpreteCardDTO {

    private Integer id;
    private String nome;
    private String cidade;
    private String estado;
    private String descricao;
    private BigDecimal valorHora;
    private String foto;
    private List<String> especialidades;

    public InterpreteCardDTO(
            Integer id,
            String nome,
            String cidade,
            String estado,
            String descricao,
            BigDecimal valorHora,
            String foto,
            List<String> especialidades) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.descricao = descricao;
        this.valorHora = valorHora;
        this.foto = foto;
        this.especialidades = especialidades;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public String getFoto() {
        return foto;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }
}