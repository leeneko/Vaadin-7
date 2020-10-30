package com.neko.v7.screen;

import java.sql.SQLException;

import com.neko.v7.dbconn.DBConnection;
import com.neko.v7.login.UserNotFoundException;
import com.neko.v7.session.UserSession;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LoginScreen extends VerticalLayout {
	
	UserSession userSession;
	final VerticalLayout loginPannel = new VerticalLayout();
	Label titleLabel;
	Label errorLabel;
	TextField id;
	PasswordField password;
	
	public LoginScreen() {
		addStyleName("login-screen");
		userSession = new UserSession(); // 유저 세션 처리를 위한 객체 선언
		setSizeFull();
		Component loginForm = buildForm();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}
	
	private Component buildForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.addStyleName("login-panel");
		
		loginPannel.setSizeUndefined();
		loginPannel.setSpacing(true);
		loginPannel.addComponent(buildLabels());
		loginPannel.addComponent(buildFields());
		
		return loginPannel;
	}
	
	private Component buildLabels() {
		titleLabel = new Label("Harim DEV");
		titleLabel.addStyleName(ValoTheme.LABEL_H2);
		titleLabel.addStyleName(ValoTheme.LABEL_BOLD);
		titleLabel.addStyleName(ValoTheme.LABEL_COLORED);
		
		// 에러 메시지 라벨 (처음엔 숨기고 있다가 에러 발생 시 표시 되도록)
		errorLabel = new Label();
		errorLabel.addStyleName(ValoTheme.LABEL_FAILURE);
		errorLabel.setVisible(false); // 처음에는 에러 라벨 숨기기
		
		return titleLabel;
	}
	
	private Component buildFields() {
		id = new TextField("ID");
		id.setIcon(FontAwesome.USER);
		id.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		// id의 형식이 Email일 때 Email 형식 Validator, {0}은 email 입력 값으로 치환 된다.
		// id.addValidator(new EmailValidator("Invalid e-mail address {0}"));
		
		password = new PasswordField("Password");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		
		final Button signin = new Button("Sign In");
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.focus();
		signin.setClickShortcut(KeyCode.ENTER);
		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				DBConnection dbconn = new DBConnection();
				try {
					// 로그인 처리 구현(id.getValue(), password.getValue())
					// DB에 ID로 기준정보 조회하여 PW 비교
					String input_id = id.getValue();
					String input_pw = password.getValue();
					try {
						String sql = "EXEC confirm_reg_info '" + input_id + "';";
						dbconn.conn = dbconn.getConnection();
						dbconn.pst = dbconn.conn.prepareStatement(sql);
						dbconn.rs = dbconn.pst.executeQuery();
						
						if (dbconn.rs.next()) { // db에서 아이디 조회 됨
							String dbid = dbconn.rs.getString(1).trim();
			                String dbpw = dbconn.rs.getString(2).trim();
			                String dblv = dbconn.rs.getString(3).trim();
			                
			                if (input_pw.equals(dbpw)) { // 입력한 비밀번호와 db의 비밀번호 일치
			                	userSession.signin(dbid, dbpw); // 로그인 확인 처리
			                	Page.getCurrent().reload();
			                } else {
			                	Notification.show("warning", "로그인 실패, ID와 PW를 확인하여 다시 시도해주세요.", Notification.TYPE_WARNING_MESSAGE);
			                }
						} else {
							Notification.show("error", "등록되지 않은 ID입니다. 확인하여 다시 시도해주세요.", Notification.TYPE_ERROR_MESSAGE);
						}
					} finally {
						dbconn.close();
					}
					
					// 로그인 처리 후 화면을 리로드 하여 다시 TESTUI로 접근 처리
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				} catch (UserNotFoundException unfe) {
					// 실패한 사유를 사용자에게 알려 주기
					Notification.show("SignIn Failed :", unfe.getMessage(), Notification.TYPE_ERROR_MESSAGE);
					unfe.printStackTrace();
					// 숨겨뒀던 라벨에 메시지 표시
					// errorLabel.setValue(String.format("Login Failed: %s", unfe.getMessage()));
					// errorLabel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.addComponents(id, password, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
		
		return fields;
	}
}
