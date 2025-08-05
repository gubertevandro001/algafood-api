package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade adicionar(Cidade cidade) {
        final var estadoId = cidade.getEstado().getId();
        final var estado = estadoRepository.buscar(estadoId);

        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de estado com código %d", estadoId));
        }

        cidade.setEstado(estado);

        return cidadeRepository.salvar(cidade);
    }

    public void excluir(Long id) {
        cidadeRepository.remover(id);
    }
}
