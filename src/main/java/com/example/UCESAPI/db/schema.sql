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
            career_name VARCHAR(100) NOT NULL,
            id_statistics int,
            constraint pk_career PRIMARY KEY (id),
            constraint fk_career_statistics FOREIGN KEY (id_statistics) REFERENCES career_statistics(id),
            constraint unq_name UNIQUE (career_name)
);

INSERT INTO careers(career_name, id_statistics)
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
("Susana Guccicone", 9.0),
("German Gianotti", 9.0),
("Pablo Fino", 10.0),
("Veronica Lourdes Tomich", 8.0),
("Sergio Gangur", 8.0),
("Adrian Solimano", 8.0);

CREATE TABLE subject_statistics(
			id int NOT NULL AUTO_INCREMENT,
            hours_per_week float,
            difficulty float,
            constraint pk_subject_statistics PRIMARY KEY (id)
);

INSERT INTO subject_statistics(hours_per_week, difficulty) VALUES 
("Estadística", 9.0),
("Matemática", 9.0),
("Base de datos I", 10.0),
("Veronica Lourdes Tomich", 8.0),
("Sergio Gangur", 8.0),
("Adrian Solimano", 8.0);

CREATE TABLE subject_statistics_professor(
			id int NOT NULL AUTO_INCREMENT,
            id_subject_statistic int NOT NULL,
            id_professor int NOT NULL,
            constraint pk_subject_statistics_professor PRIMARY KEY (id),
            constraint fk_subject_statistics_professor FOREIGN KEY (id_subject_statistic) references subject_statistics(id),
            constraint fk_professor FOREIGN KEY (id_professor) references professors(id)
);
            
CREATE TABLE subjects(
			id int NOT NULL AUTO_INCREMENT,
            `name` varchar(40) NOT NULL,
            id_statistics int,
            id_career int NOT NULL,
            constraint pk_subject PRIMARY KEY (id),
            constraint fk_subject_statistics FOREIGN KEY (id_statistics) references subject_statistics(id),
            constraint fk_career FOREIGN KEY (id_career) references careers(id)
            );
            
INSERT INTO subjects  (subject_name,id_statistics,id_career) VALUES
/*PRIMER CUATRIMESTRE*/
(),
(),
(),
(),
(),

/*SEGUNDO CUATRIMESTRE*/
(),
(),
(),
(),
(),

/*TERCERO CUATRIMESTRE*/
(),
(),
(),
(),
(),

/*CUARTO CUATRIMESTRE*/
(),
(),
(),
(),
(),

/*QUINTO CUATRIMESTRE*/
(),
(),
(),
(),
(),

/*SEXTO CUATRIMESTRE*/
(),
(),
(),
(),
();

CREATE TABLE correlatives(
			id INT NOT NULL AUTO_INCREMENT,
            id_subject INT NOT NULL,
            id_correlative INT NOT NULL,
            constraint pk_correlative PRIMARY KEY (id),
            constraint fk_correlatives_subject FOREIGN KEY (id_subject) references subjects(id),
            constraint fk_correlatives_correlative FOREIGN KEY (id_correlative) references subjects(id),
            constraint check_not_same CHECK (id_subject != id_correlative)
			);
            
CREATE TABLE boards(
			id int NOT NULL AUTO_INCREMENT,
            board_name varchar(30) NOT NULL,
            id_subject INT,
            constraint pk_boards PRIMARY KEY (id),
            constraint fk_subject FOREIGN KEY (id_subject) references subjects(id),
            constraint unq_name_subject UNIQUE (board_name, id_subject)
);

CREATE TABLE users(
			id int NOT NULL AUTO_INCREMENT,
            firstname varchar(30) NOT NULL,
            lastname varchar(30) NOT NULL,
            email varchar(30) NOT NULL,
            u_password varchar(30) NOT NULL,
            user_type int not null default 1,
            constraint pk_user PRIMARY KEY (id),
            constraint unq_email UNIQUE (email)
);
/*
CREATE TABLE forums(
			id int NOT NULL AUTO_INCREMENT,
            forum_type varchar(20) NOT NULL DEFAULT 1,
            body varchar(200) NOT NULL,
            upvotes int DEFAULT 0,
            downvotes int DEFAULT 0,
            id_user int NOT NULL,
            id_board int NOT NULL,
            constraint pk_forum PRIMARY KEY (id),
            constraint fk_user FOREIGN KEY (id_user) references users(id),
            constraint fk_board FOREIGN KEY (id_board) references boards(id)
);
*/
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
            constraint fk_user FOREIGN KEY (id_user) references users(id),
            constraint fk_board FOREIGN KEY (id_board) references boards(id)
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

/*LINEAS PARA EJECUTAR*/
ALTER TABLE careers CHANGE COLUMN career_name career_name varchar(100) NOT NULL;
ALTER TABLE forums CHANGE COLUMN forum_type forum_type INT NOT NULL DEFAULT 1;
ALTER TABLE users CHANGE COLUMN u_password u_password varchar(60);

INSERT INTO career_statistics(duration, accordance) VALUES
(2,8),
(1,9); 
SELECT * FROM career_statistics; 

INSERT INTO careers(career_name,statistics_id) VALUES 
("Tecnicatura Universitaria en Programación",1),
("Tecnicatura Universitaria en Sistemas Informáticos",2);
SELECT * FROM careers; 


INSERT INTO subject_statistics(hours_per_week,difficulty) VALUES
(6.0,9.0),
(4.0,7.0);

INSERT INTO subjects(subject_name, statistics_id, career_id) VALUES
("Estadística",1,1),
("Investigación operativa",2,1);

SELECT * FROM subjects;  


            
