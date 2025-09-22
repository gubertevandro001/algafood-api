set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert  into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (1, 'Thai Gourmet', 5.0, 1, utc_timestamp, utc_timestamp, 1, 1);
insert  into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Curry House', 7.0, 2, utc_timestamp, utc_timestamp, 1, 1);

insert  into forma_pagamento (id, descricao) values (1, 'Cartão de Crédito');
insert  into forma_pagamento (id, descricao) values (2, 'Cartão de Débito');
insert  into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert  into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1);
insert  into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 2);
insert  into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 3);

insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Coca-Cola', 'Coquinha', 5.90, 1);
insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Macarrão', 'Macarrão bom', 2.90, 1);
insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Arroz', 'Arroz top', 12.90, 1);

insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Pepsi', 'Pepsizona', 4.90, 2);
insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Feijão', 'Feijão show de bola', 1.90, 2);
insert into produto(ativo, nome, descricao, preco, restaurante_id) values(true, 'Carne', 'Carnaje', 34.90, 2);

insert  into grupo (id, nome) values (1, 'Gerente');
insert  into grupo (id, nome) values (2, 'Vendedor');
insert  into grupo (id, nome) values (3, 'Secretária');
insert  into grupo (id, nome) values (4, 'Cadastrador');

insert into permissao (id, nome, descricao) values (1, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1);
insert into grupo_permissao (grupo_id, permissao_id) values (1, 2);
insert into grupo_permissao (grupo_id, permissao_id) values (2, 2);

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1);
insert into usuario_grupo (usuario_id, grupo_id) values (1, 2);
insert into usuario_grupo (usuario_id, grupo_id) values (1, 3);
insert into usuario_grupo (usuario_id, grupo_id) values (2, 2);
insert into usuario_grupo (usuario_id, grupo_id) values (3, 1);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 1);
insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 2);
insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (2, 1);
insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (2, 3);

insert into estado (nome) values ('Minas Gerais');
insert into estado (nome) values ('Santa Catarina');
insert into estado (nome) values ('Paraná');

insert into cidade (nome, estado_id) values ('Pato Branco', 3);
insert into cidade (nome, estado_id) values ('Brusque', 2);
insert into cidade (nome, estado_id) values ('Monte Verde', 1);

insert into pedido (id,
    subtotal, taxa_frete, valor_total,
    restaurante_id, usuario_cliente_id, forma_pagamento_id,
    endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero,
    endereco_complemento, endereco_bairro,
    status, data_criacao, data_confirmacao, data_cancelamento, data_entrega) values
(1, 120.00, 10.00, 130.00, 1, 1, 1, 1, '12345-678', 'Rua das Flores', '100', 'Apto 201', 'Centro','CRIADO', utc_timestamp, null, null, null),
(2, 85.50, 5.00, 90.50, 2, 2, 2, 2, '98765-432', 'Av. Brasil', '500', 'Teste', 'Jardins','CRIADO', utc_timestamp, utc_timestamp, null, null),
(3, 60.00, 8.00, 68.00, 1, 2, 1, 1, '13579-246', 'Rua das Palmeiras', '50', 'Teste', 'Bela Vista', 'CRIADO', utc_timestamp, utc_timestamp, null, utc_timestamp);

insert into item_pedido (id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id) values
(1, 2, 30.00, 60.00, null, 1, 1),
(2, 1, 60.00, 60.00, null, 1, 2),
(3, 3, 25.00, 75.00, null, 2, 3),
(4, 1, 10.50, 10.50, null, 2, 4),
(5, 2, 20.00, 40.00, null, 3, 1),
(6, 1, 20.00, 20.00, null, 3, 5);

