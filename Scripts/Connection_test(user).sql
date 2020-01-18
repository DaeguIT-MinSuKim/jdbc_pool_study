select user(), database();

select * from employee;


/*
procedure
*/
drop procedure if exists procedure_01;
delimiter $$
create procedure procedure_01 (
    in in_dno int(11))
begin
	select  empno, empname, title, manager, salary, dno 
      from employee
     where dno=in_dno;
end $$
delimiter ;

call procedure_01(1);


-- cursor
drop procedure if exists procedure_cursor;

delimiter $$
create procedure procedure_cursor (
    in in_dno int(11))
begin
    declare out_empno int(11); 
    declare out_empname varchar(20);
    declare out_title varchar(10);
	declare out_manager int(11);
	declare out_salary int(11);
	declare out_dno int(11);

    declare end_of_row boolean default false;    
    
    declare user_cursor cursor for -- 커서 선언
        select  empno, empname, title, manager, salary, dno 
        from employee
        where dno=in_dno;
    
    declare continue handler for not found 
    begin
		set end_of_row = true;
		close user_cursor;
    end;
   
    open user_cursor;    -- 커서 열기

    cursor_loop: loop
        fetch user_cursor into out_empno, out_empname, out_title, out_manager, out_salary, out_dno; -- 사원의 급여 1개를 대입
        if end_of_row then -- 더이상 읽을 행이 없으면 loop종료
            leave cursor_loop;
        end if;
        select  out_empno as empno, out_empname as empname, out_title as title, out_manager as manager, out_salary as salary, out_dno as dno;
    end loop cursor_loop;
   
end $$
delimiter ;


call procedure_cursor(1);


