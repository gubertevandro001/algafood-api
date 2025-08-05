package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        final var cidade = cidadeRepository.buscar(id);

        if (cidade != null) {
            return ResponseEntity.ok().body(cidade);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cadastroCidadeService.adicionar(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        try {
            var cidadeAtual = cidadeRepository.buscar(id);

            if (cidadeAtual != null) {
                BeanUtils.copyProperties(cidade, cidadeAtual, "id");

                cidadeAtual = cadastroCidadeService.adicionar(cidadeAtual);
                return ResponseEntity.ok(cidadeAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            cadastroCidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
