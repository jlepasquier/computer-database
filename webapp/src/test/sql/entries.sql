insert into company (id,name) values ( 1,'Apple Inc.');
insert into company (id,name) values ( 2,'Thinking Machines');


insert into computer (id,name,introduced,discontinued,company_id) values ( 1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 2,'CM-2a',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 5,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 7,'Apple IIe',null,null,null);


insert into role (name) values ('admin');
insert into users (name, password, role_id) values ('admin', 'admin', 0);