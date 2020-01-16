package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	final Logger LOG = LogManager.getLogger();

	@Override
	public List<Department> selectDepartmentByAll() {
		List<Department> lists = new ArrayList<>();
		String sql = "SELECT deptno, deptname, floor from department";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			LOG.trace(pstmt);
			while(rs.next()) {
				lists.add(getDepartment(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

}
