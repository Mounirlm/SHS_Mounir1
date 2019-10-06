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
	unique (floor, room_number),
	foreign key(fk_type_room) references type_room(id),
	foreign key(fk_wing_room) references wing_room(id));
	
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
	
/* TYPE ROOM*/	
insert into type_room (name) values ('bedroom');
insert into type_room (name) values ('cafeteria');
insert into type_room (name) values ('corridor');
insert into type_room (name) values ('stairs');
insert into type_room (name) values ('elevator');
insert into type_room (name) values ('playroom');
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

/*--ROOMS--*/
/*--ground floor--*/
/*corridor north wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (0,1,22,3, 1, 1,2);
/*stairs north wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (0,2,10,4, 1, 1,1);
/*elevator south wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (null,1,5,5, 2, 1,0);

/*--first floor--*/
/*bedrooms*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) 
values (1,101,15,1, 1, 1,2),
 (1,102,15,1, 1, 1,2),
 (1,103,15,1, 1, 1,2),
 (1,104,15,1, 1, 1,2),
 (1,105,15,1, 1, 1,2),
 (1,106,15,1, 1, 1,2),
 (1,107,15,1, 1, 1,2),
 (1,108,15,1, 1, 1,2),
 (1,109,15,1, 1, 1,2),
 (1,110,15,1, 1, 1,2);
/*cafeteria*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,111,50, 2, 2, 4,4);
/*corridor north wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,1, 22, 3, 1, 1,2);
/*corridor south wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,2,30, 3, 2, 1,2);
/*stairs north wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,3,10 ,4, 1, 1,2);
/*stairs south wing*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,4,10, 4, 2, 1,2);
/*play room*/
insert into room (floor, room_number, m2, fk_type_room, fk_wing_room, nb_doors, nb_windows) values (1,112,65, 6, 2, 5,7);
	
