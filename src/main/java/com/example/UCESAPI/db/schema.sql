#DROP DATABASE uces;
CREATE DATABASE uces;
USE uces;

CREATE TABLE career_statistics(
			id int NOT NULL AUTO_INCREMENT,
            duration float,
            accordance int,
            constraint pk_career_statistics PRIMARY KEY (id)
);

CREATE TABLE careers(
			id int NOT NULL AUTO_INCREMENT,
            career_name varchar(40) NOT NULL,
            statistics_id int,
            constraint pk_career PRIMARY KEY (id),
            constraint fk_career_statistics FOREIGN KEY (statistics_id) references career_statistics(id),
            constraint unq_name UNIQUE (career_name)
);
            
CREATE TABLE professors(
			id int NOT NULL AUTO_INCREMENT,
            professor_name varchar(40),
            ratings float,
            constraint pk_professor PRIMARY KEY (id)
);

CREATE TABLE subject_statistics(
			id int NOT NULL AUTO_INCREMENT,
            hours_per_week float,
            difficulty float,
            constraint pk_subject_statistics PRIMARY KEY (id)
);
            
CREATE TABLE subject_statistics_professor(
			id int NOT NULL AUTO_INCREMENT,
            subject_statistic_id int NOT NULL,
            professor_id int NOT NULL,
            constraint pk_subject_statistics_professor PRIMARY KEY (id),
            constraint fk_subject_statistics_professor FOREIGN KEY (subject_statistic_id) references subject_statistics(id),
            constraint fk_professor FOREIGN KEY (professor_id) references professors(id)
);
            
CREATE TABLE subjects(
			id int NOT NULL AUTO_INCREMENT,
            subject_name varchar(40) NOT NULL,
            statistics_id int,
            career_id int NOT NULL,
            constraint pk_subject PRIMARY KEY (id),
            constraint fk_subject_statistics FOREIGN KEY (statistics_id) references subject_statistics(id),
            constraint fk_career FOREIGN KEY (career_id) references careers(id)
            );
            
CREATE TABLE correlatives(
			id int NOT NULL AUTO_INCREMENT,
            subject_id int NOT NULL,
            correlative_id int NOT NULL,
            constraint pk_correlative PRIMARY KEY (id),
            constraint fk_correlatives_subject FOREIGN KEY (subject_id) references subjects(id),
            constraint fk_correlatives_correlative FOREIGN KEY (correlative_id) references subjects(id),
            constraint check_not_same CHECK (subject_id != correlative_id)
			);
            
CREATE TABLE boards(
			id int NOT NULL AUTO_INCREMENT,
            board_name varchar(30) NOT NULL,
            subject_id int,
            constraint pk_boards PRIMARY KEY (id),
            constraint fk_subject FOREIGN KEY (subject_id) references subjects(id),
            constraint unq_name_subject UNIQUE (board_name, subject_id)
);

CREATE TABLE users(
			id int NOT NULL AUTO_INCREMENT,
            firstname varchar(30) NOT NULL,
            lastname varchar(30) NOT NULL,
            email varchar(30) NOT NULL,
            u_password varchar(30) NOT NULL,
            constraint pk_user PRIMARY KEY (id),
            constraint unq_email UNIQUE (email)
);

CREATE TABLE forums(
			id int NOT NULL AUTO_INCREMENT,
            forum_type varchar(20) NOT NULL,
            body varchar(200) NOT NULL,
            upvotes int DEFAULT 0,
            downvotes int DEFAULT 0,
            user_id int NOT NULL,
            board_id int NOT NULL,
            constraint pk_forum PRIMARY KEY (id),
            constraint fk_user FOREIGN KEY (user_id) references users(id),
            constraint fk_board FOREIGN KEY (board_id) references boards(id)
);

CREATE TABLE responses_query(
			id int NOT NULL AUTO_INCREMENT,
            body varchar(200) NOT NULL,
            user_id int NOT NULL,
            forum_id int NOT NULL,
            constraint pk_responses_query PRIMARY KEY (id),
            constraint fk_user_response FOREIGN KEY (user_id) references users(id),
            constraint fk_forum FOREIGN KEY (forum_id) references forums(id)
);

			
            
