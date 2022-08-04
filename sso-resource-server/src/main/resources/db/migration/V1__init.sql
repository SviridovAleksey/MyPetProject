create table Foo (
    id                      bigserial primary key,
    name                    varchar(30) not null unique
);

insert into Foo(id, name)
values
(1, 'Foo 1'),
(2, 'Foo 2');



create table users (
    id                      bigserial primary key,
    name                    varchar(250) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into users (id, name)
values
(1, 'a5461470-33eb-4b2d-82d4-b0484e96ad7f'),
(2, '22a4d9fe-194c-4c6e-841a-8a55b402459f');

create table roles (
    id                      bigserial primary key,
    name                    varchar(50) not null unique,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into roles(id, name)
values
(1, 'USER'),
(2, 'ADMIN');

CREATE TABLE users_roles (
          id                      bigserial,
          user_id                 bigint not null references users (id),
          role_id                 bigint not null references roles (id),
          created_at              timestamp default current_timestamp,
          updated_at              timestamp default current_timestamp,
          primary key (user_id, role_id)
);

insert into users_roles(id, user_id, role_id)
values
(1, 1, 1),
(2, 1, 2),
(3, 2, 1);


create table instruction_level (
    id                      bigserial primary key,
    name                    varchar(250) not null,
    value                   text not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

create table instruction_construct (
    id                      bigserial primary key,
    name                    varchar(250) not null,
    value                   text not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into instruction_construct(id, name, value)
values
(1, 'construct1', 1),
(2, 'construct2', 2),
(3, 'construct3', 3);

create table instruction_info (
    id                      bigserial primary key,
    name                    varchar(250) not null,
    user_creator            bigint not null references users (id),
    description             text not null,
    level_id                bigint not null,
    construct_id            bigint not null references instruction_construct (id),
    is_delete               boolean not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into instruction_info(id, name, user_creator, description, level_id, construct_id, is_delete)
values
(1, 'Probnaya1User1', 1, 'TestDescription', 1, 1, false),
(2, 'Probnaya2User1', 1, 'TestDescription', 1, 2, false),
(3, 'Probnaya3',  2, 'TestDescription', 2, 3, false);