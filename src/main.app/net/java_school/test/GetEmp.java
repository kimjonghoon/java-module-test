package net.java_school.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ServiceLoader;

import net.java_school.db.dbpool.api.ConnectionManageable;
//import net.java_school.db.dbpool.api.MySQL;
import net.java_school.db.dbpool.api.Oracle;

public class GetEmp {

	public static void main(String[] args) {

		ServiceLoader<ConnectionManageable> managers = ServiceLoader.load(ConnectionManageable.class);

		ConnectionManageable manager = managers.stream()
			.filter(provider -> isOracle(provider.type()))
			.map(ServiceLoader.Provider::get).findAny().get();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM EMP";

		try {
			con = manager.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String job = rs.getString(3);
				String mgr = rs.getString(4);
				String hiredate = rs.getString(5);
				String sal = rs.getString(6);
				String comm = rs.getString(7);
				String depno = rs.getString(8);

				System.out.println(empno + " : " + ename + " : " + job
						+ " : " + mgr + " : " + hiredate + " : " + sal
						+ " : " + comm + " : " + depno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {}
			try {
				stmt.close();
			} catch (SQLException e) {}
			manager.freeConnection(con);
		}

		System.out.println("Driver Number: " + 	manager.getDriverNumber());
	}	
	/*
	   private static boolean isMySQL(Class<?> clazz) {
	   return clazz.isAnnotationPresent(MySQL.class)
	   && clazz.getAnnotation(MySQL.class).value() == true;
	   }
	   */
	private static boolean isOracle(Class<?> clazz) {
		return clazz.isAnnotationPresent(Oracle.class)
			&& clazz.getAnnotation(Oracle.class).value() == true;
	}

}
