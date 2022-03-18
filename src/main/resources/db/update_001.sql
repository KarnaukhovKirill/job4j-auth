create table employees (
    id serial primary key,
    name varchar(255),
    surname varchar(255),
    inn varchar(12) unique ,
    hired timestamp
);

create table persons (
    id serial primary key not null,
    login varchar(2000) unique ,
    password varchar(2000)
);

create table employee_person (
    employee_id int references employees(id),
    person_id int references persons(id)
);

insert into employees (name, surname, inn, hired) values ('employee', 'one', '123456789876', CURRENT_DATE);

insert into persons (login, password) values ('firstAcc', 'password');
insert into persons (login, password) values ('secondAcc', '123');

insert into employee_person (employee_id, person_id) values (1, 1);
insert into employee_person (employee_id, person_id) values (1, 2);