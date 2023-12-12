#DROP DATABASE uces;
CREATE DATABASE uces;
USE uces;

CREATE TABLE careers(
			id int NOT NULL AUTO_INCREMENT,
            `name` VARCHAR(250) NOT NULL,
            `description` VARCHAR(250) NOT NULL,
            duration int not null,
            constraint pk_career PRIMARY KEY (id),
            constraint unq_name UNIQUE (`name`)
);

INSERT INTO careers(`name`,`description`, duration )
VALUES 
("Tecnicatura universitaria en Programación", "Formación sólida en desarrollo de software, lenguajes de programación y resolución de problemas. Preparación para ser programador o desarrollador de software.", 2),
("Tecnicatura universitaria en Sistemas Informáticos", "Gestión de sistemas informáticos, redes, seguridad y soporte técnico. Ideal para ser técnico de sistemas, administrador de redes o experto en TI.", 1);

CREATE TABLE subjects(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(60) NOT NULL,
            career_id int NOT NULL,
            constraint pk_subject PRIMARY KEY (id),
            constraint fk_career FOREIGN KEY (career_id) references careers(id) ON DELETE CASCADE ON UPDATE CASCADE
            );
            
INSERT INTO subjects (`name`,career_id) VALUES
/*PRIMER CUATRIMESTRE*/
("Matemática I",1),
("Inglés I",1),
("Sistema de Procesamiento de Datos",1),
("Laboratorio de Computación I",1),
("Programación I",1),

/*SEGUNDO CUATRIMESTRE*/
("Inglés II",1),
("Arquitectura y Sistemas Operativos",1),
("Programación II",1),
("Metodología de la Investigación",1),
("Laboratorio de Computación II",1),
("Estadística",1),

/*TERCER CUATRIMESTRE*/
("Elementos de Investigación Operativa",1),
("Organización Contable de la Empresa",1),
("Organización Empresarial",1),
("Laboratorio de Computación III",1),
("Programación III",1),

/*CUARTO CUATRIMESTRE*/
("Legislación",1),
("Metodología de Sistemas I",1),
("Laboratorio de Computación IV",1),
("Diseño y Administración de Bases de Datos",1),
("Práctica Profesional",1),

/*QUINTO CUATRIMESTRE*/
("Redes",2),
("Programación Avanzada I",2),
("Ingles Técnico Avanzado I",2),
("Metodología De Sistemas II",2),
("Base de Datos II",2),
("Matemática II",2),
("Laboratorio V",2),

/*SEXTO CUATRIMESTRE*/
("Investigación Operativa II",2),
("Administración y Dirección de Proyectos Informáticos",2),
("Programación Avanzada II",2),
("Seminario",2),
("Matemática III",2),
("Ingles Técnico Avanzado II",2),
("Metodología de Sistemas III",2);


#INSERT INTO correlatives (id_subject,id_correlative) VALUES 
/* PRIMER CUATRIMESTRE */
/*Matemática NO TIENE CORRELATIVAS*/
/*Inglés I NO TIENE CORRELATIVAS*/
/*Sistema de Procesamiento de Datos NO TIENE CORRELATIVAS*/
/*Laboratorio de Computación I NO TIENE CORRELATIVAS*/
/*Programación I NO TIENE CORRELATIVAS*/

/*SEGUNDO CUATRIMESTRE*/
#/*Inglés II*/ (6, 2),
#/*Arquitectura y Sistemas Operativos*/ (7, 3),
#/*Programación II*/ (8, 4),(8, 5),
#/*Metodología de la Investigación NO TIENE CORRELATIVAS*/ 
#/*Laboratorio de Computación II*/ (10, 4),(10, 5),
#/*Estadística*/ (11, 1),

/*TERCER CUATRIMESTRE*/
#/*Elementos de Investigación Operativa*/ (12, 11),
#/*Organización Contable de la Empresa*/ (13, 1),
#/*Organización Empresarial*/ (14, 11),
#/*Laboratorio de Computación III*/ (15, 8),(15, 10),
#/*Programación III*/ (16, 8),(16, 10),

/*CUARTO CUATRIMESTRE*/
/*Legislación NO TIENE CORRELATIVAS*/
#/*Metodología de Sistemas I*/ (18, 9),(18, 16),(18, 13),(18, 14),(18, 15),
#/*Laboratorio de Computación IV*/ (19, 15), (19, 16),
#/*Diseño y Administración de Bases de Datos*/ (20, 15),(20, 16), 
#/*Práctica Profesional NO TIENE CORRELATIVAS*/ 

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
#/*Programación Avanzada II*/ (31, 28),
#/*Seminario NO TIENE CORRELATIVAS*/ 
#/*Matemática III*/ (33, 27),
#/*Ingles Técnico Avanzado II*/ (34, 24),
#/*Metodología de Sistemas III*/ (35, 25);

