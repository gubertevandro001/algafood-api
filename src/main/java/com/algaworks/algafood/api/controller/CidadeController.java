package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {
        return cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidade) {
        try {
            return cidadeModelAssembler.toModel(
                    cadastroCidadeService.adicionar(cidadeInputDisassembler.toDomainObject(cidade)));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}")
    public CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidade) {
        try {
            var cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);

            cidadeInputDisassembler.copyToDomainObject(cidade, cidadeAtual);

            return cidadeModelAssembler.toModel(cadastroCidadeService.adicionar(cidadeAtual));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }
}
