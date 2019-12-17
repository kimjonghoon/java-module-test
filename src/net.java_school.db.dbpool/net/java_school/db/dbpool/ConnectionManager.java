package net.java_school.db.dbpool;

import java.sql.Connection;

import net.java_school.db.dbpool.api.ConnectionManageable;

public abstract class ConnectionManager implements ConnectionManageable {

	protected DBConnectionPoolManager poolManager;
	protected String poolName;

	public ConnectionManager() {
		this.poolManager = DBConnectionPoolManager.getInstance();
	}

	@Override	
	public Connection getConnection() {
		return (poolManager.getConnection(poolName));
	}

	@Override
	public void freeConnection(Connection con) {
		poolManager.freeConnection(poolName, con);
	}

	public abstract void initPoolManager(String poolName, String driver, String url, String userID, String passwd, int maxConn, int initConn, int maxWait);
	
	@Override	
	public int getDriverNumber() {
		return poolManager.getDriverNumber();
	}

}
