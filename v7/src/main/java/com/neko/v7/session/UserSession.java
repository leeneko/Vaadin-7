package com.neko.v7.session;

import java.io.Serializable;

import com.neko.v7.login.User;
import com.neko.v7.login.UserData;
import com.neko.v7.login.UserNotFoundException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;

public class UserSession implements Serializable {
	// Session Key
	public static final String SESSION_KEY = UserSession.class.getCanonicalName();
	
	private UserData userData;
	
	public UserSession() {
		this.userData = UserData.getInstance();
	}
	
	// 현재 세션의 로그인 사용자 정보 가져오기
	public static User getUser() {
		User user = (User) getCurrentSession().getAttribute(SESSION_KEY);
		return user;
	}
	
	// 현재 세션의 로그인 사용자 정보 채우기
	public static void setUser(User user) {
		if (user == null) {
			getCurrentSession().removeAttribute(SESSION_KEY);
		} else {
			getCurrentSession().setAttribute(SESSION_KEY, user);
		}
	}
	
	// 현재 세션의 로그인 유무 확인
	public static boolean isSignedIn() {
		return getUser() != null;
	}
	
	// 로그인 처리
	public void signin(String id, String pw) {
		User user = userData.findByIdAndPw(id, pw);
		if (user.getSession() == null) {
			throw new UserNotFoundException("user not found");
		}
		setUser(user); // 현재 세션에 로그인 사용자 정보 담기
	}
	
	// 로그아웃
	public static void signout() {
		getCurrentSession().invalidate(); // 현재 세션 무효화 처리
		com.vaadin.server.Page.getCurrent().reload(); // 현재 페이지 리로딩
	}
	
	// 바딘의 세션 객체
	private static WrappedSession getCurrentSession() {
		VaadinRequest request = VaadinService.getCurrentRequest();
		if (request == null) {
			throw new IllegalStateException("No request bound to current thread");
		}
		WrappedSession session = request.getWrappedSession();
		if (session == null) {
			throw new IllegalStateException("No Session bound to current thread");
		}
		return session;
	}
	
	/* VaadinService 클래스를 이용한 Cookie 사용법
	 * Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
	 * Cookie myCookie = new Cookie("cookie-name", "cookie-value");
	 * myCookie.setMaxAge(120);
	 * myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
	 * VaadinService.getCurrentResponse().addCookie(myCookie);
	 */
}