/* TYPE SENSORS*/	
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('smoke_sensor', null, 500, 3);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('temperature_sensor', 10, 24, 3);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('door_sensor', 6, 21, 1);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('window_sensor', 6, 21, 1);
insert into type_sensor (name, trigger_point_min, trigger_point_max, nb_alerts) values ('fall_sensor', null, 1, 2);



	
/*--SENSORS--*/
/*----SMOKE SENSORS----*/
/*groud floor*/
/*2 sensors in coorridor,1 sensors in stairs, 1 sensor in elevator*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor) 
values ('sm-1g-1', '192.168.20.31', 'FE000LKS67', '2019-04-16', true, true, 5, 25.99, 1, 1, 10),
 ('sm-1g-2', '192.168.20.32', 'FE000LKS68', '2019-04-16', true, true, 1, 25.99, 1, 1, 10),
 ('sm-1g-3', '192.168.20.33', 'FE000LKS69', '2019-04-16', true, false, 2, 25.99, 2, 1, 10),
 ('sm-1g-4', '192.168.20.34', 'FE000LKS70', '2019-04-16', true, false, 5, 25.99, 3, 1, 10);
/*fisrt floor*//*1 sensor by bedroom*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('sm-1g-5', '192.168.20.35', 'FE000LKS71', '2019-04-16', true, false, 5, 25.99, 4, 1, 10),
 ('sm-1g-6', '192.168.20.36', 'FE000LKS72', '2019-04-16', true, false, 5, 25.99, 5, 1, 10),
 ('sm-1g-7', '192.168.20.37', 'FE000LKS73', '2019-04-16', true, false, 5, 25.99, 6, 1, 10),
 ('sm-1g-8', '192.168.20.38', 'FE000LKS74', '2019-04-16', true, false, 5, 25.99, 7, 1, 10),
 ('sm-1g-9', '192.168.20.39', 'FE000LKS75', '2019-04-16', true, false, 5, 25.99, 8, 1, 10),
 ('sm-1g-10', '192.168.20.40', 'FE000LKS76', '2019-04-16', true, false, 5, 25.99, 9, 1, 10),
 ('sm-1g-11', '192.168.20.41', 'FE000LKS77', '2019-04-16', true, false, 5, 25.99, 10, 1, 10),
 ('sm-1g-12', '192.168.20.42', 'FE000LKS78', '2019-04-16', true, false, 5, 25.99, 11, 1, 10),
 ('sm-1g-13', '192.168.20.43', 'FE000LKS79', '2019-04-16', true, false, 5, 25.99, 12, 1, 10),
 ('sm-1g-14', '192.168.20.44', 'FE000LKS80', '2019-04-16', true, false, 5, 25.99, 13, 1, 10);
/*cafeteria*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('sm-1g-15', '192.168.20.45', 'FE000LKS81', '2019-04-16', true, false, 5, 25.99, 14, 1, 10),
 ('sm-1g-16', '192.168.20.46', 'FE000LKS82', '2019-04-16', true, false, 2, 25.99, 14, 1, 10),
 ('sm-1g-17', '192.168.20.47', 'FE000LKS83', '2019-04-16', true, false, 1, 25.99, 14, 1, 10),
 ('sm-1g-18', '192.168.20.48', 'FE000LKS84', '2019-04-16', true, false, 3, 25.99, 14, 1, 10);

/*corridor and stairs*//* 2 sensors corridor, 3 sensors in corridor, 1 sensor in stair, 1 sensor in stair*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('sm-1g-19', '192.168.20.49', 'FE000LKS85', '2019-04-16', true, false, 5, 25.99, 15, 1, 10),
 ('sm-1g-20', '192.168.20.50', 'FE000LKS86', '2019-04-16', true, false, 1, 25.99, 15, 1, 10),
 ('sm-1g-21', '192.168.20.51', 'FE000LKS87', '2019-04-16', true, false, 5, 25.99, 16, 1, 10),
 ('sm-1g-22', '192.168.20.52', 'FE000LKS88', '2019-04-16', true, false, 1, 25.99, 16, 1, 10),
 ('sm-1g-23', '192.168.20.53', 'FE000LKS89', '2019-04-16', true, false, 2, 25.99, 16, 1, 10),
 ('sm-1g-24', '192.168.20.54', 'FE000LKS90', '2019-04-16', true, false, 5, 25.99, 17, 1, 10),
 ('sm-1g-25', '192.168.20.55', 'FE000LKS91', '2019-04-16', true, false, 5, 25.99, 18, 1, 10);

/*play room*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('sm-1g-20', '192.168.20.56', 'FE000LKS92', '2019-04-16', true, false, 5, 25.99, 19, 1, 10),
 ('sm-1g-21', '192.168.20.57', 'FE000LKS93', '2019-04-16', true, false, 4, 25.99, 19, 1, 10),
 ('sm-1g-22', '192.168.20.58', 'FE000LKS94', '2019-04-16', true, false, 1, 25.99, 19, 1, 10),
 ('sm-1g-23', '192.168.20.59', 'FE000LKS95', '2019-04-16', true, false, 2, 25.99, 19, 1, 10),
 ('sm-1g-24', '192.168.20.60', 'FE000LKS96', '2019-04-16', true, false, 3, 25.99, 19, 1, 10);

/*----Temperature SENSOR-------*/
/*groud floor*/
/*elevator*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor) 
values ('tp-1g-4', '192.168.20.14', 'TP000LKS70', '2019-04-16', true, true, 1, 36.99, 3, 2, 10);

/*fisrt floor*/
/*1 by bedroom*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('tp-1g-5', '192.168.20.135', 'TP000LKS71', '2019-04-16', true, false, 1, 36.99, 4, 2, 10),
 ('tp-1g-6', '192.168.20.136', 'TP000LKS72', '2019-04-16', true, false, 1, 36.99, 5, 2, 10),
 ('tp-1g-7', '192.168.20.137', 'TP000LKS73', '2019-04-16', true, false, 1, 36.99, 6, 2, 10),
 ('tp-1g-8', '192.168.20.138', 'TP000LKS74', '2019-04-16', true, false, 1, 36.99, 7, 2, 10),
 ('tp-1g-9', '192.168.20.139', 'TP000LKS75', '2019-04-16', true, false, 1, 36.99, 8, 2, 10),
 ('tp-1g-10', '192.168.20.140', 'TP000LKS76', '2019-04-16', true, false, 1, 36.99, 9, 2, 10),
 ('tp-1g-11', '192.168.20.141', 'TP000LKS77', '2019-04-16', true, false, 1, 36.99, 10, 2, 10),
 ('tp-1g-12', '192.168.20.142', 'TP000LKS78', '2019-04-16', true, false, 1, 36.99, 11, 2, 10),
 ('tp-1g-13', '192.168.20.143', 'TP000LKS79', '2019-04-16', true, false, 1, 36.99, 12, 2, 10),
 ('tp-1g-14', '192.168.20.144', 'TP000LKS80', '2019-04-16', true, false, 1, 36.99, 13, 2, 10);

/*cafeteria*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('tp-1g-15', '192.168.20.145', 'TP000LKS81', '2019-04-16', true, false, 4, 36.99, 14, 2, 10),
 ('tp-1g-16', '192.168.20.146', 'TP000LKS82', '2019-04-16', true, false, 1, 36.99, 14, 2, 10);


/*play room*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('tp-1g-20', '192.168.20.156', 'TP000LKS92', '2019-04-16', true, false, 1, 36.99, 19, 2, 10);
 
 
 

/*--DOOR SENSORS--*/
/*groud floor*/
/*stairs*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor) 
values ('do-1g-3', '192.168.20.233', 'DO000LKS69', '2019-04-16', true, false, 1, 44.99, 2, 3, 10);
/*fisrt floor*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('do-1g-5', '192.168.20.235', 'DO000LKS71', '2019-04-16', true, true, 1, 44.99, 4, 3, 10),
 ('do-1g-6', '192.168.20.236', 'DO000LKS72', '2019-04-16', true, false, 1, 44.99, 5, 3, 10),
 ('do-1g-7', '192.168.20.237', 'DO000LKS73', '2019-04-16', true, false, 1, 44.99, 6, 3, 10),
 ('do-1g-8', '192.168.20.238', 'DO000LKS74', '2019-04-16', true, false, 1, 44.99, 7, 3, 10),
 ('do-1g-9', '192.168.20.239', 'DO000LKS75', '2019-04-16', true, false, 1, 44.99, 8, 3, 10),
 ('do-1g-10', '192.168.20.240', 'DO000LKS76', '2019-04-16', true, false, 1, 44.99, 9, 3, 10),
 ('do-1g-11', '192.168.20.241', 'DO000LKS77', '2019-04-16', true, false, 1, 44.99, 10, 3, 10),
 ('do-1g-12', '192.168.20.242', 'DO000LKS78', '2019-04-16', true, false, 1, 44.99, 11, 3, 10),
 ('do-1g-13', '192.168.20.243', 'DO000LKS79', '2019-04-16', true, false, 1, 44.99, 12, 3, 10),
 ('do-1g-14', '192.168.20.244', 'DO000LKS80', '2019-04-16', true, false, 1, 44.99, 13, 3, 10);
/*cafeteria*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('do-1g-15', '192.168.20.245', 'DO000LKS81', '2019-04-16', true, false, 1, 44.99, 14, 3, 10),
 ('do-1g-16', '192.168.20.246', 'DO000LKS82', '2019-04-16', true, false, 2, 44.99, 14, 3, 10),
 ('do-1g-17', '192.168.20.247', 'DO000LKS83', '2019-04-16', true, false, 5, 44.99, 14, 3, 10),
 ('do-1g-18', '192.168.20.248', 'DO000LKS84', '2019-04-16', true, false, 4, 44.99, 14, 3, 10);

/*stairs*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('do-1g-24', '192.168.20.254', 'DO000LKS90', '2019-04-16', true, false, 1, 44.99, 17, 3, 10),
 ('do-1g-25', '192.168.20.255', 'DO000LKS91', '2019-04-16', true, false, 1, 44.99, 18, 3, 10);

/*play room*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
 values ('do-1g-20', '192.168.20.256', 'DO000LKS92', '2019-04-16', true, false, 1, 44.99, 19, 3, 10),
 ('do-1g-21', '192.168.20.257', 'DO000LKS93', '2019-04-16', true, false, 2, 44.99, 19, 3, 10),
 ('do-1g-22', '192.168.20.258', 'DO000LKS94', '2019-04-16', true, false, 5, 44.99, 19, 3, 10),
 ('do-1g-23', '192.168.20.259', 'DO000LKS95', '2019-04-16', true, false, 3, 44.99, 19, 3, 10),
 ('do-1g-24', '192.168.20.260', 'DO000LKS96', '2019-04-16', true, false, 4, 44.99, 19, 3, 10);

/*--WINDOW SENSORS--*/
/*fisrt floor*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('WI-1g-5', '192.168.20.335', 'WI000LKS71', '2019-04-16', true, true, 1, 60.99, 4, 4, 10),
 ('WI-1g-6', '192.168.20.336', 'WI000LKS72', '2019-04-16', true, false, 1, 60.99, 5, 4, 10),
 ('WI-1g-7', '192.168.20.337', 'WI000LKS73', '2019-04-16', true, false, 1, 60.99, 6, 4, 10),
 ('WI-1g-8', '192.168.20.338', 'WI000LKS74', '2019-04-16', true, false, 1, 60.99, 7, 4, 10),
 ('WI-1g-9', '192.168.20.339', 'WI000LKS75', '2019-04-16', true, false, 1, 60.99, 8, 4, 10),
 ('WI-1g-10', '192.168.20.340', 'WI000LKS76', '2019-04-16', true, false, 1, 60.99, 9, 4, 10),
 ('WI-1g-11', '192.168.20.341', 'WI000LKS77', '2019-04-16', true, false, 1, 60.99, 10, 4, 10),
 ('WI-1g-12', '192.168.20.342', 'WI000LKS78', '2019-04-16', true, false, 1, 60.99, 11, 4, 10),
 ('WI-1g-13', '192.168.20.343', 'WI000LKS79', '2019-04-16', true, false, 1, 60.99, 12, 4, 10),
 ('WI-1g-14', '192.168.20.344', 'WI000LKS80', '2019-04-16', true, false, 1, 60.99, 13, 4, 10);
/*cafeteria*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('WI-1g-15', '192.168.20.345', 'WI000LKS81', '2019-04-16', true, false, 1, 60.99, 14, 4, 10),
 ('WI-1g-16', '192.168.20.346', 'WI000LKS82', '2019-04-16', true, false, 2, 60.99, 14, 4, 10),
 ('WI-1g-17', '192.168.20.347', 'WI000LKS83', '2019-04-16', true, false, 5, 60.99, 14, 4, 10),
 ('WI-1g-18', '192.168.20.348', 'WI000LKS84', '2019-04-16', true, false, 3, 60.99, 14, 4, 10);

/*corridors*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('WI-1g-19', '192.168.20.349', 'WI000LKS85', '2019-04-16', true, false, 1, 60.99, 15, 4, 10),
 ('WI-1g-20', '192.168.20.350', 'WI000LKS86', '2019-04-16', true, false, 2, 60.99, 15, 4, 10),
 ('WI-1g-21', '192.168.20.351', 'WI000LKS87', '2019-04-16', true, false, 3, 60.99, 16, 4, 10);


/*play room*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
 values ('WI-1g-20', '192.168.20.356', 'WI000LKS92', '2019-04-16', true, false, 1, 60.99, 19, 4, 10),
 ('WI-1g-21', '192.168.20.357', 'WI000LKS93', '2019-04-16', true, false, 2, 60.99, 19, 4, 10),
 ('WI-1g-22', '192.168.20.358', 'WI000LKS94', '2019-04-16', true, false, 5, 60.99, 19, 4, 10),
 ('WI-1g-23', '192.168.20.359', 'WI000LKS95', '2019-04-16', true, false, 3, 60.99, 19, 4, 10);

/*--FALL SENSORS--*/
/*fisrt floor*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('FA-1g-5', '192.168.20.435', 'FA000LKS71', '2019-04-16', true, true, 1, 150.99, 4, 5, 10),
 ('FA-1g-6', '192.168.20.436', 'FA000LKS72', '2019-04-16', true, false, 1, 150.99, 5, 5, 10),
 ('FA-1g-7', '192.168.20.437', 'FA000LKS73', '2019-04-16', true, false, 1, 150.99, 6, 5, 10),
 ('FA-1g-8', '192.168.20.438', 'FA000LKS74', '2019-04-16', true, false, 1, 150.99, 7, 5, 10),
 ('FA-1g-9', '192.168.20.439', 'FA000LKS75', '2019-04-16', true, false, 1, 150.99, 8, 5, 10),
 ('FA-1g-10', '192.168.20.440', 'FA000LKS76', '2019-04-16', true, false, 1, 150.99, 9, 5, 10),
 ('FA-1g-11', '192.168.20.441', 'FA000LKS77', '2019-04-16', true, false, 1, 150.99, 10, 5, 10),
 ('FA-1g-12', '192.168.20.442', 'FA000LKS78', '2019-04-16', true, false, 1, 150.99, 11, 5, 10),
 ('FA-1g-13', '192.168.20.443', 'FA000LKS79', '2019-04-16', true, false, 1, 150.99, 12, 5, 10),
 ('FA-1g-14', '192.168.20.444', 'FA000LKS80', '2019-04-16', true, false, 1, 150.99, 13, 5, 10);
/*cafeteria*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('FA-1g-15', '192.168.20.445', 'FA000LKS81', '2019-04-16', true, false, 1, 150.99, 14, 5, 10),
 ('FA-1g-16', '192.168.20.446', 'FA000LKS82', '2019-04-16', true, false, 2, 150.99, 14, 5, 10),
 ('FA-1g-17', '192.168.20.447', 'FA000LKS83', '2019-04-16', true, false, 5, 150.99, 14, 5, 10),
 ('FA-1g-18', '192.168.20.448', 'FA000LKS84', '2019-04-16', true, false, 3, 150.99, 14, 5, 10);

/*corridors*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
values ('FA-1g-19', '192.168.20.449', 'FA000LKS85', '2019-04-16', true, false, 1, 150.99, 15, 5, 10),
 ('FA-1g-20', '192.168.20.450', 'FA000LKS86', '2019-04-16', true, false, 2, 150.99, 15, 5, 10),
 ('FA-1g-21', '192.168.20.451', 'FA000LKS87', '2019-04-16', true, false, 3, 150.99, 16, 5, 10),
 ('FA-1g-22', '192.168.20.452', 'FA000LKS88', '2019-04-16', true, false, 1, 150.99, 16, 5, 10);


/*play room*/
insert into sensor (sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)
 values ('FA-1g-20', '192.168.20.456', 'FA000LKS92', '2019-04-16', true, false, 1, 150.99, 19, 5, 10),
 ('FA-1g-21', '192.168.20.457', 'FA000LKS93', '2019-04-16', true, false, 2, 150.99, 19, 5, 10),
 ('FA-1g-22', '192.168.20.458', 'FA000LKS94', '2019-04-16', true, false, 5, 150.99, 19, 5, 10),
 ('FA-1g-23', '192.168.20.459', 'FA000LKS95', '2019-04-16', true, false, 3, 150.99, 19, 5, 10);
 
/*Alert test*/												  
insert into alert (date_alert, hour_alert, description, fk_sensor, fk_users, status) values (now(), '17:00:00', 'smoke > 500 3 times', 1, 1, true);	
insert into alert (date_alert, hour_alert, description, fk_sensor, fk_users, status) values (now(), '17:00:30', 'smoke > 500 3 times', 1, 1, true);						

/*--Resident--*/
INSERT INTO resident(resident_firstname, resident_lastname, date_birth, gender, fk_resident_room) VALUES 
('Anael', 'Baelhasem', '1958-12-20', 'F', 4), ('Kadia', 'Toure', '1931-03-16','F',5),
('Mounir', 'Lehmarzi', '1957-02-26', 'H',6), ('Eric', 'Mouss', '1930-09-17', 'H',7),
('Nesirne', 'Harrouch', '1925-04-03', 'F',8), ('David', 'Lambourdiere', '1942-03-05','H',9),
('Guillaume', 'Taoudiat', '1922-12-21','H',10), ('Andrei', 'Petrov', '1940-12-02', 'H',11),
('Mariam', 'Rakibi', '1927-02-02', 'F',12), ('Lucas', 'Meunier', '1933-11-07', 'H',13);						  												  