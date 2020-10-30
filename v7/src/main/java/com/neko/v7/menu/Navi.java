package com.neko.v7.menu;

import com.neko.v7.login.RoleType;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public class Navi {
	private String fragment; // vaadin-semina/#!{fragment} 주소
	private String viewName; // 메뉴명
	private Class<? extends View> viewClass; // View 클래스
	private Resource icon; // 메뉴명 아이콘
	private RoleType roleType; // 접근 권한(USER, ADMIN)
	
	public Navi(String fragment, String viewName, Class<? extends View> viewClass, Resource icon, RoleType roleType) {
		super();
		this.fragment = fragment;
		this.viewName = viewName;
		this.viewClass = viewClass;
		this.icon = icon;
		this.roleType = roleType;
	}
	public String getFragment() {
		return fragment;
	}
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public Class<? extends View> getViewClass() {
		return viewClass;
	}
	public void setViewClass(Class<? extends View> viewClass) {
		this.viewClass = viewClass;
	}
	public Resource getIcon() {
		return icon;
	}
	public void setIcon(Resource icon) {
		this.icon = icon;
	}
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
