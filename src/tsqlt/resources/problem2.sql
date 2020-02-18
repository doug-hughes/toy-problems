
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