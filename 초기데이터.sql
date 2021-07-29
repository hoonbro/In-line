use inline;

ALTER TABLE department AUTO_INCREMENT = 100;
alter table job auto_increment = 1000;
alter table office auto_increment = 1;
alter table room auto_increment = 1;
alter table user auto_increment = 1;

insert into job(job_name) values("사장"),("이사"),("전무"),
("부장"),("차장"),("과장"),("팀장"),("대리"),("사원");

insert into department(dept_name) values("기획"),("개발"),("인사"),("경영"),("마케팅"),("회계"),("디자인"),("보안"),("제조"),("재무"),("영업");

insert into room values(1, "로비", null, 1);

