package net.java_school.db.dbpool;

import java.sql.Connection;

public interface ConnectionManageable {
	
	public Connection getConnection();

	public void freeConnection(Connection con);

	public int getDriverNumber();

}
