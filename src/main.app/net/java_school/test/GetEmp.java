package net.java_school.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.java_school.db.dbpool.mysql.MySqlConnectionManager;
import net.java_school.db.dbpool.oracle.OracleConnectionManager;

public class GetEmp {

	public static void main(String[] args) {

		OracleConnectionManager oracleConnectionManager = new OracleConnectionManager();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM emp";

		try {
			con = oracleConnectionManager.getConnection();
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

				System.out.println(empno + " : " + ename + " : " + job + " : " + mgr + " : " + hiredate + " : " + sal + " : " + comm + " : " + depno);
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
			oracleConnectionManager.freeConnection(con);
		}
		
		MySqlConnectionManager mysqlConnectionManager = new MySqlConnectionManager();
		
		con = null;
		stmt = null;
		rs = null;

		sql = "SELECT * FROM EMP";

		try {
			con = mysqlConnectionManager.getConnection();
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

				System.out.println(empno + " : " + ename + " : " + job + " : " + mgr + " : " + hiredate + " : " + sal + " : " + comm + " : " + depno);
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
			mysqlConnectionManager.freeConnection(con);
		}
		
		System.out.println("Driver Number: " + mysqlConnectionManager.getDriverNumber());
		
	}
}