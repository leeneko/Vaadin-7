package com.neko.v7;

import javax.servlet.annotation.WebServlet;

import com.neko.v7.login.LoadingUserData;
import com.neko.v7.menu.LoadingMenuData;
import com.neko.v7.screen.LoginScreen;
import com.neko.v7.screen.MainScreen;
import com.neko.v7.session.UserSession;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
public class TestUI extends UI {
	private static final LoadingUserData userData = new LoadingUserData();
	private static final LoadingMenuData menuData = new LoadingMenuData();

    @Override
    protected void init(VaadinRequest request) {
		Responsive.makeResponsive(this); // 반응형 웹 적용
    	if (UserSession.isSignedIn()) { // Session에 값이 있으면 메인 스크린으로
    		setContent(new MainScreen(this));
    		// 현재 요청된 주소(location) 값에 맞게 뷰를 동적으로 전환
    		getNavigator().navigateTo(getNavigator().getState());
    		return;
    	}
    	setContent(new LoginScreen()); // Session에 값이 없으면 로그인 스크린으로
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TestUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	/* ServerSIDE
    	 * VaadinService.getCurrent() - HTTP의 Request에 대한 VaadinRequest, VaadinResponse 정보를 제공, Session, Cookie 제어 가능
    	 * Page.getCurrent().get... - 현재 접근된 웹 브라우저의 사이즈, 타이틀, 윈도우 이름과 같은 정보와 접근된 주소 정보, 주소이동과 관련된 기능을 제공
    	 * UI.getCurrent().get... - Locale(다국어 지원) 설정, URI 처리를 담당하는 바딘의 Navigator 내장 객체와 Page, VaadinSession도 제공
    	 */
    }
}
