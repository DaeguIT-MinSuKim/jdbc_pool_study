package jdbc_pool_study.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import jdbc_pool_study.dto.Department;

public class DepartmentDaoTest {
	protected static Logger logger = LogManager.getLogger();
	private static DepartmentDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new DepartmentDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
	}

	@Test
	public void testSelectDepartmentByAll() {
		List<Department> departments = dao.selectDepartmentByAll();
		Assert.assertNotEquals(0, departments.size());
		for(Department d : departments) {
			logger.trace(d);
		}
	}
	
	@Test
	public void testSelectDepartmentByNo() throws SQLException {
		Department devDept = new Department(3);
		Department selectedDepartment = dao.selectDepartmentByNo(devDept);
		Assert.assertNotNull(selectedDepartment);
		logger.trace(selectedDepartment);
	}

}
