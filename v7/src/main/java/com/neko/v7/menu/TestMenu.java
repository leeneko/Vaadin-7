package com.neko.v7.menu;

import java.util.Iterator;
import com.neko.v7.menu.TestNavigator;
import com.neko.v7.login.RoleType;
import com.neko.v7.login.User;
import com.neko.v7.session.UserSession;
import com.neko.v7.view.AbstractForm.SaveHandler;
import com.neko.v7.view.DashboardView;
import com.neko.v7.view.UserForm;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class TestMenu extends CssLayout {
	private CssLayout menuPart; // 메뉴들을 담을 루트 레이아웃
	private CssLayout menuItems; // 메뉴 아이템 리스트 담을 레이아웃
	private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
	private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
	private static final String VALO_MENUITEMS = "valo-menuitems";
	
	public TestMenu(final TestNavigator navigator) {
		setPrimaryStyleName(ValoTheme.MENU_ROOT);
		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);
		
		final Label title = new Label("<h3>Harim <strong>DEV</strong></h3>", ContentMode.HTML);
		title.setSizeUndefined();
		
		final HorizontalLayout top = new HorizontalLayout();
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		top.setSpacing(true);
		top.addComponent(title);
		
		menuPart.addComponent(top);
		
		final MenuBar userMenu = new MenuBar(); // 사용자 메뉴 생성
		userMenu.addStyleName("user-menu");
		menuPart.addComponent(userMenu);
		
		final MenuItem userMenuItem = userMenu.addItem(UserSession.getUser().getId(), null);
		userMenuItem.addItem("Edit Profile", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				final UserForm userForm = new UserForm();
				userForm.lazyInit(UserSession.getUser());
				userForm.openPopup("Edit Profile");
				userForm.setSaveHandler(new SaveHandler<User>() {
					@Override
					public void onSave(User entity) {
						userForm.closePopup();
						Page.getCurrent().reload();
						UserSession.signout();
					}
				});
				
			}
		});
		
		userMenuItem.addItem("Sign Out", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UserSession.signout();
			}
		});
		
		// 반응형 웹 적용시 나타날 메뉴 네비 버튼
		final Button showMenu = new Button("Menu");
		showMenu.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
					menuPart.removeStyleName(VALO_MENU_VISIBLE);
				} else  {
					menuPart.addStyleName(VALO_MENU_VISIBLE);
				}
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName(VALO_MENU_TOGGLE);
		showMenu.setIcon(FontAwesome.NAVICON);
		
		menuPart.addComponent(showMenu);
		
		// 메뉴 아이템 리스트 레이아웃
		menuItems = new CssLayout();
		menuItems.setPrimaryStyleName(VALO_MENUITEMS);
		RoleType sectionType = null;
		for (Navi item: navigator.getActiveNaviMaps().values()) {
			final String fragment = item.getFragment();	// #!{fragment} 주소
			final String viewName = item.getViewName(); // 메뉴명
			final Class<? extends View> viewClass = item.getViewClass(); // View
			final Resource icon = item.getIcon(); // 메뉴명 아이콘
			final RoleType roleType = item.getRoleType(); // 접근권한(USER, ADMIN)
			
			if (viewClass != DashboardView.class && sectionType != roleType) {
				sectionType = roleType;
				Label label = new Label("Role_" + sectionType.name(), ContentMode.HTML);
				label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
				label.addStyleName("h4");
				label.setSizeUndefined();
				// 메뉴 아이템 그룹(USER, ADMIN)별 언더라인 스타일 추가
				menuItems.addComponent(label);
			}
			
			final Button naviBtn = new Button(viewName);
			naviBtn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(fragment);
				}
			});
			naviBtn.setData(fragment);
			naviBtn.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			naviBtn.setIcon(icon);
			menuItems.addComponent(naviBtn);
		}
		
		// 권한에 맞게 정리된 메뉴 아이템 리스트를 menuPart에 추가
		menuPart.addComponent(menuItems);
		
		addComponent(menuPart);
	}

	public void setSelectedItem(String viewName) {
		if(menuItems.getComponentCount() <= 0) {
			return;
		}
		for (final Iterator<Component>it = menuItems.iterator(); it.hasNext();) {
			final Component item = it.next();
			if (item instanceof Button) {
				final Button naviBtn = (Button)item;
				// 메뉴 아이템 리스트의 버튼에 대해 selected 상태 해제
				naviBtn.removeStyleName("selected");
				String fragment = (String)naviBtn.getData();
				if (fragment.equals(viewName)) {
					// parameter로 넘어온 메뉴 아이템 버튼만 selected 상태 처리
					item.addStyleName("selected");
				}
			}
		}
		
		// 반응형 웹 적용시 모바일 크기에서는 메뉴 클릭 후 메뉴 파트 영역 숨기기 처리
		menuPart.removeStyleName(VALO_MENU_VISIBLE);
	}
}
