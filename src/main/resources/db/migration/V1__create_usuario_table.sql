CREATE TABLE IF NOT EXISTS `devsafio_forohub`.`usuario` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `clave` INT NULL DEFAULT NULL,
    `email` VARCHAR(255) NULL DEFAULT NULL,
    `nombre` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;