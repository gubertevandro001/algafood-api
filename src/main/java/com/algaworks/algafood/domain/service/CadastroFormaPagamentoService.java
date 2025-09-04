package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaDePagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroFormaPagamentoService {

    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removido, pois esta em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long estadoId) {
        try {
            formaPagamentoRepository.deleteById(estadoId);
            formaPagamentoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new FormaDePagamentoNaoEncontradaException(estadoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, estadoId));
        }
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(
                () -> new FormaDePagamentoNaoEncontradaException(formaPagamentoId));
    }
}
