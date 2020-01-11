-- jdbc_pool_study
DROP SCHEMA IF EXISTS jdbc_pool_study;

-- jdbc_pool_study
CREATE SCHEMA jdbc_pool_study;

-- department
CREATE TABLE jdbc_pool_study.department (
	deptno   INT(11)  NOT NULL COMMENT '부서번호', -- 부서번호
	deptname CHAR(10) NOT NULL COMMENT '부서명', -- 부서명
	floor    INT(11)  NULL     DEFAULT 0 COMMENT '위치' -- 위치
)
COMMENT 'department';

-- department
ALTER TABLE jdbc_pool_study.department
	ADD CONSTRAINT
		PRIMARY KEY (
			deptno -- 부서번호
		);

-- employee
CREATE TABLE jdbc_pool_study.employee (
	empno   INT(11)     NOT NULL COMMENT '사원번호', -- 사원번호
	empname VARCHAR(20) NOT NULL COMMENT '사원명', -- 사원명
	title   VARCHAR(10) NULL     DEFAULT '사원' COMMENT '직책', -- 직책
	manager INT(11)     NULL     COMMENT '관리자', -- 관리자
	salary  INT(11)     NULL     COMMENT '급여', -- 급여
	dno     INT(11)     NULL     DEFAULT 1 COMMENT '부서', -- 부서
	pic     LONGBLOB    NULL     COMMENT '증명사진' -- 증명사진
)
COMMENT 'employee';

-- employee
ALTER TABLE jdbc_pool_study.employee
	ADD CONSTRAINT
		PRIMARY KEY (
			empno -- 사원번호
		);

-- employee
ALTER TABLE jdbc_pool_study.employee
	ADD CONSTRAINT employee_ibfk_1 -- employee_ibfk_1
		FOREIGN KEY (
			manager -- 관리자
		)
		REFERENCES jdbc_pool_study.employee ( -- employee
			empno -- 사원번호
		),
	ADD INDEX manager (
		manager -- 관리자
	);

-- employee
ALTER TABLE jdbc_pool_study.employee
	ADD CONSTRAINT employee_ibfk_2 -- employee_ibfk_2
		FOREIGN KEY (
			dno -- 부서
		)
		REFERENCES jdbc_pool_study.department ( -- department
			deptno -- 부서번호
		),
	ADD INDEX dno (
		dno -- 부서
	);
	

-- create user
drop user 'user_jdbc_pool_study'@'localhost';
grant all privileges on jdbc_pool_study.* to 'user_jdbc_pool_study'@'localhost' identified by 'rootroot';
flush privileges;
