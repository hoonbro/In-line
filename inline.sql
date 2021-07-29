-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema inline
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema inline
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `inline` DEFAULT CHARACTER SET utf8 ;
USE `inline` ;

-- -----------------------------------------------------
-- Table `inline`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`office` (
  `office_id` INT NOT NULL AUTO_INCREMENT,
  `office_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`office_id`),
  UNIQUE INDEX `officeName_UNIQUE` (`office_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`department` (
  `dept_id` INT NOT NULL AUTO_INCREMENT,
  `dept_name` VARCHAR(20) NOT NULL,
  `office_id` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`dept_id`),
  UNIQUE INDEX `belong_name_UNIQUE` (`dept_name` ASC) VISIBLE,
  INDEX `belong_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `dept_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`job` (
  `job_id` INT NOT NULL AUTO_INCREMENT,
  `job_name` VARCHAR(20) NOT NULL,
  `office_id` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`job_id`),
  UNIQUE INDEX `position_name_UNIQUE` (`job_name` ASC) VISIBLE,
  INDEX `position_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `position_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `room_name` VARCHAR(20) NOT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`room_id`),
  INDEX `room_fk_userId_idx` (`user_id` ASC) VISIBLE,
  INDEX `room_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `room_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`),
  CONSTRAINT `room_fk_userId`
    FOREIGN KEY (`user_id`)
    REFERENCES `inline`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `profile_image` VARCHAR(255) NULL DEFAULT NULL,
  `nick_name` VARCHAR(20) NULL DEFAULT NULL,
  `phone` VARCHAR(20) NULL DEFAULT NULL,
  `address` VARCHAR(100) NULL DEFAULT NULL,
  `join_date` DATETIME NOT NULL,
  `auth` VARCHAR(20) NOT NULL,
  `login` TINYINT NOT NULL DEFAULT '0',
  `room_id` INT NOT NULL DEFAULT '-1',
  `dept_id` INT NOT NULL,
  `job_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `user_fk_roomId_idx` (`room_id` ASC) INVISIBLE,
  INDEX `user_fk_belongId_idx` (`dept_id` ASC) INVISIBLE,
  INDEX `user_fk_positionId_idx` (`job_id` ASC) INVISIBLE,
  INDEX `user_fk_offiecId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `user_fk_deptId`
    FOREIGN KEY (`dept_id`)
    REFERENCES `inline`.`department` (`dept_id`),
  CONSTRAINT `user_fk_jobId`
    FOREIGN KEY (`job_id`)
    REFERENCES `inline`.`job` (`job_id`),
  CONSTRAINT `user_fk_offiecId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`),
  CONSTRAINT `user_fk_roomId`
    FOREIGN KEY (`room_id`)
    REFERENCES `inline`.`room` (`room_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`calender`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`calender` (
  `calender_id` INT NOT NULL AUTO_INCREMENT,
  `start_day` DATETIME NOT NULL,
  `end_day` DATETIME NOT NULL,
  `title` VARCHAR(20) NOT NULL,
  `notice` TINYINT NOT NULL DEFAULT '0',
  `user_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`calender_id`),
  INDEX `calender_fk_userId_idx` (`user_id` ASC) VISIBLE,
  INDEX `calender_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `calender_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`),
  CONSTRAINT `calender_fk_userId`
    FOREIGN KEY (`user_id`)
    REFERENCES `inline`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`chat` (
  `sender` INT NOT NULL,
  `receiver` INT NOT NULL,
  `send_time` DATETIME NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`sender`, `receiver`),
  INDEX `chat_fk_receiver_idx` (`receiver` ASC) VISIBLE,
  CONSTRAINT `chat_fk_receiver`
    FOREIGN KEY (`receiver`)
    REFERENCES `inline`.`user` (`user_id`),
  CONSTRAINT `chat_fk_sender`
    FOREIGN KEY (`sender`)
    REFERENCES `inline`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`commute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`commute` (
  `commutei_id` INT NOT NULL AUTO_INCREMENT,
  `in` DATETIME NOT NULL,
  `out` DATETIME NULL DEFAULT NULL,
  `day` DATETIME NOT NULL,
  `user_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`commutei_id`),
  INDEX `commute_fk_userId_idx` (`user_id` ASC) VISIBLE,
  INDEX `commute_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `commute_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`),
  CONSTRAINT `commute_fk_userId`
    FOREIGN KEY (`user_id`)
    REFERENCES `inline`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`onboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`onboard` (
  `onboard_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `dept_id` INT NOT NULL,
  `job_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`onboard_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `onBoard_fk_belongId_idx` (`dept_id` ASC) VISIBLE,
  INDEX `onBoard_fk_positionId_idx` (`job_id` ASC) VISIBLE,
  INDEX `onBoard_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `onBoard_fk_deptId`
    FOREIGN KEY (`dept_id`)
    REFERENCES `inline`.`department` (`dept_id`),
  CONSTRAINT `onBoard_fk_jobId`
    FOREIGN KEY (`job_id`)
    REFERENCES `inline`.`job` (`job_id`),
  CONSTRAINT `onBoard_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `inline`.`todo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inline`.`todo` (
  `todo_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` VARCHAR(45) NOT NULL,
  `day` DATETIME NULL DEFAULT NULL,
  `done` TINYINT NOT NULL DEFAULT '0',
  `user_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  PRIMARY KEY (`todo_id`),
  INDEX `todo_fk_userId_idx` (`user_id` ASC) VISIBLE,
  INDEX `todo_fk_officeId_idx` (`office_id` ASC) VISIBLE,
  CONSTRAINT `todo_fk_officeId`
    FOREIGN KEY (`office_id`)
    REFERENCES `inline`.`office` (`office_id`),
  CONSTRAINT `todo_fk_userId`
    FOREIGN KEY (`user_id`)
    REFERENCES `inline`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
