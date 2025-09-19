create table restaurante_usuario_responsavel (restaurante_id bigint not null, usuario_id bigint not null, unique key uk_rest_usr (restaurante_id, usuario_id)) engine=InnoDB default charset=utf8;
