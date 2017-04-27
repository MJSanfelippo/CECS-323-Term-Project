-- ON INSERT

DROP TRIGGER IF EXISTS `scrum_test`.`employee_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_BEFORE_INSERT` BEFORE INSERT ON `employee` FOR EACH ROW
BEGIN  
    IF NEW.zip NOT IN (SELECT zipcode.zip FROM zipcode WHERE zipcode.zip = NEW.zip) THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Zip code does not exist in database.'; 
    END IF;
END;$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`employee_project_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_project_BEFORE_INSERT` BEFORE INSERT ON `employee_project` FOR EACH ROW
BEGIN
     IF NEW.projectCode NOT IN (SELECT project.projectCode FROM project WHERE project.projectCode = NEW.projectCode) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Project does not exist. Please check project code and try again.'; 
    ELSEIF NEW.employeeID NOT IN (SELECT employee.employeeID FROM employee WHERE employee.employeeID = NEW.employeeID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee does not exist in database.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`employee_scrumteam_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `scrum_test`.`employee_scrumteam_BEFORE_INSERT` BEFORE INSERT ON `employee_scrumteam` FOR EACH ROW
BEGIN
	
    DECLARE tl VARCHAR(30); 
    
    SELECT employee.title INTO tl FROM employee WHERE employee.employeeID = NEW.employeeID;
    
	IF NEW.teamNum NOT IN (SELECT scrum_team.teamNum FROM scrum_team WHERE scrum_team.teamNum = NEW.teamNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Scrum team does not or exist or is no longer active.'; 
    ELSEIF NEW.employeeID NOT IN (SELECT employee.employeeID FROM employee WHERE employee.employeeID = NEW.employeeID) AND (LOWER(tl) = 'software engineer' OR LOWER(tl) = 'developer' OR LOWER(tl) = 'software developer') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee does not exist in database or they do not have the correct title.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`project_backlog_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`project_backlog_BEFORE_INSERT` BEFORE INSERT ON `project_backlog` FOR EACH ROW
BEGIN
	IF NEW.userstoryID NOT IN (SELECT userstory.userstoryID FROM userstory WHERE userstory.userstoryID = NEW.userstoryID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User story does not exist.'; 
	ELSEIF NEW.projectCode NOT IN (SELECT project.projectCode FROM project WHERE project.projectCode = NEW.projectCode) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Project does not exist.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`task_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`task_BEFORE_INSERT` BEFORE INSERT ON `task` FOR EACH ROW
BEGIN
	IF NEW.userStoryID NOT IN (SELECT userstory.userStoryID FROM userstory WHERE userstory.userStoryID=NEW.userStoryID) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid user story.';
    END IF;
END$$
DELIMITER ;


DROP TRIGGER IF EXISTS `scrum_test`.`stakeholder_project_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`stakeholder_project_BEFORE_INSERT` BEFORE INSERT ON `stakeholder_project` FOR EACH ROW
BEGIN
	IF NEW.stakeholderID NOT IN (SELECT stakeholder.stakeholderID FROM stakeholder WHERE stakeholder.stakeholderID = NEW.stakeholderID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid stake holder'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`sprint_backlog_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`sprint_backlog_BEFORE_INSERT` BEFORE INSERT ON `sprint_backlog` FOR EACH ROW
BEGIN
	IF NEW.userstoryID NOT IN (SELECT userstory.userstoryID FROM userstory WHERE userstory.userstoryID = NEW.userstoryID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid user story.'; 
    END IF;
END$$
DELIMITER ;


DROP TRIGGER IF EXISTS `scrum_test`.`sprint_BEFORE_INSERT`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`sprint_BEFORE_INSERT` BEFORE INSERT ON `sprint` FOR EACH ROW
BEGIN
	IF NEW.teamNum NOT IN (SELECT employee_scrumteam.teamNum FROM employee_scrumteam WHERE employee_scrumteam.teamNum = NEW.teamNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid team.'; 
    ELSEIF NEW.projectCode NOT IN (SELECT project.projectCode FROM project WHERE project.projectCode = NEW.projectCode) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not an active project'; 
    ELSEIF NEW.sprintNum NOT IN (SELECT sprint_backlog.sprintNum FROM sprint_backlog WHERE sprint_backlog.sprintNum = NEW.sprintNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid sprint.'; 
    END IF;
END$$
DELIMITER ;


--  ON UPDATE

DROP TRIGGER IF EXISTS `scrum_test`.`employee_scrumteam_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_scrumteam_BEFORE_UPDATE` BEFORE UPDATE ON `employee_scrumteam` FOR EACH ROW
BEGIN
    DECLARE tl VARCHAR(30); 
    
    SELECT employee.title INTO tl FROM employee WHERE employee.employeeID = NEW.employeeID;
    
	IF NEW.teamNum NOT IN (SELECT scrum_team.teamNum FROM scrum_team WHERE scrum_team.teamNum = NEW.teamNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Scrum team does not or exist or is no longer active.'; 
    ELSEIF NEW.employeeID NOT IN (SELECT employee.employeeID FROM employee WHERE employee.employeeID = NEW.employeeID) AND (LOWER(tl) = 'software engineer' OR LOWER(tl) = 'developer' OR LOWER(tl) = 'software developer') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee does not exist in database or they do not have the correct title.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`employee_scrumteam_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_scrumteam_BEFORE_UPDATE` BEFORE UPDATE ON `employee_scrumteam` FOR EACH ROW
BEGIN
DECLARE tl VARCHAR(30); 
    
    SELECT employee.title INTO tl FROM employee WHERE employee.employeeID = NEW.employeeID;
    
	IF NEW.teamNum NOT IN (SELECT scrum_team.teamNum FROM scrum_team WHERE scrum_team.teamNum = NEW.teamNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Scrum team does not or exist or is no longer active.'; 
    ELSEIF NEW.employeeID NOT IN (SELECT employee.employeeID FROM employee WHERE employee.employeeID = NEW.employeeID) AND (LOWER(tl) = 'software engineer' OR LOWER(tl) = 'developer' OR LOWER(tl) = 'software developer') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee does not exist in database or they do not have the correct title.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`employee_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_BEFORE_UPDATE` BEFORE UPDATE ON `employee` FOR EACH ROW
BEGIN
	IF NEW.zip NOT IN (SELECT zipcode.zip FROM zipcode WHERE zipcode.zip = NEW.zip) THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Zip code does not exist in database.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`employee_project_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`employee_project_BEFORE_UPDATE` BEFORE UPDATE ON `employee_project` FOR EACH ROW
BEGIN
	IF NEW.projectCode NOT IN (SELECT project.projectCode FROM project WHERE project.projectCode = NEW.projectCode) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Project does not exist. Please check project code and try again.'; 
    ELSEIF NEW.employeeID NOT IN (SELECT employee.employeeID FROM employee WHERE employee.employeeID = NEW.employeeID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee does not exist in database.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`sprint_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`sprint_BEFORE_UPDATE` BEFORE UPDATE ON `sprint` FOR EACH ROW
BEGIN
	IF NEW.teamNum NOT IN (SELECT employee_scrumteam.teamNum FROM employee_scrumteam WHERE employee_scrumteam.teamNum = NEW.teamNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid team.'; 
    ELSEIF NEW.projectCode NOT IN (SELECT project.projectCode FROM project WHERE project.projectCode = NEW.projectCode) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not an active project'; 
    ELSEIF NEW.sprintNum NOT IN (SELECT sprint_backlog.sprintNum FROM sprint_backlog WHERE sprint_backlog.sprintNum = NEW.sprintNum) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid sprint.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`sprint_backlog_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`sprint_backlog_BEFORE_UPDATE` BEFORE UPDATE ON `sprint_backlog` FOR EACH ROW
BEGIN
	IF NEW.userstoryID NOT IN (SELECT userstory.userstoryID FROM userstory WHERE userstory.userstoryID = NEW.userstoryID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid user story.'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`stakeholder_project_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER = CURRENT_USER TRIGGER `scrum_test`.`stakeholder_project_BEFORE_UPDATE` BEFORE UPDATE ON `stakeholder_project` FOR EACH ROW
BEGIN
	IF NEW.stakeholderID NOT IN (SELECT stakeholder.stakeholderID FROM stakeholder WHERE stakeholder.stakeholderID = NEW.stakeholderID) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not a valid stake holder'; 
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `scrum_test`.`task_BEFORE_UPDATE`;

DELIMITER $$
USE `scrum_test`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `scrum_test`.`task_BEFORE_UPDATE` BEFORE UPDATE ON `task` FOR EACH ROW
BEGIN
	IF NEW.userStoryID NOT IN (SELECT userstory.userStoryID FROM userstory WHERE userstory.userStoryID=NEW.userStoryID) THEN
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Project does not exist.';
    END IF;
END$$
DELIMITER ;




