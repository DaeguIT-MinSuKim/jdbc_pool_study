package jdbc_pool_study.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MySqlDataSourceTest {
	protected static Logger logger = LogManager.getLogger();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConnection() throws SQLException {
		Connection con = MySqlDataSource.getConnection();
		logger.debug(con);
			
		Assert.assertNotNull(con);
	}

}
