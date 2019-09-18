module net.java_school.db.dbpool.oracle {
	requires net.java_school.db.dbpool;
	
	exports net.java_school.db.dbpool.oracle;
	provides net.java_school.db.dbpool.ConnectionManager with net.java_school.db.dbpool.oracle.OracleConnectionManager;
}