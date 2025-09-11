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

insert ignore into cozinha (id, nome) values (1, 'Tailandesa');
insert ignore into cozinha (id, nome) values (2, 'Indiana');

insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (1, 'Thai Gourmet', 5.0, 1, utc_timestamp, utc_timestamp, 1);
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Curry House', 7.0, 2, utc_timestamp, utc_timestamp, 1);

insert ignore into grupo (id, nome) values (1, 'Gerente');
insert ignore into grupo (id, nome) values (2, 'Vendedor');
insert ignore into grupo (id, nome) values (3, 'Secretária');
insert ignore into grupo (id, nome) values (4, 'Cadastrador');

