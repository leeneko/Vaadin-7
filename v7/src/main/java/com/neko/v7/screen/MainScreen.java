package com.neko.v7.screen;

import com.neko.v7.TestUI;
import com.neko.v7.menu.TestMenu;
import com.neko.v7.menu.TestNavigator;
import com.neko.v7.session.UserSession;
import com.neko.v7.view.AboutView;
import com.neko.v7.view.DashboardView;
import com.neko.v7.view.ErrorView;
import com.neko.v7.view.UserView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class MainScreen extends HorizontalLayout {
	public MainScreen(TestUI testUI) {
		Responsive.makeResponsive(this); // 반응형 웹 적용
		// .valo-menu-responsive(반응형 메뉴 스타일) CSS를 MainScreen에 추가
		addStyleName(ValoTheme.UI_WITH_MENU);
		
		// 동적으로 뷰(View)가 바뀌어 보이게 되는 빈 레이아웃
		CssLayout viewArea = new CssLayout();
		viewArea.setSizeFull();
		
		// 네비게이터 생성(로그인 권한에 맞게 뷰 리스트 등록)
		final TestNavigator navigator = new TestNavigator(testUI.getCurrent(), viewArea);
		// 메뉴 영역(로그인한 권한에 맞게 Menu Item 보여주기
		final TestMenu menuArea = new TestMenu(navigator);
		// 뷰 변경시 발생되는 이벤트 리스너
		ViewChangeListener viewChangeListener = new ViewChangeListener() {
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// 선택된 메뉴 아이템 Background를 진하게 처리하기
				menuArea.setSelectedItem(event.getViewName());
			}
		};
		// MainScreen에 메뉴 영역 + 동적 변경 뷰 영역 순서대로 추가
		addComponents(menuArea, viewArea);
		// 동적 변경 뷰 영역이 빈 영역 모두 사용하기
		setExpandRatio(viewArea, 1);
		setSizeFull();
		/*
		Label label = new Label(UserSession.getUser().getId());
		final Button signout = new Button("Sign Out"); // 로그아웃 버튼
		signout.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UserSession.signout();
			}
		});
		addComponent(label);
		addComponent(signout);
		*/
		
		//View가 동적으로 바뀌어 보이게 되는 빈 Layout
		CssLayout viewContainer = new CssLayout();
		navigator.setErrorView(ErrorView.class); // 네비게이터 동작 중 오류가 나면 에러 페이지로 이동
		
		// MainScreen의 HorizontalLayout에 viewContainer를 추가
		addComponent(viewContainer);
		
		navigator.navigateTo(UI.getCurrent().getNavigator().getState());
	}
}
