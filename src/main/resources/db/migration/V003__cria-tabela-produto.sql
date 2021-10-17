create table produto (
	id serial not null primary key,
    cafe_da_manha_id bigint not null,
    descricao text not null    
);

alter table produto add constraint fk_produto_cafe_da_manha
foreign key (cafe_da_manha_id) references cafe_da_manha (id);