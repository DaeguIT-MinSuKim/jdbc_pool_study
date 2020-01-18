package jdbc_pool_study.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	protected static Logger logger = LogManager.getLogger();
	static EmployeeDao dao;
	static File picsDir;
	static File imagesDir;
	static Connection con;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = new EmployeeDaoImpl();
		con = MySqlDataSource.getConnection();
		picsDir = new File(System.getProperty("user.dir")+ System.getProperty("file.separator") + "pics" + System.getProperty("file.separator"));
		if (!picsDir.exists()) picsDir.mkdir();
			imagesDir = new File(System.getProperty("user.dir")+ System.getProperty("file.separator") + "images" + System.getProperty("file.separator"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = null;
		con.close();
	}

	@Test
	public void test05DeleteEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void test02InsertEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void test04UpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void test01SelectEmployeeByAll(){
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeByAll(con);
			Assert.assertNotEquals(0, lists.size());
			logger.trace(String.format("lists size = %d", lists.size()));
			for(Employee emp : lists) {
				logger.trace(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	@Test
	public void test03SelectEmployeeByNo() {
		Employee emp;
		try {
			emp = dao.selectEmployeeByNo(con, new Employee(1004));
			getOrWritePicFile(emp);
			Assert.assertNotNull(emp);
			logger.trace(emp) ;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private File getOrWritePicFile(Employee emp) throws IOException, FileNotFoundException {
		File file = null;
		file = new File(picsDir, emp.getEmpNo() + ".jpg");
		try (FileOutputStream output = new FileOutputStream(file)) {
			output.write(emp.getPic());
		}
		return file;
	}

	public byte[] getImage() throws FileNotFoundException, IOException {
		byte[] pic = null;
		File file = new File(imagesDir, "seohyunjin.jpg");
		try (InputStream is = new FileInputStream(file)){
			pic = new byte[is.available()];
			is.read(pic);
		}
		return pic;
	}

}
