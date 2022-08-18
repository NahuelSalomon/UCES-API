#DROP DATABASE uces;
CREATE DATABASE uces;
USE uces;

CREATE TABLE career_statistics(
			id int NOT NULL AUTO_INCREMENT,
            duration float,
            accordance int,
            constraint pk_career_statistics PRIMARY KEY (id)
);

INSERT INTO career_statistics(duration, accordance) 
VALUES 
(2.0,8),
(3.0,9);

CREATE TABLE careers(
			id int NOT NULL AUTO_INCREMENT,
            `name` VARCHAR(100) NOT NULL,
            id_statistics int,
            constraint pk_career PRIMARY KEY (id),
            constraint fk_career_statistics FOREIGN KEY (id_statistics) REFERENCES career_statistics(id),
            constraint unq_name UNIQUE (`name`)
);

INSERT INTO careers(`name`, id_statistics)
VALUES 
("Tecnicatura universitaria en Programación", 1),
("Tecnicatura universitaria en Sistemas Informáticos", 2);

CREATE TABLE professors(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(40),
            ratings float,
            constraint pk_professor PRIMARY KEY (id)
);

INSERT INTO professors(`name`, ratings) VALUES 
("Carolina Archuby", 0.0),
("Mariela Bergamin", 0.0),
("Veronica Lourdes Tomich", 0.0),
("Sergio Garguir", 0.0),
("Emmanuel Etcheber", 0.0),

("Mariela Bergamin", 0.0),
("Rodrigo Fanjul", 0.0),
("Gabriel Chaldu", 0.0),
("Rodolfo Germán Barrios", 0.0),
("Gustavo A. F. Sonvico", 0.0),
("Eduardo Mónaco", 0.0),

("Mario De Paolo", 0.0),
("Maria Teresa De Tomaso", 0.0),
("Roberto Cesar Lucero", 0.0),
("Gonzalo Benoffi", 0.0),
("Karina Felice", 0.0),

("Federico Gil de Muro", 0.0),
("Cristian Halm", 0.0),
("Jorge Adrian Solimano", 0.0),
("Julieta Wilson", 0.0),
("Matías Tesoreiro", 0.0),

("Leonardo Chiessa", 0.0),
("German Gianotti", 0.0),
("Rocio Acosta", 0.0),
("Fernando Castañeda", 0.0),
("Pablo Ezequiel Fino", 0.0),
("Susana Guccione", 0.0),
("Gonzalo Orellano", 0.0),

("Adolfo Eduardo Onaine", 0.0),
("Ignacio Casales", 0.0),
("Juan Jose Azar", 0.0),
/*("German Gianotti", 0.0),*/
/*("Susana Guccione", 0.0),*/
("Carolina Tosoni", 0.0),
("Nicolás Bertolucci", 0.0);


CREATE TABLE subject_statistics(
			id int NOT NULL AUTO_INCREMENT,
            hours_per_week float,
            difficulty float,
            constraint pk_subject_statistics PRIMARY KEY (id)
);

INSERT INTO subject_statistics(hours_per_week, difficulty) VALUES 
/* PRIMER CUATRIMESTRE */
/*Matemática*/ (9.0, 0.0),
/*Inglés I*/ (3.0, 0.0),
/*Sistema de Procesamiento de Datos*/ (6.0, 0.0),
/*Laboratorio de Computación I*/ (6.0, 0.0),
/*Programación I*/ (6.0, 0.0),

/*SEGUNDO CUATRIMESTRE*/
/*Inglés II*/ (3.0, 0.0),
/*Arquitectura y Sistemas Operativos*/ (6.0, 0.0),
/*Programación II*/ (6.0, 0.0),
/*Metodología de la Investigación*/ (3.0, 0.0),
/*Laboratorio de Computación II*/ (6.0, 0.0),
/*Estadística*/ (6.0, 9.0),

/*TERCER CUATRIMESTRE*/
/*Elementos de Investigación Operativa*/ (6.0, 0.0),
/*Organización Contable de la Empresa*/ (6.0, 0.0),
/*Organización Empresarial*/ (6.0, 0.0),
/*Laboratorio de Computación III*/ (6.0, 0.0),
/*Programación III*/ (6.0, 0.0),

