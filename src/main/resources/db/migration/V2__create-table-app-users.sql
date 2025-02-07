create table app_users (
    id bigint not null auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    app_user_role VARCHAR(255),
    locked bit,
    enabled bit,
    primary key (id)
)