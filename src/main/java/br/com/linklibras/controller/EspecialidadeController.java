package br.com.linklibras.controller;

import br.com.linklibras.model.Especialidade;
import br.com.linklibras.service.EspecialidadeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(
            EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @GetMapping("/categoria/{categoria}")
    public List<Especialidade> listarPorCategoria(
            @PathVariable String categoria) {

        return especialidadeService.listarPorCategoria(categoria);
    }
}
