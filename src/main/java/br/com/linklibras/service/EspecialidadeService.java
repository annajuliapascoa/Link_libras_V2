package br.com.linklibras.service;

import br.com.linklibras.model.Especialidade;
import br.com.linklibras.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<Especialidade> listarPorCategoria(String categoria) {

        return especialidadeRepository
                .findByCategoriaOrderByNomeAsc(categoria);
    }
}
