package br.com.linklibras.repository;

import br.com.linklibras.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {

    List<Especialidade> findByCategoriaOrderByNomeAsc(String categoria);
}
