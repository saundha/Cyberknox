DROP DATABASE IF EXISTS assignment4;
CREATE DATABASE assignment4;
USE assignment4;


CREATE TABLE `assignment4`.`useraccount` (
  `userAccountId` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NULL,
  `LastName` VARCHAR(45) NULL,
  `dateOfBirth` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `age` VARCHAR(45) NULL,
  `debitCardId` INT NULL,
  `creditCardId` INT NULL,
  PRIMARY KEY (`userAccountId`));



CREATE TABLE `assignment4`.`debitcard` (
  `debitCardId` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NULL,
  `cvv` VARCHAR(45) NULL,
  `expiryDate` VARCHAR(45) NULL,
  PRIMARY KEY (`debitCardId`));



CREATE TABLE `assignment4`.`creditcard` (
  `creditCardId` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NULL,
  `cvv` VARCHAR(45) NULL,
  `expiryDate` VARCHAR(45) NULL,
  PRIMARY KEY (`creditCardId`));



CREATE TABLE `assignment4`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(90) NULL,
  `userAccountId` VARCHAR(45) NULL,
  PRIMARY KEY (`userId`));



CREATE TABLE `assignment4`.`roles` (
  `roleId` INT NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(45) NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE INDEX `roleName_UNIQUE` (`roleName` ASC) VISIBLE);



CREATE TABLE `assignment4`.`userroles` (
  `userRoleId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `roleId` INT NULL,
  PRIMARY KEY (`userRoleId`));



alter table roles
  add constraint roles_UK unique (userId, roleId);

alter table roles
  add constraint roles_FK1 foreign key (userId)
  references user (userId);
 
alter table roles
  add constraint roles_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);



insert into user (username, password)
values ('panesar', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', NULL);
 
insert into user (username, password)
values ('harpreet', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu' NULL);



insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into sec_role (roleName)
values ('ROLE_EMPLOYEE');
 
insert into sec_role (roleName)
values ('ROLE_USER');

 
insert into userroles (userId, roleId)
values (1, 1);
 
insert into userroles (userId, roleId)
values (2, 1);



COMMIT;
