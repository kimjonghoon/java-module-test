package net.java_school.db.dbpool.oracle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java_school.db.dbpool.ConnectionManager;
import net.java_school.db.dbpool.api.Oracle;

@Oracle
public class OracleConnectionManager extends ConnectionManager {
	private static final Logger logger = Logger.getLogger(OracleConnectionManager.class.getName());

	public OracleConnectionManager() {
		this.poolName = "oracle";
		String configFile = "oracle.properties";

		Class<?> clazz = OracleConnectionManager.class;
		Module m = clazz.getModule();

		try {
			InputStream inputStream = m.getResourceAsStream(configFile);
			Properties prop = new Properties();
			prop.load(inputStream);

			String dbServer = prop.getProperty("dbServer");
			String port = prop.getProperty("port");
			String dbName = prop.getProperty("dbName");
			String userID = prop.getProperty("userID");
			String passwd = prop.getProperty("passwd");
			int maxConn = Integer.parseInt(prop.getProperty("maxConn"));
			int initConn = Integer.parseInt(prop.getProperty("initConn"));
			int maxWait = Integer.parseInt(prop.getProperty("maxWait"));
			String driver = "oracle.jdbc.driver.OracleDriver";
			String JDBCDriverType = "jdbc:oracle:thin";
			String url = JDBCDriverType + ":@" + dbServer + ":" + port + ":" + dbName;

			initPoolManager(this.poolName, driver, url, userID, passwd, maxConn, initConn, maxWait);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading properties of " + configFile);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initPoolManager(String poolName, String driver, String url, String userID, String passwd, int maxConn, int initConn, int maxWait) {
		this.poolManager.init(poolName, driver, url, userID, passwd, maxConn, initConn, maxWait);
	}

}