/*CUARTO CUATRIMESTRE*/
/*Legislación*/ (6.0, 0.0),
/*Metodología de Sistemas I*/ (12.0, 0.0),
/*Laboratorio de Computación IV*/ (6.0, 0.0),
/*Diseño y Administración de Bases de Datos*/ (6.0, 0.0),
/*Práctica Profesional*/ (0.0, 0.0),

/*QUINTO CUATRIMESTRE*/
/*Redes*/ (4.0, 0.0),
/*Programación Avanzada I	*/ (4.0, 0.0),
/*Ingles Técnico Avanzado I	*/ (3.0, 0.0),
/*Metodología De Sistemas II	*/ (4.0, 0.0),
/*Base de Datos II	*/ (4.0, 0.0),
/*Matemática II	*/ (4.0, 0.0),
/*Laboratorio V	*/ (4.0, 0.0),

/*SEXTO CUATRIMESTRE*/
/*Investigación Operativa II*/ (4.0, 0.0),
/*Administración y Dirección de Proyectos Informáticos	*/ (4.0, 0.0),
/*Programación Avanzada II*/ (4.0, 0.0),
/*Seminario*/ (4.0, 0.0),
/*Matemática III*/ (4.0, 0.0),
/*Ingles Técnico Avanzado II*/ (2.0, 0.0),
/*Metodología de Sistemas III*/ (4.0, 0.0);

CREATE TABLE subjects(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(60) NOT NULL,
            id_statistics int,
            id_career int NOT NULL,
            `code` varchar(30),
            constraint pk_subject PRIMARY KEY (id),
            constraint fk_subject_statistics FOREIGN KEY (id_statistics) references subject_statistics(id),
            constraint fk_career FOREIGN KEY (id_career) references careers(id)
            );
            
INSERT INTO subjects (`code`,`name`,id_statistics,id_career) VALUES
/*PRIMER CUATRIMESTRE*/
("95-1123","Matemática I",1,1),
("95-1111","Inglés I",2,1),
("95-1122","Sistema de Procesamiento de Datos",3,1),
("95-1124","Laboratorio de Computación I",4,1),
("95-1121","Programación I",5,1),

/*SEGUNDO CUATRIMESTRE*/
("95-1112","Inglés II",6,1),
("95-1126","Arquitectura y Sistemas Operativos",7,1),
("95-1125","Programación II",8,1),
("95-1128","Metodología de la Investigación",9,1),
("95-1127","Laboratorio de Computación II",10,1),
("95-1104","Estadística",11,1),

/*TERCER CUATRIMESTRE*/
("95-1233","Elementos de Investigación Operativa",12,1),
("95-1231","Organización Contable de la Empresa",13,1),
("95-1232","Organización Empresarial",14,1),
("95-1234","Laboratorio de Computación III",15,1),
("95-1230","Programación III",16,1),

/*CUARTO CUATRIMESTRE*/
("95-1209","Legislación",17,1),
("95-1235","Metodología de Sistemas I",18,1),
("95-1237", "Laboratorio de Computación IV",19,1),
("95-1236","Diseño y Administración de Bases de Datos",20,1),
(null, "Práctica Profesional",21,1),

/*QUINTO CUATRIMESTRE*/
(null, "Redes",22,2),
(null, "Programación Avanzada I",23,2),
(null, "Ingles Técnico Avanzado I",24,2),
(null, "Metodología De Sistemas II",25,2),
(null, "Base de Datos II",26,2),
(null, "Matemática II",27,2),
(null, "Laboratorio V",28,2),

/*SEXTO CUATRIMESTRE*/
(null, "Investigación Operativa II",29,2),
(null, "Administración y Dirección de Proyectos Informáticos",30,2),
(null, "Programación Avanzada II",31,2),
(null, "Seminario",32,2),
(null, "Matemática III",33,2),
(null, "Ingles Técnico Avanzado II",34,2),
(null, "Metodología de Sistemas III",35,2);

CREATE TABLE subject_professor(
			id int NOT NULL AUTO_INCREMENT,
            id_subject int NOT NULL,
            id_professor int NOT NULL,
            constraint pk_subject_professor PRIMARY KEY (id),
            constraint fk_subject_professor FOREIGN KEY (id_subject) references subjects(id),
            constraint fk_professor FOREIGN KEY (id_professor) references professors(id)
);

