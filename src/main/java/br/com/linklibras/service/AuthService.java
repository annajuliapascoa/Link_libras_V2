package br.com.linklibras.service;

import br.com.linklibras.dto.CadastroInterpreteDTO;
import br.com.linklibras.dto.CadastroUsuarioDTO;
import br.com.linklibras.dto.LoginDTO;
import br.com.linklibras.exception.CpfJaCadastradoException;
import br.com.linklibras.model.Especialidade;
import br.com.linklibras.model.Interprete;
import br.com.linklibras.model.Usuario;
import br.com.linklibras.repository.InterpreteRepository;
import br.com.linklibras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.linklibras.exception.EmailJaCadastradoException;
import br.com.linklibras.exception.CredenciaisInvalidasException;
import br.com.linklibras.dto.LoginRespostaDTO;
import br.com.linklibras.repository.EspecialidadeRepository;
import br.com.linklibras.service.SupabaseStorageService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final InterpreteRepository interpreteRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EspecialidadeRepository especialidadeRepository;
    private final SupabaseStorageService supabaseStorageService;

    @Autowired
    public AuthService(
            UsuarioRepository usuarioRepository,
            InterpreteRepository interpreteRepository,
            EspecialidadeRepository especialidadeRepository,
            JwtService jwtService,
            SupabaseStorageService supabaseStorageService) {

        this.usuarioRepository = usuarioRepository;
        this.interpreteRepository = interpreteRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.jwtService = jwtService;
        this.supabaseStorageService = supabaseStorageService;

        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public Usuario cadastrarUsuario(CadastroUsuarioDTO dados) {

        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já está cadastrado como usuário.");
        }

        if (usuarioRepository.existsByCpf(dados.getCpf())) {
            throw new CpfJaCadastradoException("Este CPF já está cadastrado.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setDataNascimento(dados.getDataNascimento());
        usuario.setEmail(dados.getEmail());
        usuario.setCpf(dados.getCpf());
        usuario.setCep(dados.getCep());
        usuario.setComplemento(dados.getComplemento());

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
        interprete.setCep(dados.getCep());
        interprete.setEstado(dados.getEstado());
        interprete.setCidade(dados.getCidade());
        interprete.setBairro(dados.getBairro());
        interprete.setComplemento(dados.getComplemento());
        interprete.setDescricao(dados.getDescricao());
        interprete.setValorHora(dados.getValorHora());
        interprete.setSexo(dados.getSexo());


        if (dados.getEspecialidades() != null && !dados.getEspecialidades().isEmpty()) {

            List<Especialidade> especialidadesSelecionadas =
                    especialidadeRepository.findAllById(dados.getEspecialidades());

            if (especialidadesSelecionadas.size() != dados.getEspecialidades().stream().distinct().count()) {
                throw new RuntimeException("Uma ou mais especialidades informadas não existem.");
            }

            interprete.setEspecialidades(new HashSet<>(especialidadesSelecionadas));
        }


        if (dados.getFoto() != null && !dados.getFoto().isEmpty()) {

            try {
                String caminhoFoto = supabaseStorageService.uploadFoto(dados.getFoto());

                interprete.setFoto(caminhoFoto);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao enviar a foto do intérprete.", e);
            }
        }

        return interpreteRepository.save(interprete);
    }


    public LoginRespostaDTO loginUsuario(LoginDTO dados) {

        Usuario usuario = usuarioRepository.findByEmail(dados.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos."));

        System.out.println("Usuário encontrado: " + usuario.getEmail());
        System.out.println("Senha corresponde: " +
                passwordEncoder.matches(dados.getSenha(), usuario.getSenhahash()));

        if (!passwordEncoder.matches(
                dados.getSenha(), usuario.getSenhahash())) {

            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(
                usuario.getIdUsuario(), "USUARIO");

        return new LoginRespostaDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                "USUARIO",
                token
        );

    }


    public LoginRespostaDTO loginInterprete(LoginDTO dados) {

        Interprete interprete = interpreteRepository.findByEmail(dados.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos."));

        if (!passwordEncoder.matches(
                dados.getSenha(), interprete.getSenhaHash())) {

            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(
                interprete.getIdInterprete(), "INTERPRETE");

        return new LoginRespostaDTO(
                interprete.getIdInterprete(),
                interprete.getNome(),
                interprete.getEmail(),
                "INTERPRETE",
                token
        );
    }
}
