package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		assertThatThrownBy(() -> cadastroCozinhaService.salvar(novaCozinha));
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		Throwable thrown = catchThrowable(() -> cadastroCozinhaService.excluir(1L));
		assertThat(thrown).isInstanceOf(EntidadeEmUsoException.class);
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		Throwable thrown = catchThrowable(() -> cadastroCozinhaService.excluir(100L));
		assertThat(thrown).isInstanceOf(CozinhaNaoEncontradaException.class);
	}
}
