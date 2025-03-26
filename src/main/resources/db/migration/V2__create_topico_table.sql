CREATE TABLE IF NOT EXISTS `devsafio_forohub`.`topico` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `fecha_creacion` DATE NULL DEFAULT NULL,
    `mensaje` VARCHAR(255) NULL DEFAULT NULL,
    `estado` BIT(1) NOT NULL,
    `titulo` VARCHAR(255) NULL DEFAULT NULL,
    `autor_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKsk04hscorwqdymnafg8882v64` (`autor_id` ASC) VISIBLE,
    CONSTRAINT `FKsk04hscorwqdymnafg8882v64`
        FOREIGN KEY (`autor_id`)
        REFERENCES `devsafio_forohub`.`usuario` (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;