CREATE TABLE boards(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(60) NOT NULL,
            subject_id INT,
            constraint pk_boards PRIMARY KEY (id),
            constraint fk_subject FOREIGN KEY (subject_id) references subjects(id) ON DELETE CASCADE ON UPDATE CASCADE,
            constraint unq_name_subject UNIQUE (`name`, subject_id)
);

INSERT INTO boards(`name`,subject_id) VALUES 
/*Matemática*/ ("Matemática 1", 1),
/*Inglés I*/ ("Inglés I", 2),
/*Sistema de Procesamiento de Datos*/ ("Sistema de Procesamiento de Datos", 3),
/*Laboratorio de Computación I*/ ("Laboratorio de Computación I", 4),
/*Programación I*/ ("Programación I", 5),

/*SEGUNDO CUATRIMESTRE*/
/*Inglés II*/ ("Inglés II", 6),
/*Arquitectura y Sistemas Operativos*/ ("Arquitectura y Sistemas Operativos", 7),
/*Programación II*/ ("Programación II", 8),
/*Metodología de la Investigación*/ ("Metodología de la Investigación", 9),
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
            confirmed_email BOOL NOT NULL DEFAULT FALSE,
            user_type int not null default 1,
            image mediumblob,
            constraint pk_user PRIMARY KEY (id),
            constraint unq_email UNIQUE (email)
);

