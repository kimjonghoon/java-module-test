module main.app {
	requires java.sql;
	requires net.java_school.db.dbpool.oracle;
	requires net.java_school.db.dbpool.mysql;
	uses net.java_school.db.dbpool.oracle.OracleConnectionManager;
	uses net.java_school.db.dbpool.mysql.MySqlConnectionManager;
}