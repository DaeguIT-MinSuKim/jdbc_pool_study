package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc_pool_study.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
	
	Department selectDepartmentByNo(Department department) throws SQLException;
	
	int insertDepartment(Department department) throws SQLException;
	
	int updateDepartment(Department department) throws SQLException;
	
	int deleteDepartment(int deptNo) throws SQLException;
	
	int insertDepartmentWithConnection(Connection con, Department department) throws SQLException;
}