INSERT INTO users (email,firstname,lastname,u_password, user_type, confirmed_email) VALUES
("nahuel@gmail.com","Nahuel","Salomon","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("noelia@gmail.com","Noelia","Benitez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("juanperez@gmail.com","Juan","Perez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("fran@gmail.com","Fran","Perez","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("fancomansilla@gmail.com","Franco","Mansilla","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("thomasraion@gmail.com","Thomas","Raion","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 1, true),
("admin@gmail.com","Admin","Admin","$2a$10$ELh6pkJSR4z9NfPc5Z1PGeKnVZgYJn5QvcbqWBv/ZuffgAOV8Veu6"/*123*/, 2, true);



CREATE TABLE forums(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(500) NOT NULL,
            `date` datetime NOT NULL DEFAULT NOW(),
            user_id int NOT NULL,
            board_id int NOT NULL,
            forum_type int NOT NULL DEFAULT 1,
            constraint pk_forums PRIMARY KEY (id),
            constraint fk_forums_user FOREIGN KEY (user_id) references users(id) ON DELETE CASCADE ON UPDATE CASCADE,
            constraint fk_forums_board FOREIGN KEY (board_id) references boards(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO forums
(body,`date`,user_id,board_id,forum_type) VALUES 
("Consulta 1",DATE_ADD(NOW(),INTERVAL -2 MONTH),1,1,1),
("Consulta 2",DATE_ADD(NOW(),INTERVAL -1 MONTH),2,1,1),
("Consulta 3",DATE_ADD(NOW(),INTERVAL -3 MONTH),2,1,1),
("Consulta 4",DATE_ADD(NOW(),INTERVAL -6 MONTH),1,1,1),
("Consulta 5",DATE_ADD(NOW(),INTERVAL -5 MONTH),2,1,1),
("Recomendacion 1",DATE_ADD(NOW(),INTERVAL -11 MONTH),4,1,2),
("Recomendacion 2",DATE_ADD(NOW(),INTERVAL -7 MONTH),5,1,2),
("Recomendacion 3",DATE_ADD(NOW(),INTERVAL -10 MONTH),1,1,2),
("Recomendacion 4",DATE_ADD(NOW(),INTERVAL -9 MONTH),2,1,2),
("Recomendacion 5",DATE_ADD(NOW(),INTERVAL -10 MONTH),2,1,2);

CREATE TABLE users_voted_forums (
			forum_id int NOT NULL,
            user_id int NOT NULL,
			constraint pk_users_voted_forums PRIMARY KEY (forum_id, user_id),
            constraint fk_users_voted_forums_forums FOREIGN KEY (forum_id) references forums(id),
            constraint fk_users_voted_forums_users FOREIGN KEY (user_id) references users(id)
);

INSERT INTO users_voted_forums
(forum_id, user_id) VALUES
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
            user_id int NOT NULL,
            query_id int NOT NULL,
            constraint pk_query_response PRIMARY KEY (id),
            constraint fk_query_response_user FOREIGN KEY (user_id) references users(id),
            constraint fk_query_response_query FOREIGN KEY (query_id) references forums(id)
);

INSERT INTO `uces`.`query_responses` (`body`, `user_id`, `query_id`)
VALUES 
("Respuesta 1",2,1), 
("Respuesta 2",1,2), 
("Respuesta 3",2,3), 
("Respuesta 4",1,4);

CREATE TABLE polls(
			id int NOT NULL AUTO_INCREMENT,
            poll_type int,
            career_id INT,
            subject_id INT,
            constraint pk_poll PRIMARY KEY (id),
            constraint fk_poll_career FOREIGN KEY (career_id) references careers(id),
            constraint fk_poll_subject FOREIGN KEY (subject_id) references subjects(id)
);

INSERT INTO polls (poll_type, career_id, subject_id) VALUES
			(0, 1, null), (1, NULL, 1);

CREATE TABLE poll_results(
			id int NOT NULL AUTO_INCREMENT,   
            poll_id int NOT NULL,
            student_user_id int NOT NULL,
			constraint pk_poll_results PRIMARY KEY (id),
            constraint fk_poll_results_user FOREIGN KEY (poll_id) references polls(id) ON DELETE CASCADE ON UPDATE CASCADE,
            constraint fk_poll_results_poll FOREIGN KEY (student_user_id) references users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE poll_questions(
			id int NOT NULL AUTO_INCREMENT,
            poll_id int NOT NULL,
            question varchar(150) NOT NULL,
            short_description varchar(30) NOT NULL,
            poll_response_type varchar(100) NOT NULL,
			constraint pk_poll_question PRIMARY KEY (id),
            constraint fk_poll_question_poll FOREIGN KEY (poll_id) references polls(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE poll_answers(
			id int NOT NULL AUTO_INCREMENT,
            poll_question_id int NOT NULL,
            poll_result_id int NOT NULL,
            bool_response bool,
            rank_response int,
			constraint pk_poll_answers PRIMARY KEY (id),
            constraint fk_poll_answers_poll_question FOREIGN KEY (poll_question_id) references poll_questions(id) ON DELETE CASCADE ON UPDATE CASCADE,
            constraint fk_poll_answers_poll_result FOREIGN KEY (poll_result_id) references poll_results(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `uces`.`poll_results`
(`poll_id`,
`student_user_id`)
VALUES
(1,1),
(2,1),

(1,2),
(2,2),

(1,3),
(2,3);

INSERT INTO poll_questions(poll_id, question, short_description, poll_response_type) values
			(1,'¿Con que valor del 1 al 5 calificarías la dificultad de la carrera?',"Dificultad", 'RATING_TO_FIVE'),
            (1,'¿Estas conforme con la carrera la carrera?', "Conformidad" , 'YES_NO_ANSWER'),
            (2,'¿Con que valor del 1 al 5 calificarías la dificultad de la materia?', "Dificultad", 'RATING_TO_FIVE'),
            (2,'¿Con que valor del 1 al 5 calificarías el valor de lo aprendido en la cursada de la materia?', "Valor de lo aprendido", 'RATING_TO_FIVE'),
			(2,'¿Se cumplieron con todos los contenidos de la materia?', "Cumplimiento de contenidos" , 'YES_NO_ANSWER');

INSERT INTO poll_answers
(`poll_question_id`,
`poll_result_id`,
`bool_response`,
`rank_response`)
VALUES
(1,1,null, 4), #RATING_TO_FIVE
(2,1,1, null), #YES_NO_ANSWER
(3,2,null, 5), #RATING_TO_FIVE
(4,2,null, 3), #RATING_TO_FIVE
(5,2,0, null), #YES_NO_ANSWER

(1,1,null, 4), #RATING_TO_FIVE
(2,1,0, null), #YES_NO_ANSWER
(3,2,null, 5), #RATING_TO_FIVE
(4,2,null, 3), #RATING_TO_FIVE
(5,2,1, null), #YES_NO_ANSWER

(1,1,null, 4), #RATING_TO_FIVE
(2,1,1, null), #YES_NO_ANSWER
(3,2,null, 5), #RATING_TO_FIVE
(4,2,null, 3), #RATING_TO_FIVE
(5,2,0, null); #YES_NO_ANSWER


CREATE TABLE poll_question_statistics(
			id int NOT NULL AUTO_INCREMENT,
            poll_question_id int NOT NULL,
			number_of_positive_response FLOAT DEFAULT 0,
			number_of_negative_response FLOAT DEFAULT 0,
			total_range_response FLOAT DEFAULT 0,
			number_of_responses INT DEFAULT 0,
			constraint pk_poll_answers PRIMARY KEY (id),
            constraint fk_poll_question_statistics_poll_question FOREIGN KEY (poll_question_id) references poll_questions(id) on delete cascade on update cascade
);

INSERT INTO poll_question_statistics
(`poll_question_id`,
`number_of_positive_response`,
`number_of_negative_response`,
`total_range_response`,
`number_of_responses`)
VALUES
(1,0, 0, 12, 3), #RATING_TO_FIVE
(2,2, 1, 0, 3), #YES_NO_ANSWER
(3,0, 0, 15, 3), #RATING_TO_FIVE
(4,0, 0, 9, 3), #RATING_TO_FIVE
(5,1, 2, 0, 3); #YES_NO_ANSWER

use uces;
select * from poll_answers;
select * from poll_results;
select * from poll_questions;
#delete from poll_results where id <> 9999;
#delete from poll_answers where id <> 9999;