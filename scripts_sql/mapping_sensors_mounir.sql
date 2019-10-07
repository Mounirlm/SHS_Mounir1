-- Database: shs

-- DROP DATABASE shs;

/*CREATE DATABASE shs
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;*/
	


create table users (
	id serial primary key,
	first_name varchar(25),
	last_name varchar(25),
	email varchar(100),
	login varchar(25),
	password varchar(25),
	function varchar(25));
	

	
create table building(
	id serial primary key,
	name varchar(25),
	type varchar(25));
	
					
	
	create table floor_map(
	id serial primary key,
	name varchar(25),
	image_path varchar(250),
	fk_building integer,
	unique (fk_building),
	foreign key(fk_building) references building(id));
												 
create table type_room
	(id serial primary key,
	name varchar(25) unique);
												 
	
	
create table wing_room
	(id serial primary key,
	name varchar(25) unique);
	
create table room
	(id serial primary key,
	floor integer,
	room_number integer not null,
	m2 integer,
	fk_type_room integer not null,
	fk_wing_room integer not null,
	nb_doors integer not null,
	nb_windows integer not null,
	x integer,
	y integer,
	width integer,
	height integer,
	fk_floor_map integer,
	unique (floor, room_number),
	foreign key(fk_type_room) references type_room(id),
	foreign key(fk_wing_room) references wing_room(id),
	foreign key(fk_floor_map) references floor_map(id));
	
create table type_sensor(
	id serial primary key,
	name varchar(25),
	trigger_point_min integer,
	trigger_point_max integer,
	nb_alerts integer);

/*2 same sensors can't e in the same position of a same room*/	
create table sensor(
	id serial primary key,
	sensor_name varchar(25),
	ip_address varchar(25) UNIQUE,
	mac_address varchar(25) UNIQUE,
	date_setup date,
	status boolean,
	installed boolean,
	fk_position integer,
	price float,
	fk_room integer,
	fk_type_sensor integer,
	scope_sensor integer not null,
	x integer,
	y integer,
	unique (fk_position, fk_room, fk_type_sensor),
	foreign key(fk_room) references room(id),
	foreign key(fk_type_sensor) references type_sensor(id),
	foreign key(fk_position) references wing_room(id));
	
	
	
	
	
	

	
	
create table alert
	(id serial primary key,
	date_alert date,
	hour_alert time,
	description varchar(200),
	fk_sensor integer,
	fk_users integer,
	status boolean,
	foreign key(fk_users) references users(id),
	foreign key(fk_sensor) references sensor(id));
	


create table resident
	(id serial primary key,
	resident_firstname varchar(25),
	resident_lastname varchar(25),
	date_birth date,
	gender char,
	fk_resident_room integer,
	foreign key(fk_resident_room) references room(id));
	

create table historical
	(id serial primary key,
	date_signal Date,
	hour_signal Time,
	message varchar(50),
	fk_sensor integer,
	foreign key(fk_sensor) references sensor(id));
	
	
	
/*
*INSERTIONS
*/


/* USERS*/
insert into users(first_name,last_name,email, password, function) values('kadia','toure','k.toure@shs.com','admin','supervisory officer');	
insert into users(first_name,last_name,email, password, function) values('louis','michel','l.michel@shs.com','123','direction');
insert into users(first_name,last_name,email, password, function) values('mounir','lemharzi','mounir@shs.com','admin','direction');	
/* TYPE ROOM*/	
insert into type_room (name) values ('bedroom');
insert into type_room (name) values ('cafeteria');
insert into type_room (name) values ('corridor');
insert into type_room (name) values ('stairs');
insert into type_room (name) values ('elevator');
insert into type_room (name) values ('playroom');
insert into type_room (name) values ('toilet');
insert into type_room (name) values ('cupboard');

/* WING ROOM*/	
insert into wing_room (name) values ('NORTH');
insert into wing_room (name) values ('SOUTH');
insert into wing_room (name) values ('EAST');
insert into wing_room (name) values ('WEST');
insert into wing_room (name) values ('CENTER');
insert into wing_room (name) values ('NORTH-EAST');
insert into wing_room (name) values ('NORTH-WEST');
insert into wing_room (name) values ('SOUTH-EAST');
insert into wing_room (name) values ('SOUTH-WEST');

/*insert into stock (name) values ('stock');*/

insert into building(name,type) values('Residence-1','building');
											 

insert into floor_map(name,image_path,fk_building) values('floor1','C:\Users\mounp\git\repository\SHS_AJAP\ressources\floor1.png',1);



