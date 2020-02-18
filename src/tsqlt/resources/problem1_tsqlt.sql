EXEC tSQLt.DropClass 'testDayOfWeekFunction'
GO
EXEC tSQLt.NewTestClass 'testDayOfWeekFunction';
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Weekend displays for Saturday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Weekend displays for Saturday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/15/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Weekend';   --(Saturday 2/15/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Weekend displays for Sunday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Weekend displays for Sunday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/16/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Weekend';   --(Sunday 2/15/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Monday displays for Monday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Monday displays for Monday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/17/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Monday';   --(Monday 2/17/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Tuesday displays for Tuesday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Tuesday displays for Tuesday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/18/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Tuesday';   --(Tuesday 2/18/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Wednesday displays for Wednesday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Wednesday displays for Wednesday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/19/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Wednesday';   --(Wednesday 2/19/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Thursday displays for Thursday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Thursday displays for Thursday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/20/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Thursday';   --(Thursday 2/20/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Friday displays for Friday];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Friday displays for Friday]
AS
BEGIN
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/14/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Friday';   --(Friday 2/14/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that Weekend displays for Sunday with datefirst 3];
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that Weekend displays for Sunday with datefirst 3]
AS
BEGIN
    DECLARE @original tinyint; SET @original = @@datefirst;
    set datefirst 3;
    DECLARE @actual NVARCHAR(9);
    DECLARE @date smalldatetime; SET @date = '2/16/2020';

    SELECT @actual = [dbo].[DayOfWeek](@date);

    DECLARE @expected NVARCHAR(9); SET @expected = 'Weekend';   --(Sunday 2/16/2020)
    EXEC tSQLt.AssertEquals @expected, @actual;
    set datefirst @original;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test that datepart(w) varies with datefirst]
GO
CREATE PROCEDURE testDayOfWeekFunction.[test that datepart(w) varies with datefirst]
AS
BEGIN
    DECLARE @original tinyint; SET @original = @@datefirst;
    DECLARE @datefirst7 TINYINT
    DECLARE @datefirst3 TINYINT
    DECLARE @date smalldatetime; SET @date = '2/16/2020';
    set datefirst 7;
    set @datefirst7 = datepart(w, @date);
    set datefirst 3;
    set @datefirst3 = datepart(w, @date);

    EXEC tSQLt.AssertNotEquals @datefirst7, @datefirst3;
    set datefirst @original;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test DayOfWeek function as a set]
GO
CREATE PROCEDURE testDayOfWeekFunction.[test DayOfWeek function as a set]
AS
BEGIN
    create table expected (date smalldatetime, day nvarchar(9));
    insert expected (date,day) VALUES ('03/21/2013','Thursday');
    insert expected (date,day) VALUES ('03/21/2013','Thursday');
    insert expected (date,day) VALUES ('03/21/2013','Thursday');
    insert expected (date,day) VALUES ('03/21/2013','Thursday');
    insert expected (date,day) VALUES ('03/22/2013','Friday');
    insert expected (date,day) VALUES ('03/22/2013','Friday');
    insert expected (date,day) VALUES ('03/22/2013','Friday');
    insert expected (date,day) VALUES ('03/23/2013','Weekend');
    insert expected (date,day) VALUES ('03/23/2013','Weekend');
    insert expected (date,day) VALUES ('03/24/2013','Weekend');
    insert expected (date,day) VALUES ('03/28/2013','Thursday');

    select date, dbo.DayOfWeek(date) as 'day'
    into actual
    from expected;
    
    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO
DROP PROCEDURE IF EXISTS testDayOfWeekFunction.[test group by day of week using Weekend]
GO
CREATE PROCEDURE testDayOfWeekFunction.[test group by day of week using Weekend]
AS
BEGIN
    create table source (date smalldatetime, day nvarchar(9));
    insert source (date,day) VALUES ('03/21/2013','Thursday');
    insert source (date,day) VALUES ('03/21/2013','Thursday');
    insert source (date,day) VALUES ('03/21/2013','Thursday');
    insert source (date,day) VALUES ('03/21/2013','Thursday');
    insert source (date,day) VALUES ('03/22/2013','Friday');
    insert source (date,day) VALUES ('03/22/2013','Friday');
    insert source (date,day) VALUES ('03/22/2013','Friday');
    insert source (date,day) VALUES ('03/23/2013','Weekend');
    insert source (date,day) VALUES ('03/23/2013','Weekend');
    insert source (date,day) VALUES ('03/24/2013','Weekend');
    insert source (date,day) VALUES ('03/28/2013','Thursday');

    select dbo.DayOfWeek(date) as 'day', count(date) as 'absences'
    into actual
    from ( -- get distinct days with an absence for a student ignoring the class
        select distinct personId, date
        from AttendanceRecord) as a1
    group by dbo.DayOfWeek(date);

    create table expected (day nvarchar(9), absences tinyint)
    insert expected (day,absences) values (N'Friday', 2);
    insert expected (day,absences) values (N'Thursday', 3);
    insert expected (day,absences) values (N'Weekend', 3);
    
    EXEC tSQLt.AssertEqualsTable expected, actual;
END
GO