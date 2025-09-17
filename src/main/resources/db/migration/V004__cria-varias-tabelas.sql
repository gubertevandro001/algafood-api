create table forma_pagamento (
    id bigint not null auto_increment,
    descricao varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;


create table grupo (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB default charset=utf8;

create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB default charset=utf8;
create table permissao (id bigint not null, descricao varchar(255), primary key (id)) engine=InnoDB default charset=utf8;
create table produto (id bigint not null auto_increment, ativo bit not null, nome varchar(255), descricao varchar(255), preco decimal(19,2),
restaurante_id bigint, primary key (id)) engine=InnoDB default charset=utf8;
create table restaurante (id bigint not null auto_increment, data_atualizacao datetime not null, data_cadastro datetime not null,
endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255),
endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255),
taxa_frete decimal(19,2), cozinha_id bigint not null, endereco_cidade_id bigint, primary key (id)) engine=InnoDB default charset=utf8;
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null, unique key uk_rest_frm_pag (restaurante_id, forma_pagamento_id)) engine=InnoDB default charset=utf8;
create table usuario (id bigint not null, data_cadastro datetime not null, email varchar(255), nome varchar(255), senha varchar(255),
primary key (id)) engine=InnoDB default charset=utf8;
create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB default charset=utf8;
alter table grupo_permissao add constraint fk_permissao_grupo foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint fk_grupo_permissao foreign key (grupo_id) references grupo (id);
alter table produto add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint fk_restaurante_end_cidade_id foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_forma_pagamento add constraint fk_restfpgto_fpgto_id foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_forma_pagamento add constraint fk_restfpgto_rest_id foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint fk_usuariogrupo_grupo_id foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint fk_usuariogrupo_usuario_id foreign key (usuario_id) references usuario (id);