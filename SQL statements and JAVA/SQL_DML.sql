use scrum_test;

 CREATE TABLE IF NOT EXISTS project(
	projectCode	INT(11)		NOT NULL,
	startDate	DATE		NOT NULL,
	deadline	DATE		NOT NULL,
    name 		VARCHAR(50) NOT NULL,
	PRIMARY KEY(projectCode)
);

CREATE TABLE IF NOT EXISTS zipCode (
	zip 	INT(12) 	NOT NULL,
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
	zip			INT(12)		NOT NULL,
	PRIMARY KEY(employeeID),
	FOREIGN KEY(zip) REFERENCES zipCode(zip)
);

CREATE TABLE IF NOT EXISTS scrum_team(
	teamNum		INT(12)		NOT NULL,
	teamName	VARCHAR(30)	NOT NULL,
	PRIMARY KEY(teamNum),
	UNIQUE(teamName)
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
	userStoryID		INT(11)			NOT NULL,
	asA				VARCHAR(30)		NOT NULL,
	IwantTo			VARCHAR(100)	NOT NULL,
	becauseSoThat	VARCHAR(100)	NOT NULL,
	userClass		VARCHAR(30)		NOT NULL,
	PRIMARY KEY(userStoryID)
);

CREATE TABLE IF NOT EXISTS task(
	userStoryID		INT(11) 	NOT NULL,
	task		VARCHAR(30) 	NOT NULL,
	PRIMARY KEY(userStoryID, task),
   	FOREIGN KEY(userStoryID) REFERENCES userStory(userStoryID)
);
CREATE TABLE IF NOT EXISTS sprint_backlog(
	projectCode 	INT(11)		NOT NULL,
	userStoryID		INT(11)		NOT NULL,
	sprintNum		INT(11)     NOT NULL,
	progress		VARCHAR(30) NOT NULL,
	priority		INT(11)	    NOT NULL,
	PRIMARY KEY(sprintNum, projectCode),
	FOREIGN KEY(userStoryID) 	REFERENCES	userStory(userStoryID)
);


CREATE TABLE IF NOT EXISTS project_backlog (
	projectCode 	INT(11) 		NOT NULL,
	userStoryID		INT(11)			NOT NULL,
	name			VARCHAR(100)	NOT NULL,
	createDate 		DATE			NOT NULL,
	PRIMARY KEY(projectCode),
	FOREIGN KEY(userStoryID) REFERENCES userStory(userStoryID),
	FOREIGN KEY(projectCode) REFERENCES project(projectCode)
 );

CREATE TABLE IF NOT EXISTS employee_project(
	projectCode		INT(11)		NOT NULL,
	employeeID		INT(11)		NOT NULL,
	dateStarted		DATE		NOT NULL,
	hoursWorked		INT(11)		NOT NULL,
	PRIMARY KEY(projectCode, employeeID),  
	FOREIGN KEY(projectCode) 	REFERENCES 	project(projectCode),
	FOREIGN KEY(employeeID) 	REFERENCES 	employee(employeeID)
);

CREATE TABLE IF NOT EXISTS sprint(
	sprintNum		INT(11)		NOT NULL,
	projectCode 	INT(11) 	NOT NULL,
	active			BOOLEAN		NOT NULL,
	startDate		DATE		NOT NULL,
	endDate			DATE		NOT NULL,
	teamNum			INT(11)		NOT NULL,
	PRIMARY KEY(projectCode, sprintNum),
	FOREIGN KEY(teamNum) 		REFERENCES employee_scrumTeam(teamNum),
    FOREIGN KEY(projectCode) 	REFERENCES project(projectCode),
    FOREIGN KEY(sprintNum) 		REFERENCES sprint_backlog(sprintNum)
);

CREATE TABLE IF NOT EXISTS stakeholder (
	stakeholderID 	INT,
	firstName 		VARCHAR(50),
	lastName 		VARCHAR(50),
	PRIMARY KEY(stakeholderID)
);

CREATE TABLE IF NOT EXISTS stakeholder_project (
	stakeholderID 	INT,
	projectCode 	INT,
	interestLevel 	INT,
	type 			varchar(50),
	PRIMARY KEY(projectCode, stakeholderID),
	FOREIGN KEY(projectCode) 	REFERENCES project(projectCode),
	FOREIGN KEY(stakeholderID) 	REFERENCES stakeholder(stakeholderID)
); 

