package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc_pool_study.dto.Employee;

public interface EmployeeDao {
	int deleteEmployee(Connection con, Employee employee) throws SQLException;

	int insertEmployee(Connection con, Employee employee) throws SQLException;

	int updateEmployee(Connection con, Employee employee) throws SQLException;

	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;

	Employee selectEmployeeByNo(Connection con, Employee employee) throws SQLException;
	
	List<Employee> procedureEmployeeByDeptNo(Connection con, int deptNo) throws SQLException;
	
	List<Employee> procedureEmployeeByDeptNoWithCursor(Connection con, int deptNo) throws SQLException;
	
}
