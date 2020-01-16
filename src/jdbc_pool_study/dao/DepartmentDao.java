package jdbc_pool_study.dao;

import java.sql.SQLException;
import java.util.List;

import jdbc_pool_study.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
	
	Department selectDepartmentByNo(Department department) throws SQLException;
}
