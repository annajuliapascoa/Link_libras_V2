package br.com.linklibras.controller;

import br.com.linklibras.dto.CadastroInterpreteDTO;
import br.com.linklibras.dto.CadastroUsuarioDTO;
import br.com.linklibras.dto.InterpreteRespostaDTO;
import br.com.linklibras.dto.UsuarioRespostaDTO;
import br.com.linklibras.model.Interprete;
import br.com.linklibras.model.Usuario;
import br.com.linklibras.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.linklibras.dto.LoginDTO;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/usuarios/cadastro")
    public ResponseEntity<UsuarioRespostaDTO> cadastrarUsuario(
            @Valid @RequestBody CadastroUsuarioDTO dados) {

        Usuario usuario = authService.cadastrarUsuario(dados);

        UsuarioRespostaDTO resposta = new UsuarioRespostaDTO();

        resposta.setIdUsuario(usuario.getIdUsuario());
        resposta.setNome(usuario.getNome());
        resposta.setEmail(usuario.getEmail());
        resposta.setTelefone(usuario.getTelefone());
        resposta.setEstado(usuario.getEstado());
        resposta.setCidade(usuario.getCidade());
        resposta.setBairro(usuario.getBairro());
        resposta.seteSurdo(usuario.geteSurdo());
        resposta.setSexo(usuario.getSexo());

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }


    @PostMapping("/interpretes/cadastro")
    public ResponseEntity<InterpreteRespostaDTO> cadastrarInterprete(
            @Valid @RequestBody CadastroInterpreteDTO dados) {

        Interprete interprete = authService.cadastrarInterprete(dados);

        InterpreteRespostaDTO resposta = new InterpreteRespostaDTO();

        resposta.setIdInterprete(interprete.getIdInterprete());
        resposta.setNome(interprete.getNome());
        resposta.setEmail(interprete.getEmail());
        resposta.setTelefone(interprete.getTelefone());
        resposta.setEstado(interprete.getEstado());
        resposta.setCidade(interprete.getCidade());
        resposta.setBairro(interprete.getBairro());
        resposta.setDescricao(interprete.getDescricao());
        resposta.setValorHora(interprete.getValorHora());
        resposta.setSexo(interprete.getSexo());

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }


    @PostMapping("/usuarios/login")
    public ResponseEntity<UsuarioRespostaDTO> loginUsuario(
            @Valid @RequestBody LoginDTO dados) {

        Usuario usuario = authService.loginUsuario(dados);

        UsuarioRespostaDTO resposta = new UsuarioRespostaDTO();

        resposta.setIdUsuario(usuario.getIdUsuario());
        resposta.setNome(usuario.getNome());
        resposta.setEmail(usuario.getEmail());
        resposta.setTelefone(usuario.getTelefone());
        resposta.setEstado(usuario.getEstado());
        resposta.setCidade(usuario.getCidade());
        resposta.setBairro(usuario.getBairro());
        resposta.seteSurdo(usuario.geteSurdo());
        resposta.setSexo(usuario.getSexo());

        return ResponseEntity.ok(resposta);
    }


    @PostMapping("/interpretes/login")
    public ResponseEntity<InterpreteRespostaDTO> loginInterprete(
            @Valid @RequestBody LoginDTO dados) {

        Interprete interprete = authService.loginInterprete(dados);

        InterpreteRespostaDTO resposta = new InterpreteRespostaDTO();

        resposta.setIdInterprete(interprete.getIdInterprete());
        resposta.setNome(interprete.getNome());
        resposta.setEmail(interprete.getEmail());
        resposta.setTelefone(interprete.getTelefone());
        resposta.setEstado(interprete.getEstado());
        resposta.setCidade(interprete.getCidade());
        resposta.setBairro(interprete.getBairro());
        resposta.setDescricao(interprete.getDescricao());
        resposta.setValorHora(interprete.getValorHora());
        resposta.setSexo(interprete.getSexo());

        return ResponseEntity.ok(resposta);
    }


}
