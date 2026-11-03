package br.com.linklibras.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "interprete")
public class Interprete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_interprete")
    private Integer idInterprete;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha_hash", columnDefinition = "TEXT")
    private String senhaHash;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Pattern(regexp = "M|F|O")
    @Column(name = "sexo", length = 1)
    private String sexo;

    public Interprete() {

    }

    // Gatters e Setters

    public Integer getIdInterprete() {
        return idInterprete;
    }

    public void setIdInterprete(Integer idInterprete) {
        this.idInterprete = idInterprete;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
