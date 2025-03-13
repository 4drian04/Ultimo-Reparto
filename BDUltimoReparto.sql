DROP DATABASE IF EXISTS `ultimoreparto`;

CREATE DATABASE `ultimoreparto`;
USE `ultimoreparto`;

-- ultimoreparto.comodin definition

CREATE TABLE `comodin` (
  `idComodin` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `nombreImagen` varchar(100) NOT NULL,
  PRIMARY KEY (`idComodin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ultimoreparto.jugador definition

CREATE TABLE `jugador` (
  `idJugador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contrasenha` varchar(16) NOT NULL,
  PRIMARY KEY (`idJugador`),
  UNIQUE KEY `jugador_unique` (`usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ultimoreparto.maquina definition

CREATE TABLE `maquina` (
  `idMaquina` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `dificultad` varchar(15) NOT NULL,
  PRIMARY KEY (`idMaquina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ultimoreparto.partida definition

CREATE TABLE `partida` (
  `idPartida` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `fecha` date NOT NULL,
  `nombreGanador` varchar(30) DEFAULT NULL,
  `resultado` varchar(10) DEFAULT NULL,
  `idMaquina` int(11) NOT NULL,
  `idJugador` int(11) NOT NULL,
  PRIMARY KEY (`idPartida`),
  KEY `partida_jugador_FK` (`idJugador`),
  KEY `partida_maquina_FK` (`idMaquina`),
  CONSTRAINT `partida_jugador_FK` FOREIGN KEY (`idJugador`) REFERENCES `jugador` (`idJugador`),
  CONSTRAINT `partida_maquina_FK` FOREIGN KEY (`idMaquina`) REFERENCES `maquina` (`idMaquina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO ultimoreparto.maquina
(nombre, dificultad)
VALUES('Máquina_Principiante', 'PRINCIPIANTE');
INSERT INTO ultimoreparto.maquina
(nombre, dificultad)
VALUES('Máquina_Normal', 'NORMAL');
INSERT INTO ultimoreparto.maquina
(nombre, dificultad)
VALUES('Máquina_Difícil', 'DIFÍCIL');
INSERT INTO ultimoreparto.maquina
(nombre, dificultad)
VALUES('Máquina_Loquillo', 'LOQUILLO');

INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Uno mas', 'Obtén un comodín. Además, mientras este comodín esté en la mesa, la apuesta de tu rival tiene un valor de +1.', 'uno_mas.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Turno perfecto', 'Obtén la mejor carta posible del mazo.', 'turno_perfecto.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Devolucion', 'Devuelve la última carta boca arriba que hayas cogido del mazo.', 'devolucion.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Eliminar', 'Devuelve la última carta boca arriba que tu rival haya cogido del mazo.', 'eliminar.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Escudo', 'Mientras este comodín esté en la mesa, el valor de tu apuesta se reduce en -1.', 'escudo.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Destruccion', 'Quita el último comodín de tu rival de la mesa.', 'destruccion.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Intercambio', 'Intercambia la última carta que has cogido con la última carta que ha cogido tu rival.', 'intercambio.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 2', 'Coge la carta número 2. Si ya no está en el mazo, no tendrá ningún efecto.', 'get2.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 3', 'Coge la carta número 3. Si ya no está en el mazo, no tendrá ningún efecto.', 'get3.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 4', 'Coge la carta número 4. Si ya no está en el mazo, no tendrá ningún efecto.', 'get4.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 5', 'Coge la carta número 5. Si ya no está en el mazo, no tendrá ningún efecto.', 'get5.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 6', 'Coge la carta número 6. Si ya no está en el mazo, no tendrá ningún efecto.', 'get6.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('Carta numero 7', 'Coge la carta número 7. Si ya no está en el mazo, no tendrá ningún efecto.', 'get7.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('A por 24', 'Cuando este comodín esté sobre la mesa, el que más se acerque a 24 será el vencedor. Este comodín sustituye a los demás del mismo tipo.', 'por24.jpg');
INSERT INTO ultimoreparto.comodin
(nombre, descripcion, nombreImagen)
VALUES('A por 27', 'Cuando este comodín esté sobre la mesa, el que más se acerque a 27 será el vencedor. Este comodín sustituye a los demás del mismo tipo.', 'por27.jpg');