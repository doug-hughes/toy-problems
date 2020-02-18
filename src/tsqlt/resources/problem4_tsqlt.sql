EXEC tSQLt.DropClass 'testProblem4'
GO
EXEC tSQLt.NewTestClass 'testProblem4';
GO
CREATE PROCEDURE testProblem4.[test results with Jack double enrolled using sample data]
AS
BEGIN
    DECLARE @date smalldatetime; SET @date = '4/15/2013';
    select p.name, e.grade, c.name as 'className'
    into actual
    from Enrollment e
    join Schedule s on e.personID = s.personID
    join Class c on c.classID = s.classID
    join Person p on e.personID = p.personID
    where @date between e.startDate and e.endDate
    group by p.name, e.personID, e.grade, c.classID, c.name
    having e.grade = (select max(e2.grade) 
        from Enrollment e2 
        where e.personID = e2.personID 
        and @date between e2.startDate and e2.endDate
        group by e2.personID)
    order by p.name;

    create table expected (name varchar(50), grade int, className varchar(50))
    insert expected (name, grade, className) VALUES ('Adam',8,'Science');
    insert expected (name, grade, className) VALUES ('Adam',8,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'History');
    insert expected (name, grade, className) VALUES ('Jack',8,'Math');
    insert expected (name, grade, className) VALUES ('Jack',8,'English');
    insert expected (name, grade, className) VALUES ('Jack',8,'History');
    insert expected (name, grade, className) VALUES ('Jake',8,'Math');
    insert expected (name, grade, className) VALUES ('Jake',8,'History');
    insert expected (name, grade, className) VALUES ('Jill',7,'Science');
    insert expected (name, grade, className) VALUES ('Jill',7,'English');

    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO
tSQLt.run 'testProblem4.[test results with Jack double enrolled using sample data]'
GO
CREATE PROCEDURE testProblem4.[test results with Jack double enrolled grade 7 at this date]
AS
BEGIN
    DECLARE @date smalldatetime; SET @date = '1/15/2013';
    select p.name, e.grade, c.name as 'className'
    into actual
    from Enrollment e
    join Schedule s on e.personID = s.personID
    join Class c on c.classID = s.classID
    join Person p on e.personID = p.personID
    where @date between e.startDate and e.endDate
    group by p.name, e.personID, e.grade, c.classID, c.name
    having e.grade = (select max(e2.grade) 
        from Enrollment e2 
        where e.personID = e2.personID 
        and @date between e2.startDate and e2.endDate
        group by e2.personID)
    order by p.name;

    create table expected (name varchar(50), grade int, className varchar(50))
    insert expected (name, grade, className) VALUES ('Adam',8,'Science');
    insert expected (name, grade, className) VALUES ('Adam',8,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'History');
    insert expected (name, grade, className) VALUES ('Jack',7,'Math');
    insert expected (name, grade, className) VALUES ('Jack',7,'English');
    insert expected (name, grade, className) VALUES ('Jack',7,'History');
    insert expected (name, grade, className) VALUES ('Jake',8,'Math');
    insert expected (name, grade, className) VALUES ('Jake',8,'History');
    insert expected (name, grade, className) VALUES ('Jill',7,'Science');
    insert expected (name, grade, className) VALUES ('Jill',7,'English');

    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO
tSQLt.run 'testProblem4.[test results with Jack double enrolled grade 7 at this date]'
GO
CREATE PROCEDURE testProblem4.[test results with Jack and Adam enrolled grade 7 at this date]
AS
BEGIN
    DECLARE @date smalldatetime; SET @date = '12/15/2012';
    select p.name, e.grade, c.name as 'className'
    into actual
    from Enrollment e
    join Schedule s on e.personID = s.personID
    join Class c on c.classID = s.classID
    join Person p on e.personID = p.personID
    where @date between e.startDate and e.endDate
    group by p.name, e.personID, e.grade, c.classID, c.name
    having e.grade = (select max(e2.grade) 
        from Enrollment e2 
        where e.personID = e2.personID 
        and @date between e2.startDate and e2.endDate
        group by e2.personID)
    order by p.name;

    create table expected (name varchar(50), grade int, className varchar(50))
    insert expected (name, grade, className) VALUES ('Adam',7,'Science');
    insert expected (name, grade, className) VALUES ('Adam',7,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'History');
    insert expected (name, grade, className) VALUES ('Jack',7,'Math');
    insert expected (name, grade, className) VALUES ('Jack',7,'English');
    insert expected (name, grade, className) VALUES ('Jack',7,'History');
    insert expected (name, grade, className) VALUES ('Jake',8,'Math');
    insert expected (name, grade, className) VALUES ('Jake',8,'History');
    insert expected (name, grade, className) VALUES ('Jill',7,'Science');
    insert expected (name, grade, className) VALUES ('Jill',7,'English');

    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO
tSQLt.run 'testProblem4.[test results with Jack and Adam enrolled grade 7 at this date]'
GO
CREATE PROCEDURE testProblem4.[test results with two students named Jill at this date]
AS
BEGIN
    select * into #person from Person;
    -- change jack to jill, can distinguish in output by grade
    update #person set name = 'Jill' where personId = 25;

    EXEC tSQLt.FakeTable @TableName = N'Person';
    insert into Person select * from #person;

    DECLARE @date smalldatetime; SET @date = '4/15/2013';
    select p.name, e.grade, c.name as 'className'
    into actual
    from Enrollment e
    join Schedule s on e.personID = s.personID
    join Class c on c.classID = s.classID
    join Person p on e.personID = p.personID
    where @date between e.startDate and e.endDate
    group by p.name, e.personID, e.grade, c.classID, c.name
    having e.grade = (select max(e2.grade) 
        from Enrollment e2 
        where e.personID = e2.personID 
        and @date between e2.startDate and e2.endDate
        group by e2.personID)
    order by p.name;

    create table expected (name varchar(50), grade int, className varchar(50))
    insert expected (name, grade, className) VALUES ('Adam',8,'Science');
    insert expected (name, grade, className) VALUES ('Adam',8,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'English');
    insert expected (name, grade, className) VALUES ('Alice',7,'History');
    insert expected (name, grade, className) VALUES ('Jake',8,'Math');
    insert expected (name, grade, className) VALUES ('Jake',8,'History');
    insert expected (name, grade, className) VALUES ('Jill',8,'Math');
    insert expected (name, grade, className) VALUES ('Jill',8,'English');
    insert expected (name, grade, className) VALUES ('Jill',8,'History');
    insert expected (name, grade, className) VALUES ('Jill',7,'Science');
    insert expected (name, grade, className) VALUES ('Jill',7,'English');

    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO
tSQLt.run 'testProblem4.[test results with two students named Jill at this date]'
GO