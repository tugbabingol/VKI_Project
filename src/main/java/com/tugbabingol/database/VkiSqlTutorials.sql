

-- Create Database
CREATE SCHEMA `vki` DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci ;

-- Set
use vki;

-- Table
CREATE TABLE `vki`.`register` (
                                  `id` INT NOT NULL AUTO_INCREMENT,
                                  `name` VARCHAR(255) NULL DEFAULT 'adınızı yazmadınız.',
                                  `surname` VARCHAR(255) NULL DEFAULT 'soyadınızı yazmadınız.',
                                  `email_address` VARCHAR(255) NULL DEFAULT 'email adres yazmadınız',
                                  `password` VARCHAR(255) NULL DEFAULT 'şifrenizi girmediniz',
                                  `roles` VARCHAR(10) NULL DEFAULT 'user',
                                  `remaining_number` INT(2) NULL DEFAULT '4',
                                  `is_passive` VARCHAR(2) NULL DEFAULT '0',
                                  PRIMARY KEY (`id`))
    ENGINE = InnoDB;
CREATE TABLE `vki`.`data`(
                             `vki_id` INT NOT NULL AUTO_INCREMENT ,
                             `weight` FLOAT NULL DEFAULT '0',
                             `height` FLOAT NULL DEFAULT '0',
                             `vki_value` FLOAT NULL DEFAULT '0',
                             PRIMARY KEY (`vki_id`)
)
    ENGINE = InnoDB;
