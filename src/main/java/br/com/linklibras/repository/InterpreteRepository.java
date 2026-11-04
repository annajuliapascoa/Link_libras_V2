package br.com.linklibras.repository;

import br.com.linklibras.model.Interprete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InterpreteRepository
        extends JpaRepository<Interprete, Integer> {

    Optional<Interprete> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Interprete> findTop9ByOrderByIdInterpreteAsc();

    // Filtra por cidade (LIKE case-insensitive), nome (LIKE case-insensitive) e preço máximo.
    // O CAST resolve o erro "function lower(bytea)" do PostgreSQL quando o parâmetro é null.
    @Query("""
        SELECT i FROM Interprete i
        WHERE (CAST(:cidade AS string) IS NULL OR LOWER(i.cidade) LIKE LOWER(CONCAT('%', CAST(:cidade AS string), '%')))
          AND (CAST(:nome AS string) IS NULL OR LOWER(i.nome) LIKE LOWER(CONCAT('%', CAST(:nome AS string), '%')))
          AND (:precoMax IS NULL OR i.valorHora <= :precoMax)
        ORDER BY i.idInterprete
        """)
    List<Interprete> buscarPorCidadeNomePreco(
            @Param("cidade") String cidade,
            @Param("nome") String nome,
            @Param("precoMax") BigDecimal precoMax
    );
}