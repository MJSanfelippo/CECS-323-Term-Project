-- HOW TO SELECT ALL EMPLOYEES AND ALL INFO INCLUDING CITY AND STATE
-- SELECT employeeID, firstName, lastName, title, address, employee.zip, city, st FROM employee INNER JOIN zipCode ON employee.zip = zipCode.zip;

INSERT INTO employee VALUES (1, 'Michael', 'Sanfelippo', 'Software Engineer', '1234 Fake St.', 90706), (2, 'Renny','Olmedo', 'Software Engineer', '5678 Cool Ave.', 90706), (3, 'Ricardo', 'Vargas', 'Architect', '8737 Charlamagne Ave.', 90242), (4, 'Kasey', 'Peoples', 'Secretary', '1432 Downey St.', 90242), (5, 'Gerardo', 'Zacarias', 'Artist', '6368 Artesia Blvd.', 90637),
(6, 'Brandon', 'Borge', 'Specialist', '2535 Best Ave.', 90706), (7, 'Tate', 'McGeary', 'Software Engineer', '6753 Beach Blvd.', 90804), (8, 'Mark', 'Saavedra', 'Developer', '8998 Foster Rd.', 90706), (9, 'Deemal', 'Patel', 'Software Engineer', '4245 Artesia Blvd.', 90702), (10, 'Pedro', 'Santiago', 'Resident Advisor', '1250 Bellflower Blvd.', 90840), (11, 'Madison', 'Flores', 'Cosmetologist', '1234 Polk St.', 97351), (12, 'Frank', 'Macias', 'Carpenter', '1234 Polk St.', 97351), (13, 'Debora', 'Sanfelippo', 'CEO', '1234 Fake St.', 90706), (14, 'Juliana', 'Sanfelippo', 'CTO', '1234 Fake St.', 90706), (15, 'Genie', 'Bottle', 'Technician', '5691 Wish Ave.', 90840);

INSERT INTO stakeholder VALUES (1, 'Erika', 'Perez'), (2, 'Abel', 'Cole'), (3, 'Neal', 'Carson'), (4, 'Douglas', 'Holland'), (5, 'Chad', 'Ballard'), (6, 'Katie', 'Torres'), (7, 'Dawn', 'Hogan'), (8, 'Minnie', 'Chandler'), (9, 'Alyssa', 'Kim'), (10, 'Patrick', 'Arnold');

INSERT INTO project VALUES (1, '2017-04-22', '2018-05-23', 'CECS 323 Database Project');
INSERT INTO project VALUES (2, '2017-04-25', '2019-03-23', 'CECS 277 Java Project');
INSERT INTO project VALUES (3, '2016-03-05', '2020-09-11', 'CECS 201 Engineering Project');

INSERT INTO project_backlog VALUES (1, 1, 'Create and populate the database', '2017-04-22');

INSERT INTO scrum_team VALUES (1, 'CLG');
INSERT INTO scrum_team VALUES (3, 'Cloud 9');
INSERT INTO scrum_team VALUES (4, 'Phoenix1');

INSERT INTO employee_project VALUES (1, 1, '2017-04-22', 0), (1, 2, '2017-04-22', 0);
INSERT INTO employee_project VALUES (2, 5, '2017-04-25', 5);
INSERT INTO employee_project VALUES (2,6,'2017-04-25', 10);
INSERT INTO employee_project VALUES (2, 7, '2017-04-25', 0);
INSERT INTO employee_project VALUES (2, 9, '2017-04-25', 30);
INSERT INTO employee_project VALUES (3, 10, '2017-04-23', 0);
INSERT INTO employee_project VALUES (3, 12,'2016-05-03', 100);
INSERT INTO employee_project VALUES (3, 15, '2016-07-23', 30);

INSERT INTO employee_scrumTeam VALUES (1,1, TRUE);
INSERT INTO employee_scrumTeam VALUES (5, 2, FALSE);
INSERT INTO employee_scrumTeam VALUES (6, 2, FALSE);
INSERT INTO employee_scrumTeam VALUES (7, 3, TRUE);
INSERT INTO employee_scrumTeam VALUES (8, 3, FALSE);
INSERT INTO employee_scrumTeam VALUES (9, 4, TRUE);
INSERT INTO employee_scrumTeam VALUES (10, 4, FALSE);


INSERT INTO stakeholder_project VALUES (1,1,5, 'User');
INSERT INTO stakeholder_project VALUES (2, 2, 10, 'Investor');
INSERT INTO stakeholder_project VALUES (3,3,3,'Advisor');
INSERT INTO stakeholder_project VALUES (4,3,6, 'User');

INSERT INTO userStory VALUES (1, 'Software Developer', 'be able to insert data', 'I can access that data at a later date', 'Some user class');
INSERT INTO userStory VALUES (2, 'Engineer', 'build something', 'people can live in it', 'Construction');
INSERT INTO userStory VALUES (3, 'Investor', 'make money', 'I become richer', 'Self-interest');
INSERT INTO userStory VALUES (4, 'Developer', 'make a good interface', 'people will buy it', 'GUI');
INSERT INTO userStory VALUES (5, 'Manager', 'manage people', 'I will be respected', 'Self-interest');


INSERT INTO sprint_backlog VALUES (1,1,1,'Started', 5);
INSERT INTO sprint_backlog VALUES (2, 2, 5, 'In progress', 3);
INSERT INTO sprint_backlog VALUES (2, 3, 6, 'Completed', 1);
INSERT INTO sprint_backlog VALUES (3, 4, 7, 'Almost done', 8);

INSERT INTO sprint VALUES (1, 1, TRUE, '2017-04-22', '2017-05-22', 1);
INSERT INTO sprint VALUES (2,1, FALSE, '2017-05-23', '2017-06-23', 1);
INSERT INTO sprint VALUES (5, 2, TRUE, '2017-04-25', '2017-05-26', 2);
INSERT INTO sprint VALUES (6, 2, FALSE, '2017-05-25', '2017-06-26', 2);

INSERT INTO task VALUES (1, 'Design the database');
INSERT INTO task VALUES (2, 'Build something');
INSERT INTO task VALUES (2, 'Build another thing');
INSERT INTO task VALUES (3, 'Make more money');
INSERT INTO task VALUES (4, 'Build the gui');
INSERT INTO task VALUES (4, 'Design the gui');
INSERT INTO task VALUES (5, 'Yell at employees');
INSERT INTO task VALUES (5, 'Yell at more employees');
