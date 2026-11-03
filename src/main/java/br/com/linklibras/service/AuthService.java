package br.com.linklibras.service;

import br.com.linklibras.dto.CadastroInterpreteDTO;
import br.com.linklibras.dto.CadastroUsuarioDTO;
import br.com.linklibras.dto.LoginDTO;
import br.com.linklibras.model.Interprete;
import br.com.linklibras.model.Usuario;
import br.com.linklibras.repository.InterpreteRepository;
import br.com.linklibras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.linklibras.exception.EmailJaCadastradoException;
import br.com.linklibras.exception.CredenciaisInvalidasException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final InterpreteRepository interpreteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(
            UsuarioRepository usuarioRepository,
            InterpreteRepository interpreteRepository) {

        this.usuarioRepository = usuarioRepository;
        this.interpreteRepository = interpreteRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public Usuario cadastrarUsuario(CadastroUsuarioDTO dados) {

        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já está cadastrado como usuário.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setDataNascimento(dados.getDataNascimento());
        usuario.setEmail(dados.getEmail());

        String senhaCriptografada = passwordEncoder.encode(dados.getSenha());
        usuario.setSenhahash(senhaCriptografada);

        usuario.setTelefone(dados.getTelefone());
        usuario.setEstado(dados.getEstado());
        usuario.setCidade(dados.getCidade());
        usuario.setBairro(dados.getBairro());
        usuario.seteSurdo(dados.geteSurdo());
        usuario.setSexo(dados.getSexo());

        return usuarioRepository.save(usuario);
    }


    public Interprete cadastrarInterprete(CadastroInterpreteDTO dados) {

        if (interpreteRepository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já está cadastrado como intérprete.");
        }

        Interprete interprete = new Interprete();

        interprete.setNome(dados.getNome());
        interprete.setDataNascimento(dados.getDataNascimento());
        interprete.setEmail(dados.getEmail());

        String senhaCriptografada = passwordEncoder.encode(dados.getSenha());
        interprete.setSenhaHash(senhaCriptografada);

        interprete.setTelefone(dados.getTelefone());
        interprete.setEstado(dados.getEstado());
        interprete.setCidade(dados.getCidade());
        interprete.setBairro(dados.getBairro());
        interprete.setDescricao(dados.getDescricao());
        interprete.setValorHora(dados.getValorHora());
        interprete.setSexo(dados.getSexo());

        return interpreteRepository.save(interprete);
    }


    public Usuario loginUsuario(LoginDTO dados) {

        Usuario usuario = usuarioRepository.findByEmail(dados.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos."));

        boolean senhaCorreta = passwordEncoder.matches(
                dados.getSenha(),
                usuario.getSenhahash()
        );

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }

        return usuario;
    }


    public Interprete loginInterprete(LoginDTO dados) {

        Interprete interprete = interpreteRepository.findByEmail(dados.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos."));

        boolean senhaCorreta = passwordEncoder.matches(
                dados.getSenha(),
                interprete.getSenhaHash()
        );

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }

        return interprete;
    }
}
