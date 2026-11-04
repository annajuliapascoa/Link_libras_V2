package br.com.linklibras.controller;

import br.com.linklibras.dto.InterpreteCardDTO;
import br.com.linklibras.service.InterpreteService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/interpretes")
public class InterpreteController {

    private final InterpreteService interpreteService;

    public InterpreteController(InterpreteService interpreteService) {
        this.interpreteService = interpreteService;
    }

    @GetMapping("/destaques")
    public List<InterpreteCardDTO> buscarDestaques() {
        return interpreteService.buscarPrimeiros9();
    }

    // Busca com filtros opcionais.
    // Ex.: /api/interpretes?cidade=Cotia&nome=Fernanda&especialidades=Presencial,Inglês&precoMax=150
    @GetMapping
    public List<InterpreteCardDTO> buscarComFiltros(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) List<String> especialidades,
            @RequestParam(required = false) BigDecimal precoMax) {

        return interpreteService.buscarComFiltros(cidade, nome, especialidades, precoMax);
    }
}