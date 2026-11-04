package br.com.linklibras.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CadastroUsuarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser anterior à data atual.")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    private String telefone;
    private String estado;
    private String cidade;
    private String bairro;

    @NotNull(message = "O sexo é obrigatório")
    @Pattern(regexp = "M|F|O", message = "Informe M, F ou O.")
    private String sexo;

    @JsonProperty("eSurdo")
    @NotNull(message = "Informe se o usuário é surdo.")
    private Boolean eSurdo;

    @NotBlank(message = "CPF é obrigatório.")
    @Pattern(
            regexp = "\\d{11}",
            message = "CPF deve conter 11 números."
    )
    private String cpf;

    @Pattern(
            regexp = "\\d{8}",
            message = "CEP deve conter 8 números."
    )
    private String cep;

    private String complemento;


    public CadastroUsuarioDTO() {

    }

    //Getters e Setters

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean geteSurdo() {
        return eSurdo;
    }

    public void seteSurdo(Boolean eSurdo) {
        this.eSurdo = eSurdo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
