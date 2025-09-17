package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoNoRestauranteException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoNoRestauranteException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoNoRestauranteException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante " +
                "de código %d", produtoId, restauranteId));
    }
}
