drop database tododb;
drop user postgres;
drop table TRVS_TODO;
create user postgres with password 'password';
create database tododb with template=template1 owner=postgres;
\connect tododb;
alter default privileges grant all on tables to postgres;
alter default privileges grant all on sequences to postgres;

create table TRVS_USER(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
password text not null,
email varchar(30) not null,
username varchar(20) not null
);

create table TRVS_TODO(
  id integer primary key not null,
  name varchar(20) not null,
  reminder_time bigint not null,
  status integer not null,
  priority integer not null,
  user_id integer not null
);

create sequence trvs_user_seq increment 1 start 1;
create sequence trvs_todo_seq increment 1 start 1;