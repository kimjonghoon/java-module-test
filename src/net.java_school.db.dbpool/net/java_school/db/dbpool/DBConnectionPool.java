package net.java_school.db.dbpool; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPool {
	private static final Logger logger = Logger.getLogger(DBConnectionPool.class.getName());
	
	//Number of connections currently in use
	private int checkedOut;

	// Free Connection List
	private Vector<Connection> freeConnections = new Vector<Connection>();

	//Maximum number of connections
	private int maxConn;

	//Waiting time (maximum time to wait when there is no connection in the pool)
	private int maxWait;

	//Connection Pool Name
	private String poolName;

	//DB Password
	private String passwd;

	//DB URL
	private String URL;

	//DB UserID
	private String userID;

	//Constructor
	public DBConnectionPool(String poolName, 
			String URL, 
			String userID, 
			String passwd, 
			int maxConn, 
			int initConn, 
			int waitTime) {

		this.poolName = poolName;
		this.URL = URL;
		this.userID = userID;
		this.passwd = passwd;
		this.maxConn = maxConn;
		this.maxWait = waitTime;

		for (int i = 0; i < initConn; i++) {
			freeConnections.addElement(newConnection());
		}
	}

	//Returning Connection
	//@param con : Connection to return
	public synchronized void freeConnection(Connection con) {
		freeConnections.addElement(con);
		checkedOut--;
		//Notify thread waiting to get Connection
		notifyAll();
	}

	//Get Connection
	@SuppressWarnings("resource")
	public synchronized Connection getConnection() {
		Connection con = null;
		//If Connection is in Free List, get the first of List
		if (freeConnections.size() > 0) {
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);

			try {
				//If the connection is closed by the DBMS, request again
				if (con.isClosed()) {
					logger.log(Level.SEVERE, "Removed bad connection from " + poolName);
					con = getConnection();
				}
			} //If strange connection occurs, request again
			catch (SQLException e) {
				logger.log(Level.SEVERE, "Removed bad connection from " + poolName);
				con = getConnection();
			}
		} //If Connection is not in Free List, create new
		else if (maxConn == 0 || checkedOut < maxConn) {
			con = newConnection();
		}

		if (con != null) {
			checkedOut++;
		}

		return con;
	}

	//Get Connection
	//@param timeout : Maximum Wait Time to Obtain a Connection
	public synchronized Connection getConnection(long timeout) {
		long startTime = new Date().getTime();
		Connection con;
		while ((con = getConnection()) == null) {
			try {
				wait(timeout * maxWait);
			} catch (InterruptedException e) {}
			if ((new Date().getTime() - startTime) >= timeout) {
				//Wait timeout
				return null;
			}
		}

		return con;
	}

	//Get Connection
	private Connection newConnection() {
		Connection con = null;
		try {
			if (userID == null) {
				con = DriverManager.getConnection(URL);
			} else {
				con = DriverManager.getConnection(URL, userID, passwd);
			}
			logger.info("Created a new connection in pool " + poolName);
		} catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("Can't create a new connection for ");
			sb.append(URL);
			sb.append(" user: ");
			sb.append(userID);
			sb.append(" passwd: ");
			sb.append(passwd);
			logger.log(Level.SEVERE, sb.toString());
			return null;
		}

		return con;
	}

}