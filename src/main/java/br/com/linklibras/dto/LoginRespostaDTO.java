package br.com.linklibras.dto;

public class LoginRespostaDTO {

    private Integer id;
    private String nome;
    private String email;
    private String perfil;
    private String token;

    public LoginRespostaDTO() {
    }

    public LoginRespostaDTO(Integer id, String nome, String email, String perfil, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
