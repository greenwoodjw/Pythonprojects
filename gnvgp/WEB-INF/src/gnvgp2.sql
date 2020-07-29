SET FOREIGN_KEY_CHECKS = 0; -- TOP  
SET FOREIGN_KEY_CHECKS = 1;
create table victims(
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
    windate varchar(10) not null,
        INDEX (victimlastname),
        Primary key (vicID),
        FOREIGN KEY (ownerid) REFERENCES users (user_name)
)  TYPE=INNODB;

