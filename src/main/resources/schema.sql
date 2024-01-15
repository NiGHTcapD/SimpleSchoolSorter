create sequence teacher_seq start with 1 increment by 50;
create sequence student_seq start with 1 increment by 50;
create sequence course_seq start with 1 increment by 50;

create table teacher (idgnorable integer not null, teachFirstName varchar(100), teachLastName varchar(100), hour1 integer, hour2 integer, hour3 integer, hour4 integer, hour5 integer, hour6 integer, hour7 integer, hour8 integer, primary key (idgnorable));
create table student (idgnorable integer not null, studentFirstName varchar(100), studentLastName varchar(100), studentGrade varchar(100), class1 integer, class2 integer, class3 integer, class4 integer, class5 integer, class6 integer, class7 integer, class8 integer, primary key (idgnorable));
create table course (classId integer not null, courseName varchar(100), courseGrade integer, primary key (classId));


//create table users (id integer, username varchar(100), email varchar(100), password varchar(100), primary key (id));
//create table auth (id integer, email varchar, role varchar, primary key (id));