create table cafe_da_manha (
	id serial not null primary key,
    cliente_id bigint not null,
    descricao text not null
);

alter table cafe_da_manha add constraint fk_cafa_da_manha_cliente
foreign key (cliente_id) references cliente (id);