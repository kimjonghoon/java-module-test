package net.java_school.db.dbpool;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPoolManager {
	private static final Logger logger = Logger.getLogger(DBConnectionPoolManager.class.getName());
	
	// To apply the singleton pattern to the DBConnectionPoolManager (keep only one instance), declare it as static
	static private DBConnectionPoolManager instance;
	private Vector<String> drivers = new Vector<String>();
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	// Obtaining instance of DBConnectionPoolManager
	// @return DBConnectionManger
	static synchronized public DBConnectionPoolManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionPoolManager();
		}

		return instance;
	}

	// Default Constructor
	private DBConnectionPoolManager() {}

	// Send current Connection to Free Connection List
	// @param name : Pool Name
	// @param con : Connection
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}

		logger.info("One Connection of " + name + " was freed");
	}

	// Obtain Open Connection. Creates a new connection if there are no open connections and the maximum number of connections has not been reached.
	// Waits for the default wait time when there are no open connections currently and the maximum number of connections is in use.
	// @param name : Pool Name
	// @return Connection : The connection or null
	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(10);
		}
		return null;
	}

	// Create a Connection Pool
	// @param poolName : Name of Pool to create
	// @param url : DB URL
	// @param userID : DB UserID
	// @param passwd : DB Password
	private void createPools(String poolName, 
			String url, 
			String userID,
			String passwd, 
			int maxConn, 
			int initConn, 
			int maxWait) {

		DBConnectionPool pool = new DBConnectionPool(poolName, url, userID, passwd, maxConn, initConn, maxWait);
		pools.put(poolName, pool);
		logger.info("Initialized pool " + poolName);
	}

	//Initialization
	public void init(String poolName, 
			String driver, 
			String url,
			String userID, 
			String passwd, 
			int maxConn, 
			int initConn, 
			int maxWait) {

		loadDrivers(driver);
		createPools(poolName, url, userID, passwd, maxConn, initConn, maxWait);
	}

	// JDBC Driver Loading
	// @param driverClassName : The JDBC driver for the DB you want to use.
	private void loadDrivers(String driverClassName) {
		try {
			Class.forName(driverClassName);
			drivers.addElement(driverClassName);
			logger.info("Registered JDBC driver " + driverClassName);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can't register JDBC driver: " + driverClassName);
		}
	}

	public Hashtable<String,DBConnectionPool> getPools() {
		return pools;
	}
	
	public int getDriverNumber() {
		return drivers.size();
	}

}