CREATE TABLE correlatives(
			id INT NOT NULL AUTO_INCREMENT,
            id_subject INT NOT NULL,
            id_correlative INT NOT NULL,
            constraint pk_correlative PRIMARY KEY (id),
            constraint fk_correlatives_subject FOREIGN KEY (id_subject) references subjects(id),
            constraint fk_correlatives_correlative FOREIGN KEY (id_correlative) references subjects(id),
            constraint check_not_same CHECK (id_subject != id_correlative)
			);

INSERT INTO correlatives (id_subject,id_correlative) VALUES 
/* PRIMER CUATRIMESTRE */
/*Matemática NO TIENE CORRELATIVAS*/
/*Inglés I NO TIENE CORRELATIVAS*/
/*Sistema de Procesamiento de Datos NO TIENE CORRELATIVAS*/
/*Laboratorio de Computación I NO TIENE CORRELATIVAS*/
/*Programación I NO TIENE CORRELATIVAS*/

/*SEGUNDO CUATRIMESTRE*/
/*Inglés II*/ (6, 2),
/*Arquitectura y Sistemas Operativos*/ (7, 3),
/*Programación II*/ (8, 4),(8, 5),
/*Metodología de la Investigación NO TIENE CORRELATIVAS*/ 
/*Laboratorio de Computación II*/ (10, 4),(10, 5),
/*Estadística*/ (11, 1),

/*TERCER CUATRIMESTRE*/
/*Elementos de Investigación Operativa*/ (12, 11),
/*Organización Contable de la Empresa*/ (13, 1),
/*Organización Empresarial*/ (14, 11),
/*Laboratorio de Computación III*/ (15, 8),(15, 10),
/*Programación III*/ (16, 8),(16, 10),

/*CUARTO CUATRIMESTRE*/
/*Legislación NO TIENE CORRELATIVAS*/
/*Metodología de Sistemas I*/ (18, 9),(18, 16),(18, 13),(18, 14),(18, 15),
/*Laboratorio de Computación IV*/ (19, 15), (19, 16),
/*Diseño y Administración de Bases de Datos*/ (20, 15),(20, 16), 
/*Práctica Profesional NO TIENE CORRELATIVAS*/ 

/*QUINTO CUATRIMESTRE*/
/*Redes NO TIENE CORRELATIVAS*/
/*Programación Avanzada I NO TIENE CORRELATIVAS*/ 
/*Ingles Técnico Avanzado I NO TIENE CORRELATIVAS*/ 
/*Metodología De Sistemas II NO TIENE CORRELATIVAS*/ 
/*Base de Datos II	NO TIENE CORRELATIVAS*/ 
/*Matemática II	NO TIENE CORRELATIVAS*/ 
/*Laboratorio V	NO TIENE CORRELATIVAS*/ 

/*SEXTO CUATRIMESTRE*/
/*Investigación Operativa II NO TIENE CORRELATIVAS*/ 
/*Administración y Dirección de Proyectos Informáticos NO TIENE CORRELATIVAS*/ 
/*Programación Avanzada II*/ (31, 28),
/*Seminario NO TIENE CORRELATIVAS*/ 
/*Matemática III*/ (33, 27),
/*Ingles Técnico Avanzado II*/ (34, 24),
/*Metodología de Sistemas III*/ (35, 25);

CREATE TABLE boards(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(60) NOT NULL,
            id_subject INT,
            constraint pk_boards PRIMARY KEY (id),
            constraint fk_subject FOREIGN KEY (id_subject) references subjects(id),
            constraint unq_name_subject UNIQUE (`name`, id_subject)
);

INSERT INTO boards(`name`,id_subject) VALUES 
/*Matemática*/ ("Matemática 1", 1),
/*Inglés I*/ ("Inglés I", 2),
/*Sistema de Procesamiento de Datos*/ ("Sistema de Procesamiento de Datos", 3),
/*Laboratorio de Computación I*/ ("Laboratorio de Computación I", 4),
/*Programación I*/ ("Programación I", 5),

/*SEGUNDO CUATRIMESTRE*/
/*Inglés II*/ ("Inglés II", 6),
/*Arquitectura y Sistemas Operativos*/ ("Arquitectura y Sistemas Operativos", 7),
/*Programación II*/ ("Programación II", 8),
/*Metodología de la Investigación*/ ("Metodología de la Investigación", 10),
/*Laboratorio de Computación II*/ ("Laboratorio de Computación II", 10),
/*Estadística*/ ("Estadística", 11),

