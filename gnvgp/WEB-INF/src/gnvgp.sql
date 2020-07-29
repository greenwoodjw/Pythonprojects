CREATE DATABASE IF NOT EXISTS gnvgp;
USE gnvgp;

DROP TABLE IF EXISTS victims;
Drop table if exists jackpot;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS log;
drop table if exists wins;

DROP TABLE IF EXISTS users;

create table users
(
  user_name varchar(15) not null,
  user_pass varchar(15) not null,
  firstname varchar(15) not null,
  lastname varchar(15) not null,
  email varchar(32) not null,
  	friendlyname  varchar(50) NOT NULL,
  	balance int NOT NULL,
	PRIMARY KEY (user_name)
)type=innodb;


create table roles
(
  role_name varchar(15) not null primary key
)type= innodb;

create table user_roles
(
  user_name varchar(15) not null,
  role_name varchar(15) not null,
  primary key( user_name, role_name )
)type=innodb;


CREATE TABLE jackpot (
	month timestamp NOT NULL,
	skin int NOT NULL,
	awarded boolean not null
)  type=innodb;

CREATE TABLE victims (
	vicID int AUTO_INCREMENT,
	ownerid varchar(8) NOT NULL,
	victimfirstname varchar (256) NOT NULL,
	victimlastname varchar (256) NOT NULL,
	months int NOT NULL,
	stolen boolean not null,
	INDEX (victimlastname),
	Primary key (vicID),
	FOREIGN KEY (ownerid) REFERENCES users (user_name)
)  TYPE=INNODB;

CREATE TABLE wins (
	vicID int,
	ownerid varchar(8) NOT NULL,
	victimfirstname varchar (256) NOT NULL,
	victimlastname varchar (256) NOT NULL,
    windate timestamp not null,
	INDEX (victimlastname),
	Primary key (vicID),
	FOREIGN KEY (ownerid) REFERENCES users (user_name)
)  TYPE=INNODB;


CREATE TABLE log (
month timestamp NOT NULL,
type varchar(12),
vicID int,
ownerid varchar(8),
amount int )
type=innodb;

//insert into wins (vicID, ownerid,victimfirstname,victimlastname,windate) values ( 0,1,"joe","brown","12/31/01");
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("jim", "password", "jim","greenwood","jameswgreenwood@aol.com","Jim Greenwood",100);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("admin", "password", "admin","admin","rootx@thenancyjeanteam.com","admin",0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("joe", "password", "jim","greenwood","jagreenwoodjr@mac.om","joe greenwood",3);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("doug", "password", "Doug","Lang","doug@dougandjulie.com","Doug Lang",100);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("ahunt", "password", "amy", "hunt", "ahunt@landonstark.com", "Amy Hunt", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("clee", "password", "chris", "lee", "ch1lee@verizon.ne", "Chris Lee", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("dmorse", "password", "dan", "morse", "morsed@washpost.com", "Dan Morse", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("ddeblyker", "password", "dave", "den blyker", "ddenblyker@liquidmachines.com", "Dave Den Bleyker", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("ddavert", "password", "doug", "davert", "dougdavert@davertandloe.com", "Doug Davert", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("dlang", "password", "doug", "lang","doug@dougandjulie.com", "Doug Lang", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("jcoates", "password", "joan", "coates", "coatesrm@verizon.net", "Joan Coates","0);





//insert into jackpot (month, skin, awarded) values ("2007-12-01", 88, 0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("joe","bob","brbown", -99,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("jim","bob","jones", 0,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("jim","bob","hope", 18,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("joe","bob","newhart", 3,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("joe","bob","apples", 1,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("joe","bob","smith", 0,0);
insert into victims (ownerid, victimfirstname, victimlastname, months, stolen) values ("doug","bob","flapdoodle",12,0);








insert into roles (role_name) values ("user");
insert into roles (role_name) values ("admin");

insert into user_roles (user_name, role_name) values ("jim","admin");
insert into user_roles (user_name, role_name) values ("admin","admin");
insert into user_roles (user_name, role_name) values ("joe","user");
insert into user_roles (user_name, role_name) values ("doug","user");
insert into user_roles (user_name, role_name) values ("ahunt","user");
insert into user_roles (user_name, role_name) values ("clee","user");
insert into user_roles (user_name, role_name) values ("dmorse","user");
insert into user_roles (user_name, role_name) values ("ddeblyker","user");
insert into user_roles (user_name, role_name) values ("ddavert","user");
insert into user_roles (user_name, role_name) values ("dlang","user");
insert into user_roles (user_name, role_name) values ("jcoates","user");
insert into user_roles (user_name, role_name) values ("jlang","user");
insert into user_roles (user_name, role_name) values ("kjohnson","user");
insert into user_roles (user_name, role_name) values ("kbuckley","user");
insert into user_roles (user_name, role_name) values ("mjacobs","user");
insert into user_roles (user_name, role_name) values ("ngreenwood","user");
insert into user_roles (user_name, role_name) values ("sraitala","user");


insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("kjohnson", "password", "keith", "johnson", "kjohnson@liquidmachines.com", "Keith Johnson", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("kbuckley", "password", "kevin", "buckley","kbuckley4@nc.rr.com", "	Kevin Buckley", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("mjacobs", "password", "mike", "jacobs","mjacobs@liquidmachines.com", "Mike Jacobs", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("ngreenwood", "password", "nancy", "greenwood","ncoatesgreenwood@aol.com", "Nancy Greenwood", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("sraitala", "password", "steve", "raitala","raitala@yahoo.com", "Steve Raitala", 0);
insert into users (user_name, user_pass, firstname, lastname, email,friendlyname, balance) values ("jlang","password", "julie", "lang", "julie@dougandjulie.com", "Julie Lang", 0);




