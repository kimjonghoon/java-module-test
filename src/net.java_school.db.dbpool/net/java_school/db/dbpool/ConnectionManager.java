package net.java_school.db.dbpool;

import java.sql.Connection;

public abstract class ConnectionManager {

	protected DBConnectionPoolManager poolManager;
	protected String poolName;
	
	public ConnectionManager() {
		this.poolManager = DBConnectionPoolManager.getInstance();
	}
	
	public Connection getConnection() {
		return (poolManager.getConnection(poolName));
	}

	public void freeConnection(Connection con) {
		poolManager.freeConnection(poolName, con);
	}
	
	public abstract void initPoolManager(String poolName, String driver, String url, String userID, String passwd, int maxConn, int initConn, int maxWait);
	
	public int getDriverNumber() {
        return poolManager.getDriverNumber();
    }

}