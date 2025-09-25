package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResumoModel {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal subtotal;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private RestaurantePedidoModel restaurante;
    private UsuarioModel cliente;
}
