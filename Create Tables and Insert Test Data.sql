USE Dummy323;



CREATE TABLE IF NOT EXISTS project(
	projectCode	INT(11)	NOT NULL,
	startDate	DATE		NOT NULL,
	deadline		DATE		NOT NULL,
    	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(projectCode)
);

CREATE TABLE IF NOT EXISTS zipCode (
	zip INT(12) 	NOT NULL,
	city	VARCHAR(30)	NOT NULL,
	st	 	VARCHAR(30)	NOT NULL,
	PRIMARY KEY(zip)
);

CREATE TABLE IF NOT EXISTS employee (
	employeeID	INT(12)		NOT NULL,
	firstName	VARCHAR(30)	NOT NULL,
	lastName	VARCHAR(30)	NOT NULL,
	title		VARCHAR(30)	NOT NULL,
	address		VARCHAR(30)	NOT NULL,
	zip		int(12)		NOT NULL,
	PRIMARY KEY(employeeID),
	FOREIGN KEY(zip) REFERENCES zipCode(zip)
);

CREATE TABLE IF NOT EXISTS scrum_team(
	teamNum		INT(12)	NOT NULL,
	teamName	VARCHAR(30)	NOT NULL,
	PRIMARY KEY(teamNum)
);

CREATE TABLE IF NOT EXISTS employee_scrumTeam(
	employeeID	INT(12) NOT NULL,
	teamNum		INT(12)	NOT NULL,
	teamLead	BOOLEAN	NOT NULL,
	PRIMARY KEY(teamNum, employeeID),
	FOREIGN KEY(teamNum) 	REFERENCES scrum_team(teamNum),
	FOREIGN KEY(employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE IF NOT EXISTS userStory(
	userStoryID		INT(11)		NOT NULL,
	asA				VARCHAR(30)	NOT NULL,
	IwantTo			VARCHAR(100)	NOT NULL,
	becauseSoThat	VARCHAR(100)	NOT NULL,
	userClass		VARCHAR(30)	NOT NULL,
	PRIMARY KEY(userStoryID)
);

CREATE TABLE IF NOT EXISTS task(
	userStoryID	INT(11) 	NOT NULL,
	task	VARCHAR(30) NOT NULL,
	PRIMARY KEY(userStoryID, task),
   	FOREIGN KEY(userStoryID) REFERENCES userStory(userStoryID)
);
CREATE TABLE IF NOT EXISTS sprint_backlog(
	projectCode 	INT(11)		NOT NULL,
	userStoryID	INT(11)		NOT NULL,
	sprintNum		INT(11)	     NOT NULL,
	progress	VARCHAR(30)         NOT NULL,
	priority	INT(11)		     NOT NULL,
	PRIMARY KEY(sprintNum, projectCode),
	FOREIGN KEY(userStoryID) 	REFERENCES	userStory(userStoryID)
);

CREATE TABLE IF NOT EXISTS sprint(
	sprintNum		INT(11)	NOT NULL,
	projectCode 	INT(11) 	NOT NULL,
	active		BOOLEAN	NOT NULL,
	startDate		DATE		NOT NULL,
	endDate		DATE		NOT NULL,
	teamNum		INT(11)	NOT NULL,
	PRIMARY KEY(projectCode, sprintNum),
	FOREIGN KEY(teamNum) 	REFERENCES employee_scrumTeam(teamNum),
     FOREIGN KEY(projectCode) REFERENCES project(projectCode),
     FOREIGN KEY(sprintNum) REFERENCES sprint_backlog(sprintNum)
);



CREATE TABLE IF NOT EXISTS project_backlog (
	projectCode INT(11) 		NOT NULL,
	userStoryID	INT(11)		NOT NULL,
	name		VARCHAR(100)	NOT NULL,
	createDate 	DATE		NOT NULL,
	PRIMARY KEY(projectCode),
	FOREIGN KEY(projectCode) REFERENCES sprint(projectCode),
	FOREIGN KEY(userStoryID) REFERENCES userStory(userStoryID),
     FOREIGN KEY(projectCode) REFERENCES project(projectCode)
 );

CREATE TABLE IF NOT EXISTS employee_project(
	projectCode		INT(11)	NOT NULL,
	employeeID		INT(11)		NOT NULL,
	dateStarted		DATE		NOT NULL,
	hoursWorked		INT(11)		NOT NULL,
	PRIMARY KEY(projectCode, employeeID),  
	FOREIGN KEY(projectCode) REFERENCES project(projectCode),
	FOREIGN KEY(employeeID) REFERENCES 	employee(employeeID)
);

CREATE TABLE IF NOT EXISTS stakeholder (
	stakeholderID int,
	firstName varchar(50),
	lastName varchar(50)
);

CREATE TABLE IF NOT EXISTS stakeholder_project (
	stakeholderID int,
	projectCode int,
	interestLevel int,
	type varchar(50)
);

-- HOW TO SELECT ALL EMPLOYEES AND ALL INFO INCLUDING CITY AND STATE
-- SELECT employeeID, firstName, lastName, title, address, employee.zip, city, st FROM employee INNER JOIN zipCode ON employee.zip = zipCode.zip;

INSERT INTO employee VALUES (1, 'Michael', 'Sanfelippo', 'Software Engineer', '1234 Fake St.', 90706), (2, 'Renny','Olmedo', 'Software Engineer', '5678 Cool Ave.', 90706), (3, 'Ricardo', 'Vargas', 'Architect', '8737 Charlamagne Ave.', 90242), (4, 'Kasey', 'Peoples', 'Secretary', '1432 Downey St.', 90242), (5, 'Gerardo', 'Zacarias', 'Artist', '6368 Artesia Blvd.', 90637),
(6, 'Brandon', 'Borge', 'Specialist', '2535 Best Ave.', 90706), (7, 'Tate', 'McGeary', 'Software Engineer', '6753 Beach Blvd.', 90804), (8, 'Mark', 'Saavedra', 'Developer', '8998 Foster Rd.', 90706), (9, 'Deemal', 'Patel', 'Software Engineer', '4245 Artesia Blvd.', 90702), (10, 'Pedro', 'Santiago', 'Resident Advisor', '1250 Bellflower Blvd.', 90840), (11, 'Madison', 'Flores', 'Cosmetologist', '1234 Polk St.', 97351), (12, 'Frank', 'Macias', 'Carpenter', '1234 Polk St.', 97351), (13, 'Debora', 'Sanfelippo', 'CEO', '1234 Fake St.', 90706), (14, 'Juliana', 'Sanfelippo', 'CTO', '1234 Fake St.', 90706), (15, 'Genie', 'Bottle', 'Technician', '5691 Wish Ave.', 90840);

INSERT INTO stakeholder VALUES (1, 'Erika', 'Perez'), (2, 'Abel', 'Cole'), (3, 'Neal', 'Carson'), (4, 'Douglas', 'Holland'), (5, 'Chad', 'Ballard'), (6, 'Katie', 'Torres'), (7, 'Dawn', 'Hogan'), (8, 'Minnie', 'Chandler'), (9, 'Alyssa', 'Kim'), (10, 'Patrick', 'Arnold')

INSERT INTO project VALUES (1, '2017-04-22', '2018-05-23', 'CECS 323 Database Project');


INSERT INTO employee_project VALUES (1, 1, '2017-04-22', 0), (1, 2, '2017-04-22', 0);

INSERT INTO stakeholder_project VALUES (1,1,5, 'User');

INSERT INTO scrum_team VALUES (1, 'CLG');

INSERT INTO employee_scrumTeam VALUES (1,1, TRUE);

INSERT INTO sprint_backlog VALUES (1,1,1,'Started', 5);

INSERT INTO sprint VALUES (1, 1, TRUE, '2017-04-22', '2017-05-22', 1);
INSERT INTO sprint VALUES (2,1, FALSE, '2017-05-23', '2017-06-23', 1);

INSERT INTO userStory VALUES (1, 'Software Developer', 'be able to insert data', 'I can access that data at a later date', 'Some user class');

INSERT INTO task VALUES (1, 'Design the database');


INSERT INTO project_backlog VALUES (1, 1, 1, 'Create and populate the database', '2017-04-22');

INSERT INTO project VALUES (2, '2017-04-25', '2019-03-23', 'CECS 277 Java Project');
INSERT INTO project VALUES (3, '2016-03-05', '2020-09-11', 'CECS 201 Engineering Project');

INSERT INTO employee_project VALUES (2, 5, '2017-04-25', 5);
INSERT INTO employee_project VALUES (2,6,'2017-04-25', 10);
INSERT INTO employee_project VALUES (2, 7, '2017-04-25', 0);
INSERT INTO employee_project VALUES (2, 9, '2017-04-25', 30);
INSERT INTO employee_project VALUES (3, 10, '2017-04-23', 0);
INSERT INTO employee_project VALUES (3, 12,'2016-05-03', 100);
INSERT INTO employee_project VALUES (3, 15, '2016-07-23', 30);

INSERT INTO stakeholder_project VALUES (2, 2, 10, 'Investor');
INSERT INTO stakeholder_project VALUES (3,3,3,'Advisor');
INSERT INTO stakeholder_project VALUES (4,3,6, 'User');

INSERT INTO scrum_team VALUES (3, 'Cloud 9');
INSERT INTO scrum_team VALUES (4, 'Phoenix1');

INSERT INTO employee_scrumTeam VALUES (5, 2, FALSE);
INSERT INTO employee_scrumTeam VALUES (6, 2, FALSE);
INSERT INTO employee_scrumTeam VALUES (7, 3, TRUE);
INSERT INTO employee_scrumTeam VALUES (8, 3, FALSE);
INSERT INTO employee_scrumTeam VALUES (9, 4, TRUE);
INSERT INTO employee_scrumTeam VALUES (10, 4, FALSE);

INSERT INTO userStory VALUES (2, 'Engineer', 'build something', 'people can live in it', 'Construction');
INSERT INTO userStory VALUES (3, 'Investor', 'make money', 'I become richer', 'Self-interest');
INSERT INTO userStory VALUES (4, 'Developer', 'make a good interface', 'people will buy it', 'GUI');
INSERT INTO userStory VALUES (5, 'Manager', 'manage people', 'I will be respected', 'Self-interest');

INSERT INTO sprint_backlog VALUES (2, 2, 5, 'In progress', 3);
INSERT INTO sprint_backlog VALUES (2, 3, 6, 'Completed', 1);
INSERT INTO sprint_backlog VALUES (3, 4, 7, 'Almost done', 8);

INSERT INTO sprint VALUES (5, 2, TRUE, '2017-04-25', '2017-05-26', 2);
INSERT INTO sprint VALUES (6, 2, FALSE, '2017-05-25', '2017-06-26', 2);

INSERT INTO task VALUES (2, 'Build something');
INSERT INTO task VALUES (2, 'Build another thing');
INSERT INTO task VALUES (3, 'Make more money');
INSERT INTO task VALUES (4, 'Build the gui');
INSERT INTO task VALUES (4, 'Design the gui');
INSERT INTO task VALUES (5, 'Yell at employees');
INSERT INTO task VALUES (5, 'Yell at more employees');
