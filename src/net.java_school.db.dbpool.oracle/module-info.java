module net.java_school.db.dbpool.oracle {
  requires net.java_school.db.dbpool;
	requires net.java_school.db.dbpool.api;
	
	provides net.java_school.db.dbpool.api.ConnectionManageable
		with net.java_school.db.dbpool.oracle.OracleConnectionManager;
}
