DROP TABLE EMPLOYEE;

CREATE TABLE IF NOT EXISTS EMPLOYEE (
    ID INTEGER,
    EmpID VARCHAR(36),
    NamePrefix VARCHAR(8),
    FirstName VARCHAR(32),
    MiddleInitial VARCHAR(1),
    LastName VARCHAR(32),
    Gender VARCHAR(16),
    EMail VARCHAR(128),
    FathersName VARCHAR(32),
    MothersName VARCHAR(32),
    MothersMaidenName VARCHAR(32),
    DateOfBirth VARCHAR(32),
    TimeOfBirth VARCHAR(32),
    AgeInYrs VARCHAR(5),
    WeightInKgs VARCHAR(12),
    DateOfJoining VARCHAR(10),
    QuarterOfJoining VARCHAR(2),
    HalfOfJoining VARCHAR(2),
    YearOfJoining VARCHAR(4),
    MonthOfJoining VARCHAR(2),
    MonthNameOfJoining VARCHAR(32),
    ShortMonth VARCHAR(32),
    DayOfJoining VARCHAR(2),
    DOWOfJoining VARCHAR(32),
    ShortDOW VARCHAR(32),
    AgeInCompanyYears VARCHAR(5),
    Salary VARCHAR(12),
    LastPercentHike VARCHAR(12),
    SSN VARCHAR(32),
    PhoneNum VARCHAR(32),
    PlaceName VARCHAR(64),
    County VARCHAR(64),
    City VARCHAR(64),
    State VARCHAR(64),
    Zip VARCHAR(16),
    Region VARCHAR(64),
    UserName VARCHAR(32),
    Password BINARY,
    PRIMARY KEY (EmpID)
)
;

--SET STREAMING ON;
COPY FROM '/data/Employee-dt-head2.csv' INTO PUBLIC.EMPLOYEE (EmpID,NamePrefix,FirstName,MiddleInitial,LastName,Gender,EMail,FathersName,MothersName,MothersMaidenName,DateOfBirth,TimeOfBirth,AgeInYrs,WeightInKgs,DateOfJoining,QuarterOfJoining,HalfOfJoining,YearOfJoining,MonthOfJoining,MonthNameOfJoining,ShortMonth,DayOfJoining,DOWOfJoining,ShortDOW,AgeInCompanyYears,Salary,LastPercentHike,SSN,PhoneNum,PlaceName,County,City,State,Zip,Region,UserName,"PASSWORD") FORMAT CSV;
