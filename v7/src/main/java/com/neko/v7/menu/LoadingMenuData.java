package com.neko.v7.menu;

import com.neko.v7.login.RoleType;
import com.neko.v7.view.*;
import com.vaadin.server.FontAwesome;

public class LoadingMenuData {
	static {
		loadMenus();
	}
	private static void loadMenus() {
		TestNavigator.naviMaps.put("",			new Navi(DashboardView.VIEW_NAME,	"Dashboard",	DashboardView.class,	FontAwesome.HOME,	RoleType.User));
		TestNavigator.naviMaps.put("session",	new Navi(SessionView.VIEW_NAME,		"Session",		SessionView.class,		FontAwesome.CUBE,	RoleType.User));
		TestNavigator.naviMaps.put("about",		new Navi(AboutView.VIEW_NAME,		"About",		AboutView.class,		FontAwesome.INFO,	RoleType.User));
		TestNavigator.naviMaps.put("user",		new Navi(UserView.VIEW_NAME,		"User",			UserView.class,			FontAwesome.USERS,	RoleType.Admin));
	}
}
