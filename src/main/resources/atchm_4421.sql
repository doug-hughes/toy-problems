/*

DELETE FROM dbo.Class
DELETE FROM dbo.PErson
DELETE FROM dbo.Parent
DELETE FROM dbo.AttendanceRecord
DELETE FROM dbo.Schedule
DELETE FROM dbo.Enrollment
*/

INSERT INTO dbo.Class
SELECT 123, 'Math'
UNION
SELECT 456, 'English'
UNION
SELECT 789, 'History'
UNION 
SELECT 258, 'Lunch'
UNION
SELECT 147, 'Science'
GO
INSERT INTO dbo.Person
SELECT 25, 'Jack', 255, NULL, 'Male'
UNION
SELECT 13, 'Jill', 100, 101, 'Female'
UNION
SELECT 82, 'Adam', NULL, 200, 'Male'
UNION
SELECT 100, 'Jeff', NULL, NULL, 'Male'
UNION
SELECT 101, 'June', NULL, NULL, 'Female'
UNION
SELECT 200, 'Shirley', NULL, NULL, 'Female'
UNION
SELECT 155, 'Alice', 255, NULL, 'Female'
UNION
SELECT 255, 'Tom', NULL, NULL, 'Male'
UNION
SELECT 19, 'Jake', 100,101, 'Male'
GO

INSERT INTO dbo.Schedule
SELECT 25,123
UNION
SELECT 25,456
UNION
SELECT 25,789
UNION
SELECT 13,147
UNION
SELECT 82,147
UNION
SELECT 82,456
UNION
SELECT 13,456
UNION
SELECT 19,123
UNION
SELECT 19,789
UNION
SELECT 155,456
UNION
SELECT 155,789
GO

INSERT INTO dbo.Enrollment
SELECT 25, '09/01/2012','05/31/2013', 7
UNION
SELECT 25, '03/01/2013','05/31/2013', 8
UNION
SELECT 13, '09/01/2012','05/31/2013', 7
UNION
SELECT 82, '09/01/2012', '12/31/2012', 7
UNION
SELECT 82, '01/01/2013', '05/31/2013', 8
UNION
SELECT 19, '09/01/2012', '05/31/2013', 8
UNION
SELECT 155, '09/01/2012','05/31/2013', 7 
GO
INSERT INTO dbo.AttendanceRecord
SELECT 25,123,'03/21/2013'
UNION
SELECT 25,456,'03/21/2013'
UNION
SELECT 25,789,'03/21/2013'
UNION
SELECT 13,147,'03/21/2013'
UNION
SELECT 25,123,'03/22/2013'
UNION
SELECT 82,147,'03/22/2013'
UNION
SELECT 82,456,'03/22/2013'
UNION
SELECT 13,147,'03/23/2013'
UNION
SELECT 25,456,'03/23/2013'
UNION
SELECT 13,456,'03/24/2013'
UNION
SELECT 25,456,'03/28/2013'
GO