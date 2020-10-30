package com.neko.v7.login;

public class User {
	private Long session;
	private String id;
	private String pw;
	private RoleType role;
	
	public User() {
		this.role = RoleType.User;
	}
	public User(User other) {
		this.session = other.getSession();
		this.id = other.getId();
		this.pw = other.getPw();
		this.role = other.getRole();
	}
	public User(String id, String pw, RoleType role) {
		this.id = id;
		this.pw = pw;
		this.role = role;
	}
	public User(Long session, String id, String pw, RoleType role) {
		this.session = session;
		this.id = id;
		this.pw = pw;
		this.role = role;
	}
	public Long getSession() {
		return session;
	}
	public void setSession(Long session) {
		this.session = session;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [session=" + session + ", id=" + id + ", pw=" + pw + ", role=" + role + "]";
	}
}
