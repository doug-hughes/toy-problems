-- problem #3
select SUM(IIF(p.gender = 'Male', 1, 0)) as 'male', SUM(IIF(p.gender = 'Female', 1, 0)) as 'female'
from Person p 
where p.personID = (
    select distinct e.personID
    from Enrollment e
    where e.personID = p.personID)
