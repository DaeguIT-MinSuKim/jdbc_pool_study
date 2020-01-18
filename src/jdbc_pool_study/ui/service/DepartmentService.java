package jdbc_pool_study.ui.service;

import java.sql.SQLException;
import java.util.List;

import jdbc_pool_study.dao.DepartmentDao;
import jdbc_pool_study.dao.DepartmentDaoImpl;
import jdbc_pool_study.dto.Department;

public class DepartmentService {
	private DepartmentDao deptDao;

	public DepartmentService() {
		deptDao = new DepartmentDaoImpl();
	}
	
	public void addDepartment(Department dept) throws SQLException {
		deptDao.insertDepartment(dept);
	}
	
	public void removeDepartment(Department dept) throws SQLException {
		deptDao.deleteDepartment(dept.getDeptNo());
	}
	
	public void modifyDepartment(Department dept) throws SQLException {
		deptDao.updateDepartment(dept);
	}
	
	public Department searchDepartment(Department dept) throws SQLException {
		return deptDao.selectDepartmentByNo(dept);
	}
	
	public List<Department> listDeparments(){
		return deptDao.selectDepartmentByAll();
	}
	
}
