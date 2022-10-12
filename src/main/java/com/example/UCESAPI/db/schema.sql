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
            soft_delete boolean default 0,
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

insert into subject_professor (id_subject, id_professor) VALUES
(1, 27),(2, 32),(3, 11),(4, 1),(4, 4),(4, 5),
(4, 8),(5, 4),(5, 5),(5, 8),(6,32),(7,11),(8, 1),
(8, 4),(8, 5),(8, 10),(8, 15), (9, 9), (10, 1),
(10, 4), (10, 5), (10, 15), (11, 2), (11, 27), (12, 11),
(13, 14), (14, 14), (15, 15), (15, 18), (15, 28), (16, 15),
(16,18), (16, 28), (17, 17), (18, 3), (19, 19), (20, 3), (20, 26),
(22, 22), (23, 26), (24, 24), (25, 25), (26, 26), (27, 27), (28, 23),
(29, 29), (30, 23), (31, 31), (32, 23), (33, 27), (34, 24), (35, 33);

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
("juanperez@gmail.com","Juan","Perez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("fran@gmail.com","Fran","Perez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("fancomansilla@gmail.com","Franco","Mansilla","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("thomasraion@gmail.com","Thomas","Raion","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("admin@gmail.com","Admin","Admin","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 2, true);

/*CREATE TABLE queries(
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

INSERT INTO `uces`.`queries`
(`body`,`upvotes`,`downvotes`,`id_user`,`id_board`) VALUES 
("Opinion 1",5,0,1,1),
("Opinion 2",5,0,2,1),
("Opinion 3",7,0,1,1),
("Opinion 4",8,0,2,1),
("Opinion 5",9,0,1,1);

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

INSERT INTO `uces`.`recommendations`
(`body`,`upvotes`,`downvotes`,`id_user`,`id_board`) VALUES 
("Recomendacion 1",5,0,1,1),
("Recomendacion 2",5,0,2,1),
("Recomendacion 3",7,0,1,1),
("Recomendacion 4",8,0,2,1),
("Recomendacion 5",9,0,1,1);*/

CREATE TABLE forums(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(300) NOT NULL,
            id_user int NOT NULL,
            id_board int NOT NULL,
            forum_type int NOT NULL DEFAULT 1,
            constraint pk_forums PRIMARY KEY (id),
            constraint fk_forums_user FOREIGN KEY (id_user) references users(id),
            constraint fk_forums_board FOREIGN KEY (id_board) references boards(id)
);

INSERT INTO `uces`.`forums`
(`body`,`id_user`,`id_board`,`forum_type`) VALUES 
("Consulta 1",1,1,1),
("Consulta 2",2,1,1),
("Consulta 3",2,1,1),
("Consulta 4",1,1,1),
("Consulta 5",2,1,1),
("Recomendacion 1",4,1,2),
("Recomendacion 2",5,1,2),
("Recomendacion 3",1,1,2),
("Recomendacion 4",2,1,2),
("Recomendacion 5",2,1,2);

CREATE TABLE users_voted_forums (
			id_forum int NOT NULL,
            id_user int NOT NULL,
			constraint pk_users_voted_forums PRIMARY KEY (id_forum, id_user),
            constraint fk_users_voted_forums_forums FOREIGN KEY (id_forum) references forums(id),
            constraint fk_users_voted_forums_users FOREIGN KEY (id_user) references users(id)
);


INSERT INTO `uces`.`users_voted_forums`
(`id_forum`, `id_user`) VALUES
(1,1),
(2,1),
(3,1),
(9,1),

(1,2),
(8,2),
(7,2),
(6,2),
(5,2),

(1,4),
(8,4),
(7,4),
(6,4),
(5,4),

(1,5),
(2,5),
(3,5),
(9,5);

CREATE TABLE query_responses(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(200) NOT NULL,
            id_user int NOT NULL,
            id_query int NOT NULL,
            constraint pk_query_response PRIMARY KEY (id),
            constraint fk_query_response_user FOREIGN KEY (id_user) references users(id),
            constraint fk_query_response_query FOREIGN KEY (id_query) references forums(id)
);

INSERT INTO `uces`.`query_responses` (`body`, `id_user`, `id_query`)
VALUES 
("Respuesta 1",2,1), 
("Respuesta 2",1,2), 
("Respuesta 3",2,3), 
("Respuesta 4",1,4);

CREATE TABLE poll_templates(
			id int NOT NULL AUTO_INCREMENT,
            template_name varchar(50),
            title varchar(50),
            poll_description varchar(200),
            constraint pk_poll_template PRIMARY KEY (id)
);

INSERT INTO poll_templates (template_name, title, poll_description) VALUES
			('CAREER_TEMPLATE', 'Encuesta general de la carrera', 'Esta encuesta tiene como fin conocer el opinión de los alumnos en base a su experiencia.'),
			('SUBJECT_TEMPLATE', 'Encuesta de opinión de la materia', 'Esta encuesta tiene como fin conocer el opinión de los alumnos en base a su experiencia.');

CREATE TABLE polls(
			id int NOT NULL AUTO_INCREMENT,
            id_career int,
            id_subject int,
			id_poll_template int NOT NULL,
            constraint pk_poll PRIMARY KEY (id),
            constraint fk_poll_career FOREIGN KEY (id_career) references careers(id),
            constraint fk_poll_subject FOREIGN KEY (id_subject) references subjects(id),
            constraint fk_poll_poll_template FOREIGN KEY (id_poll_template) references poll_templates(id)
);

INSERT INTO polls (id_career, id_subject, id_poll_template) VALUES
			(1, null, 1), (null, 1, 2), (null, 5, 2);

CREATE TABLE polls_x_users(
			id int NOT NULL AUTO_INCREMENT,
            id_user int NOT NULL,
            id_poll int NOT NULL,
			constraint pk_poll_x_user PRIMARY KEY (id),
            constraint fk_pollxuser_user FOREIGN KEY (id_user) references users(id),
            constraint fk_pollxuser_poll FOREIGN KEY (id_poll) references polls(id)
);

CREATE TABLE poll_questions(
			id int NOT NULL AUTO_INCREMENT,
            id_poll_template int NOT NULL,
            question varchar(150) NOT NULL,
            poll_response_type varchar(30) NOT NULL,
            theme varchar(30) NOT NULL,
			constraint pk_poll_question PRIMARY KEY (id),
            constraint fk_poll_question_poll FOREIGN KEY (id_poll_template) references poll_templates(id)
);

INSERT INTO poll_questions(id_poll_template, question, poll_response_type, theme) values
			(1, '¿Con que valor del 1 al 5 calificarías la dificultad de la carrera?', 'RATING_TO_FIVE', 'CAREER_RATING'),
            (1, '¿Qué profesor de la carrera te gustaría destacar?', 'PROFESSOR_RATING', 'CAREER_PROFESSOR_DISTINGUISHED'),
            (1, 'En una respuesta breve, ¿Cúal es tu opinión general sobre la carrera?', 'SHORT_ANSWER', 'CAREER_GENERAL_OPINION'),
            (2, '¿Con qué valor del 1 al 5 calificarías al profesor con el que cursaste la materia?', 'PROFESSOR_RATING', 'PROFESSOR_RATING'),
            (2, '¿Con que valor del 1 al 5 calificarías la dificultad de la materia?', 'RATING_TO_FIVE', 'DIFFICULTY_RATING'),
            (2, '¿Con que valor del 1 al 5 calificarías el valor de lo aprendido en la cursada de la materia?', 'RATING_TO_FIVE', 'LEARNED_VALUE_RATING'),
			(2, '¿Qué cantidad de horas semanales en promedio tuviste que dedicarle a la materia fuera de las clases?', 'RATING_TO_FIVE', 'SUBJECT_EXTRA_HOURS');
    
CREATE TABLE poll_responses(
			id int NOT NULL AUTO_INCREMENT,
            id_poll_question int NOT NULL,
            id_professor int,
            rating int,
            short_answer varchar(200),
			constraint pk_poll_responses PRIMARY KEY (id),
            constraint fk_poll_responses_question FOREIGN KEY (id_poll_question) references poll_questions(id),
			constraint fk_poll_response_professor FOREIGN KEY (id_professor) references professors(id)
);