
create table levels_info (
    id                      bigserial primary key,
    name                    varchar(250) not null,
    owner                   bigint not null,
    level_place             bigint not null,
    user_id                 bigint not null,
    is_delete               boolean not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);


insert into levels_info(name, owner, level_place, user_id, is_delete)
values
('Level1',0, 0, 1, false),
('Level12',1, 1, 1, false),
('LevelNext1',1, 1, 1, false),
('Level13',2, 2, 1, false),
('Level3',0, 0, 1, false);
