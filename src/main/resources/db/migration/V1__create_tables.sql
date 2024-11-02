-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema devsafio_forohub
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema devsafio_forohub
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `devsafio_forohub` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `devsafio_forohub` ;

-- -----------------------------------------------------
-- Table `devsafio_forohub`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devsafio_forohub`.`usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contrasena` INT NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `devsafio_forohub`.`topico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devsafio_forohub`.`topico` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `fecha_creacion` DATE NULL DEFAULT NULL,
  `mensaje` VARCHAR(255) NULL DEFAULT NULL,
  `status` BIT(1) NOT NULL,
  `titulo` VARCHAR(255) NULL DEFAULT NULL,
  `autor_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKsk04hscorwqdymnafg8882v64` (`autor_id` ASC) VISIBLE,
  CONSTRAINT `FKsk04hscorwqdymnafg8882v64`
    FOREIGN KEY (`autor_id`)
    REFERENCES `devsafio_forohub`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;