CREATE TABLE `respuesta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contenido_respuesta` varchar(255) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `vista_previa_mensaje` varchar(255) DEFAULT NULL,
  `autor_id` bigint DEFAULT NULL,
  `topico_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86awar7rot4lcb51wfjr7f7eq` (`autor_id`),
  KEY `FKjra4s6fhmvi5bm7y30a42pmv0` (`topico_id`),
  CONSTRAINT `FK86awar7rot4lcb51wfjr7f7eq` FOREIGN KEY (`autor_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKjra4s6fhmvi5bm7y30a42pmv0` FOREIGN KEY (`topico_id`) REFERENCES `topico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci