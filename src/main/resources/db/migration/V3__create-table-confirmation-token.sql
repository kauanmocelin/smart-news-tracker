create table confirmation_tokens (
    id bigint not null auto_increment,
    token varchar(255) not null,
    created_at timestamp(6),
    expiresd_at timestamp(6),
    confirmed_at timestamp(6),
    app_user_id bigint not null,
    primary key (id),
    foreign key (app_user_id)
        references app_users(id)
)