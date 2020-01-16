package jdbc_pool_study.dao;

import java.util.List;

import jdbc_pool_study.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
}
