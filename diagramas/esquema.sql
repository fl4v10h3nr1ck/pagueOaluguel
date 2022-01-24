-- MySQL Script generated by MySQL Workbench
-- 12/16/14 23:59:49
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema registros_de_softwares
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `registros_de_softwares` ;

-- -----------------------------------------------------
-- Schema registros_de_softwares
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `registros_de_softwares` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `registros_de_softwares` ;

-- -----------------------------------------------------
-- Table `registros_de_softwares`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `registros_de_softwares`.`cliente` ;

CREATE TABLE IF NOT EXISTS `registros_de_softwares`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(150) NOT NULL,
  `cod` VARCHAR(150) NOT NULL,
  `status` ENUM('ATIVO','INATIVO') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `registros_de_softwares`.`permissao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `registros_de_softwares`.`permissao` ;

CREATE TABLE IF NOT EXISTS `registros_de_softwares`.`permissao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dia_inicio` INT NOT NULL,
  `mes_inicio` INT NOT NULL,
  `ano_inicio` INT NOT NULL,
  `cont_licencas` INT NOT NULL,
  `dia_fim` INT NOT NULL,
  `mes_fim` INT NOT NULL,
  `ano_fim` INT NOT NULL,
  `periodo` ENUM('MEN','TRIMEN','SEMEN','ANUAL') NOT NULL,
  `fk_cliente` INT NOT NULL,
  `cod_software` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cliente_idx` (`fk_cliente` ASC))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `registros_de_softwares`.`registros`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `registros_de_softwares`.`registros` ;

CREATE TABLE IF NOT EXISTS `registros_de_softwares`.`registros` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_software` VARCHAR(45) NOT NULL,
  `chave_gerada` LONGTEXT NOT NULL,
  `data_geracao` VARCHAR(20) NOT NULL,
  `fk_permissao` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_permissao_idx` (`fk_permissao` ASC))
ENGINE = MyISAM;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
