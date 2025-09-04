package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id) {
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(
                formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput)));
    }

    @PutMapping("/{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        final var formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }
}

