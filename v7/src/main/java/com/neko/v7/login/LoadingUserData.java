package com.neko.v7.login;

import java.sql.SQLException;

import com.neko.v7.dbconn.DBConnection;

public final class LoadingUserData {
	static {
		loadUsers();
	}
	private static void loadUsers() {
		UserData userData = UserData.getInstance();
		// userData.save(new User("admin", "admin", RoleType.Admin));
		DBConnection dbconn = new DBConnection();
		try {
			String sql = "EXEC loading_user_data;";
			dbconn.conn = dbconn.getConnection();
			dbconn.pst = dbconn.conn.prepareStatement(sql);
			dbconn.rs = dbconn.pst.executeQuery();
			while (dbconn.rs.next()) {
				String id = dbconn.rs.getString(1).trim();
				String pw = dbconn.rs.getString(2).trim();
				String lv = dbconn.rs.getString(3).trim();
				RoleType role = null;
				if (lv.equals("Admin")) {
					role = RoleType.Admin;
				} else if (lv.equals("User")) {
					role = RoleType.User;
				}
				if (role != null) {
					userData.save( new User(null, id, pw, role) );
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbconn.close();
		}
	}
}
