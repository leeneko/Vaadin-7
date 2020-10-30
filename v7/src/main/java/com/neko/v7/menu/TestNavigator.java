package com.neko.v7.menu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.neko.v7.login.RoleType;
import com.neko.v7.session.UserSession;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public class TestNavigator extends Navigator {
	// Menu Mock Data(key : fragment)
	public static final Map<String, Navi> naviMaps = new LinkedHashMap<>();
	
	// 현재 접근된 사용자의 메뉴 목록
	private Map<String, Navi> activeNaviMaps;
	
	public TestNavigator(UI ui, ComponentContainer container) {
		// 네비게이터가 처리 할 UI와 View 영역 넘겨주기
		super(ui, container);
		// 현재 로그인한 사용자의 권한 가져오기
		final RoleType UserRoleType = UserSession.getUser().getRole();
		
		activeNaviMaps = new LinkedHashMap<>();
		for (Navi item: naviMaps.values()) {
			if (UserRoleType.ordinal() >= item.getRoleType().ordinal()) {
				// 네비게이터에 뷰 등록
				super.addView(item.getFragment(), item.getViewClass());
				// 현재 사용자 메뉴 목록에 뷰 등록
				activeNaviMaps.put(item.getFragment(), item);
			}
		}
	}
	
	//현재 로그인한 사용자의 접근 가능한 Navi 목록
	public Map<String, Navi> getActiveNaviMaps() {
		return activeNaviMaps;
	}
}