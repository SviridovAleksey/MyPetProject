
create table steps_text (
    id                      bigserial primary key,
    value                   text not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

create table steps_info (
    id                      bigserial primary key,
    name                    varchar(250) not null,
    step_place              bigint not null,
    instruction_info_id     bigint not null,
    user_id                 bigint not null,
    text_id                 bigint not null references steps_text (id),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into steps_text(value)
values
('sdelaem shag 1'),
('sdelaem shag 2'),
('sdelaem shag 3'),
('sdelaem shag 4'),
('sdelaem shag 5'),
('sdelaem shag 6'),
('sdelaem shag 7'),
('sdelaem shag 8'),
('sdelaem shag 9'),
('sdelaem shag 10'),
('sdelaem shag 11');

insert into steps_info(name, step_place, instruction_info_id, user_id, text_id)
values
('step1', 1, 1, 1, 1),
('step2', 2, 1, 1, 2),
('step3', 3, 1, 1, 3),
('step4', 4, 1, 1, 4),
('step5', 5, 1, 1, 5),
('step6', 6, 1, 1, 6),
('step7', 7, 1, 1, 7),
('step8', 8, 1, 1, 8),
('step9', 9, 1, 1, 9),
('step10', 10, 1, 1, 10),
('step11', 11, 1, 1, 11);