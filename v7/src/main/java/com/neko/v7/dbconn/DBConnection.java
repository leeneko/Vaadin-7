package com.neko.v7.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	public Connection conn;
	public PreparedStatement pst;
	public ResultSet rs;
	
	String user = "sa";
	String password = "dbadmin@123";
	String url = "jdbc:sqlserver://localhost:10161;databaseName=harimdev";
	
	public DBConnection() {
		conn = null;
		pst = null;
		rs = null;
	}
	
	public DBConnection(String sql) {
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 : " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("DB 접속 실패 : " + sqle.getMessage());
			sqle.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
