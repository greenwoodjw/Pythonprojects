CREATE DATABASE IF NOT EXISTS final
;
USE final
;
DROP TABLE IF EXISTS userID
;
DROP TABLE IF EXISTS messages
;

CREATE TABLE userID (
	uid varchar (8) NOT NULL,
	password varchar (50) NOT NULL,
	publicKey blob NOT NULL,
	privateKey blob NOT NULL,
	PRIMARY KEY (uid)
)  TYPE=INNODB
;

CREATE TABLE messages (
	msgID int AUTO_INCREMENT,
	senderid varchar(8) NOT NULL,
	recipientid varchar(8) NOT NULL,
	subject varchar (256) NOT NULL,
	plaintext text NOT NULL,
	ciphertext text NOT NULL,
	INDEX (recipientid),
	Primary key (msgID),
	FOREIGN KEY (senderid) REFERENCES userID(uid)
)  TYPE=INNODB
;

insert into userID (uid, password, publicKey, privateKey) values ("jim", "password","public", "private");
insert into userID (uid, password, publicKey, privateKey) values ("joe", "password","public", "private");
insert into userID (uid, password, publicKey, privateKey) values ("dave","password","public", "private");
insert into userID (uid, password, publicKey, privateKey) values ("ed","password","public", "private");
