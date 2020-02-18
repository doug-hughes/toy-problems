/* problem #1
	utilize a scalar function to help group by days and weekend
	using datename and not datepart which will vary by @@datefirst
*/
DROP FUNCTION IF EXISTS [dbo].[DayOfWeek]
GO
CREATE FUNCTION [dbo].[DayOfWeek] 
(
	@datepart datetime
)
RETURNS VARCHAR(9)
AS
BEGIN
	declare @dayOfWeek varchar(9); SET @dayOfWeek = datename(w,@datepart);
	if (@dayOfWeek in ('Sunday','Saturday'))
		set @dayOfWeek = 'Weekend';
	RETURN @dayOfWeek
END
GO

select dbo.DayOfWeek(date) as 'day', count(date) as 'absences'
from ( -- get distinct days with an absence for a student ignoring the class
	select distinct personId, date
	from AttendanceRecord) as a1
group by dbo.DayOfWeek(date);


-- problem #2.a
select c.name as 'child', p.name as 'parent'
from person c 
left outer join person p on c.parentID1 = p.personID
where c.parentID1 is not null
union
select c.name as 'child', p.name as 'parent'
from person c
left outer join person p on c.parentID2 = p.personID
where c.parentID2 is not null

-- problem #2.b
select distinct c.name as 'child', p1.name as 'parent1', p2.name as 'parent2'
from person c inner join Enrollment e on c.personID = e.personID
left outer join person p1 on c.parentID1 = p1.personID
left outer join person p2 on c.parentID2 = p2.personID

-- problem #3
select SUM(IIF(p.gender = 'Male', 1, 0)) as 'male', SUM(IIF(p.gender = 'Female', 1, 0)) as 'female'
from Person p 
where p.personID = ( 
    select distinct e.personID
    from Enrollment e
    where e.personID = p.personID)

--problem #4
    declare @date smalldatetime;set @date = '01/15/2013' --'04/15/2013' for Jack grade 8
    select p.name, e.grade, c.name as 'className'
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
