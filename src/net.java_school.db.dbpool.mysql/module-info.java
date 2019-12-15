module net.java_school.db.dbpool.mysql {
	requires net.java_school.db.dbpool;
	
	provides net.java_school.db.dbpool.ConnectionManager 
		with net.java_school.db.dbpool.mysql.MySqlConnectionManager;
}
