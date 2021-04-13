drop database tododb;
drop user triveous;
create user triveous with password 'password';
create database tododb with template=template1 owner=triveous;
\connect tododb;
alter default privileges grant all on tables to triveous;
alter default privileges grant all on sequences to triveous;

create table trvs_user(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
password text not null
);

create table trvs_todo(
  id integer primary key not null,
  name varchar(20) not null,
  reminder_time bigint not null,
  status integer
);

create sequence trvs_user_seq increment 1 start 1;
create sequence trvs_todo_seq increment 1 start 1;