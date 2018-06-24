create table Admin (id  serial not null, password varchar(255), username varchar(255), primary key (id))
create table Apply (id  bigserial not null, email varchar(255), filename varchar(255), name varchar(255), phone varchar(255), primary key (id))