/*TERCER CUATRIMESTRE*/
/*Elementos de Investigación Operativa*/ ("Elementos de Investigación Operativa", 12),
/*Organización Contable de la Empresa*/ ("Organización Contable de la Empresa", 13),
/*Organización Empresarial*/ ("Organización Empresarial", 14),
/*Laboratorio de Computación III*/ ("Laboratorio de Computación III", 15),
/*Programación III*/ ("Programación III", 16),

/*CUARTO CUATRIMESTRE*/
/*Legislación*/ ("Legislación", 17),
/*Metodología de Sistemas I*/ ("Metodología de Sistemas I", 18),
/*Laboratorio de Computación IV*/ ("Laboratorio de Computación IV", 19),
/*Diseño y Administración de Bases de Datos*/ ("Diseño y Administración de Bases de Datos", 20),
/*Práctica Profesional*/  ("Práctica Profesional", 21),

/*QUINTO CUATRIMESTRE*/
/*Redes*/  ("Redes", 22),
/*Programación Avanzada I*/  ("Programación Avanzada I", 23), 
/*Ingles Técnico Avanzado I*/  ("Ingles Técnico Avanzado I", 24), 
/*Metodología De Sistemas II*/  ("Metodología De Sistemas II", 25), 
/*Base de Datos II*/  ("Base de Datos II", 26), 
/*Matemática II*/  ("Matemática II", 27), 
/*Laboratorio V*/  ("Laboratorio V", 28), 

/*SEXTO CUATRIMESTRE*/
/*Investigación Operativa II*/  ("Investigación Operativa II", 29), 
/*Administración y Dirección de Proyectos Informáticos*/  ("Administración y Dirección de Proyectos Informáticos", 30),
/*Programación Avanzada II*/ ("Programación Avanzada II", 31),
/*Seminario*/ ("Seminario", 32),
/*Matemática III*/ ("Matemática III", 33),
/*Ingles Técnico Avanzado II*/ ("Ingles Técnico Avanzado II", 34),
/*Metodología de Sistemas III*/ ("Metodología de Sistemas III", 35);

CREATE TABLE users(
			id int NOT NULL AUTO_INCREMENT,
            firstname varchar(30) NOT NULL,
            lastname varchar(30) NOT NULL,
            email varchar(30) NOT NULL,
            u_password varchar(60) NOT NULL,
            `active` bool NOT NULL DEFAULT TRUE, 
            user_type int not null default 1,
            constraint pk_user PRIMARY KEY (id),
            constraint unq_email UNIQUE (email)
);
ALTER TABLE users ADD COLUMN `confirmed_email` BOOL NOT NULL DEFAULT FALSE;

INSERT INTO users (email,firstname,lastname,u_password, user_type, confirmed_email) VALUES
("nahuel@gmail.com","Nahuel","Salomon","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("noelia@gmail.com","Noelia","Benitez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("admin@gmail.com","Admin","Admin","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 2, true);

CREATE TABLE queries(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(200) NOT NULL,
            upvotes int DEFAULT 0,
            downvotes int DEFAULT 0,
            id_user int NOT NULL,
            id_board int NOT NULL,  
            constraint pk_query PRIMARY KEY (id),
            constraint fk_user FOREIGN KEY (id_user) references users(id),
            constraint fk_board FOREIGN KEY (id_board) references boards(id)
);

CREATE TABLE recommendations(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(200) NOT NULL,
            upvotes int DEFAULT 0,
            downvotes int DEFAULT 0,
            id_user int NOT NULL,
            id_board int NOT NULL,
            constraint pk_recommendation PRIMARY KEY (id),
            constraint fk_recommendations_user FOREIGN KEY (id_user) references users(id),
            constraint fk_recommendations_board FOREIGN KEY (id_board) references boards(id)
);

CREATE TABLE query_responses(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(200) NOT NULL,
            id_user int NOT NULL,
            id_query int NOT NULL,
            constraint pk_query_response PRIMARY KEY (id),
            constraint fk_query_response_user FOREIGN KEY (id_user) references users(id),
            constraint fk_query_response_query FOREIGN KEY (id_query) references queries(id)
);


