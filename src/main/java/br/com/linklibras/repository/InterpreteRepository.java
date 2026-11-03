package br.com.linklibras.repository;

import br.com.linklibras.model.Interprete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterpreteRepository extends JpaRepository<Interprete, Integer> {

    Optional<Interprete> findByEmail(String email);

    boolean existsByEmail(String email);

}
