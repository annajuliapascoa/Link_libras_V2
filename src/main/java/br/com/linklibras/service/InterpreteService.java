package br.com.linklibras.service;

import br.com.linklibras.dto.InterpreteCardDTO;
import br.com.linklibras.model.Especialidade;
import br.com.linklibras.model.Interprete;
import br.com.linklibras.repository.InterpreteRepository;
import org.springframework.stereotype.Service;
import br.com.linklibras.service.SupabaseStorageService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterpreteService {

    private final InterpreteRepository interpreteRepository;
    private final SupabaseStorageService supabaseStorageService;

    public InterpreteService(InterpreteRepository interpreteRepository,
                             SupabaseStorageService supabaseStorageService) {
        this.interpreteRepository = interpreteRepository;
        this.supabaseStorageService = supabaseStorageService;
    }

    // ===== MANTENHA AQUI OS OUTROS MÉTODOS QUE JÁ EXISTIAM =====
    // (cadastrar, login, buscarPorEmail, etc.)
    // Não os remova. Só adicione os métodos abaixo.

    public List<InterpreteCardDTO> buscarPrimeiros9() {
        return interpreteRepository.findTop9ByOrderByIdInterpreteAsc()
                .stream()
                .map(this::toCardDTO)
                .toList();
    }

    // Busca com filtros opcionais: cidade, nome, especialidades e preço máximo
    public List<InterpreteCardDTO> buscarComFiltros(
            String cidade,
            String nome,
            List<String> especialidades,
            BigDecimal precoMax) {

        String cidadeFiltro = (cidade == null || cidade.isBlank())
                ? null
                : cidade.trim();

        String nomeFiltro = (nome == null || nome.isBlank())
                ? null
                : nome.trim();

        List<String> esps = (especialidades == null)
                ? List.of()
                : especialidades;

        List<Interprete> encontrados =
                interpreteRepository.buscarPorCidadeNomePreco(cidadeFiltro, nomeFiltro, precoMax);

        // Interseção: precisa ter TODAS as especialidades pedidas
        if (!esps.isEmpty()) {
            Set<String> pedidas = esps.stream()
                    .map(String::trim)
                    .collect(Collectors.toSet());

            encontrados = encontrados.stream()
                    .filter(i -> {
                        Set<String> tem = i.getEspecialidades().stream()
                                .map(Especialidade::getNome)
                                .collect(Collectors.toSet());
                        return tem.containsAll(pedidas);
                    })
                    .toList();
        }

        return encontrados.stream()
                .map(this::toCardDTO)
                .toList();
    }

    // NOVO MÉTODO: buscar por ID para a página de perfil
    public InterpreteCardDTO buscarPorId(Integer id) {
        Interprete i = interpreteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intérprete não encontrado"));
        return toCardDTO(i);
    }

    private InterpreteCardDTO toCardDTO(Interprete i) {
        List<String> especialidades = i.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .sorted()
                .toList();

        return new InterpreteCardDTO(
                i.getIdInterprete(),
                i.getNome(),
                i.getCidade(),
                i.getEstado(),
                i.getDescricao(),
                i.getValorHora(),
                supabaseStorageService.gerarUrlAssinada(i.getFoto()),
                especialidades,
                i.getTelefone() // <-- Passando o telefone
        );
    }
}