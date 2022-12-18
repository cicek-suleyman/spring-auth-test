create database auth_test_db;
use auth_test_db;

create table privileges (
    -- base
    ID int(11) not null AUTO_INCREMENT,
    created_at DATETIME(3) not null default now(3),
    primary key (ID),

    name varchar(24) not null,
    unique key (name)
);

create table roles (
    -- base
    ID int(11) not null AUTO_INCREMENT,
    created_at DATETIME(3) not null default now(3),
    primary key (ID),

    authority varchar(24) not null,
    unique key (authority)
);

create table roles_privileges (
    role_id int(11) not null,
    privilege_id int(11) not null,
    constraint foreign key (role_id) references roles(ID),
    constraint foreign key (privilege_id) references privileges(ID)
);

create table users (
    -- base
    ID int(11) not null AUTO_INCREMENT,
    created_at DATETIME(3) not null default now(3),
    primary key (ID),

    -- auth
    uid varchar(64) not null,
    username varchar(20) not null,
    password varchar(60) default null,
    account_non_expired boolean default true,
    account_non_locked boolean default true,
    credentials_non_expired boolean default true,
    enabled boolean default false,
    unique key (uid),
    unique key (username)
);

create table auth_token (
-- base
ID int(11) not null AUTO_INCREMENT,
created_at DATETIME(3) not null default now(3),
primary key (ID),

token varchar(512) not null,
user_id int(11) not null,
constraint foreign key (user_id) references users(ID),
unique key (token)
);

create table users_roles (
    user_id int(11) not null,
    role_id int(11) not null,
    constraint foreign key (user_id) references users(ID),
    constraint foreign key (role_id) references roles(ID)
);

create table critical_data (
    -- base
    ID int(11) not null AUTO_INCREMENT,
    created_at DATETIME(3) not null default now(3),
    primary key (ID),

    value TEXT
);

create user auth_db@localhost identified by '3LEnDMlXEVr6b8a9pho=';
grant all privileges on auth_test_db.* to auth_db@localhost;