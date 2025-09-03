package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private Cozinha cozinhaAmericana;
	private Cozinha cozinhaTailandesa;
	private Integer totalCozinhas;

	private static final Long COZINHA_ID_INEXISTENTE = 100L;
	private static final String COZINHA_ID = "cozinhaId";

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();

		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConterCozinhas_QuandoConsultarCozinhas() {

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", hasSize(totalCozinhas));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body(getContentFromResource())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarResposaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam(COZINHA_ID, cozinhaTailandesa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaTailandesa.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam(COZINHA_ID, COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {

		cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");

		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");

		cozinhaRepository.saveAll(List.of(cozinhaTailandesa, cozinhaAmericana));

		totalCozinhas = (int) cozinhaRepository.count();
	}

	private static String getContentFromResource() {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream("/cozinha.json");
			return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
