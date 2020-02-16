CREATE TABLE dbo.[Class]
(
	classID INT NOT NULL PRIMARY KEY,
	name VARCHAR(50)
)

CREATE TABLE dbo.[Person]
(
	personID INT NOT NULL PRIMARY KEY,
	name VARCHAR(50),
	parentID1 INT,
	parentID2 INT,
	gender VARCHAR(10)
)


CREATE TABLE dbo.AttendanceRecord
(
	attendanceID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	personID INT,
	classID INT,
	[date] SMALLDATETIME,
	CONSTRAINT FK_attendancePerson FOREIGN KEY (personID) REFERENCES Person(personID),
	CONSTRAINT FK_attendanceClass FOREIGN KEY (classID) REFERENCES Class(classID)
)


CREATE TABLE dbo.Schedule
(
	personID INT,
	classID INT,
	CONSTRAINT FK_schedulePerson FOREIGN KEY (personID) REFERENCES Person(personID),
	CONSTRAINT FK_scheduleClass FOREIGN KEY (classID) REFERENCES Class(classID)
)

CREATE TABLE dbo.Enrollment 
(
	personID INT,
	startDate SMALLDATETIME,
	endDate SMALLDATETIME,
	grade INT,
	CONSTRAINT FK_enrollmentPerson FOREIGN KEY (personID) REFERENCES Person(personID)
)