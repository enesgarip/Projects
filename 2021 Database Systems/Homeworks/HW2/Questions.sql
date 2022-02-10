-- smalldatetime throw an error because of dateformat. So, I set the dateformat DD/MM/YYYY
USE EG_150116034;
SET DATEFORMAT DMY;

-- A)
SELECT s.fName, s.lName, s.birthDate, s.city FROM STUDENT s;

-- B)
SELECT s.fName, s.lName, d.dName, st.fName, st.lName 
FROM STUDENT s, ADVISOR a, DEPARTMENT d, STAFF st
WHERE s.advisorID = a.staffID AND s.deptCode = d.deptCode AND a.staffID = st.staffID
ORDER BY d.dName, s.lName;

-- C)
SELECT DISTINCT s.fName, s.lName 
FROM STUDENT s, DEPARTMENT d
WHERE s.deptCode=d.deptCode AND d.dName = 'Computer Engineering';

-- D)
SELECT * 
FROM STUDENT s
WHERE s.fName LIKE '%at%';

-- E)
SELECT *
FROM STAFF st, MANAGER m
WHERE m.staffID = st.staffID AND st.isMarried='1' AND DATEDIFF(YEAR,st.birthDate,GETDATE())>40 AND st.noOfChildren >= 2;

-- F)
SELECT s.studentID, s.fName, s.lName, d.dName, di.dateOfGraduation
FROM STUDENT s, DEPARTMENT d, DIPLOMA di
WHERE s.deptCode = d.deptCode AND s.studentID = di.studentID AND DATEDIFF(DAY,di.dateOfGraduation,GETDATE())>0;