/*bedroom mounir       */
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows,x,y,width,height,fk_floor_map) 
values (1,101,15,1, 1, 1,2,10,160,190,75,1),
 (1,102,15,1, 1, 1,2, 10,235,190,76, 1),
 (1,103,15,1, 1, 1,2, 10,311,190,89, 1),
 (1,104,15,1, 1, 1,2, 10,400,190,86,1),
 (1,105,15,1, 1, 1,2, 10,486,190,82,1),
 (1,106,15,1, 1, 1,2, 627,164,165,73,1),
 (1,107,15,1, 1, 1,2, 627,237,165,74,1),
 (1,108,15,1, 1, 1,2, 627,311,165,76,1),
 (1,109,15,1, 1, 1,2, 627,387,165,89,1),
 (1,110,15,1, 1, 1,2, 627,476,165,69, 1);
 
 
 
 /*cafeteria*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room,nb_doors, nb_windows,x,y,width,height,fk_floor_map) values (1,111,50,2, 2,2,3,  287,377,258,223,1);
 /*stairs */
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room,nb_doors, nb_windows,x,y,width,height,fk_floor_map ) values (1,200,10 ,4, 1,1,1 ,200,4,337,88,1);
 /*play room*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room,nb_doors, nb_windows,x,y,width,height,fk_floor_map) values (1,112,65, 6, 2,2,1, 276,160,272,150,1);
 /*elevator */
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room,nb_doors, nb_windows,x,y,width,height,fk_floor_map) values (1,201,5,5,2,1,1,    3,6,337,141,1);
 
 /*toilet*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room,nb_doors, nb_windows,x,y,width,height,fk_floor_map) values (1,113,115, 6, 6,1,1,644,3,156,93,1);
 

/* TYPE SENSORS*/	
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('smoke_sensor', null, 500, 3);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('temperature_sensor', 10, 24, 3);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('door_sensor', 6, 21, 1);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('window_sensor', 6, 21, 1);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('fall_sensor', null, 1, 2);

/*Stock*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup,status, installed, fk_position,price,fk_room, fk_type_sensor, scope_sensor,x,y)
values 
 ('sm-1g-25', '192.168.20.61', 'FE000LKS97', null, true, false,null, 25.99,null, 1,10,null,null),
 ('sm-1g-26', '192.168.20.62', 'FE000LKS98', null, true, false,null, 25.99, null, 1,10,null,null),
 ('sm-1g-27', '192.168.20.63', 'FE000LKS99', null, true, false,null, 25.99, null, 1,10,null,null),
 ('tp-1g-22', '192.168.20.141', 'TP000LKS81', null, true, false,null, 36.99,null, 2,10,null,null),
 ('tp-1g-23', '192.168.20.142', 'TP000LKS82',null , true, false,null, 36.99,null,  2,10,null,null),
 ('tp-1g-24', '192.168.20.143', 'TP000LKS83', null, true, false,null, 36.99,null, 2,10,null,null),
 ('tp-1g-25', '192.168.20.144', 'TP000LKS84', null, true, false,null, 36.99,null, 2,10,null,null),
 ('do-1g-25', '192.168.20.261', 'DO000LKS97', null, true, false,null, 44.99,null, 3,10,null,null),
 ('do-1g-26', '192.168.20.262', 'DO000LKS98',null,  true, false,null, 44.99,null, 3,10,null,null),
 ('do-1g-27', '192.168.20.263', 'DO000LKS99',null,  true, false,null, 44.99,null, 3,10,null,null),
 ('do-1g-28', '192.168.20.264', 'DO000LKS100',null,  true, false,null, 44.99,null,3,10,null,null),
 ('WI-1g-23', '192.168.20.363', 'WI000LKS99',null,  true, false,null, 60.99,null, 4,10,null,null),
 ('WI-1g-23', '192.168.20.364', 'WI000LKS100',null,  true, false,null, 60.99,null,4,10,null,null),
 ('WI-1g-24', '192.168.20.365', 'WI000LKS101',null,  true, false,null, 60.99,null,4,10,null,null);

 /*fisrt floor*//*1 sensor by bedroom*/
/*smoke*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed,fk_position, price,fk_room, fk_type_sensor,scope_sensor,x,y)
values ('sm-1g-5', '192.168.20.35', 'FE000LKS71', '2019-09-07', true, true,null, 25.99, 2, 1,10,85,245);


 
					  
/*Alert test*/												  
insert into alert (date_alert, hour_alert, description, fk_sensor, fk_users, status) values (now(), '17:00:00', 'smoke > 500 3 times', 1, 1, true);	
insert into alert (date_alert, hour_alert, description, fk_sensor, fk_users, status) values (now(), '17:00:30', 'smoke > 500 3 times', 1, 1, true);						

INSERT INTO resident(resident_firstname, resident_lastname, date_birth, gender, fk_resident_room) VALUES 
('Anael', 'Baelhasem', '1958-12-20', 'F', 4), ('Kadia', 'Toure', '1931-03-16','F',5),
('Mounir', 'Lemharzi', '1957-02-26', 'H',6), ('Eric', 'Mouss', '1930-09-17', 'H',7),
('Nesirne', 'Harrouch', '1925-04-03', 'F',8), ('David', 'Lambourdiere', '1942-03-05','H',9),
('Guillaume', 'Taoudiat', '1922-12-21','H',10), ('Andrei', 'Petrov', '1940-12-02', 'H',11),
('Mariam', 'Rakibi', '1927-02-02', 'F',12), ('Lucas', 'Meunier', '1933-11-07', 'H',13);		