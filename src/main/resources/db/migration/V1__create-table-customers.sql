create table customers (
    id bigint not null auto_increment,
    date_of_birth date not null,
    created_date timestamp(6),
    last_modified_date timestamp(6),
    name varchar(100) not null,
    email varchar(255) not null unique,
    primary key (id)
)