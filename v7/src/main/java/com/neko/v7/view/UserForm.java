package com.neko.v7.view;

import com.neko.v7.dbconn.DBConnection;
import com.neko.v7.login.User;
import com.neko.v7.login.UserData;
import com.neko.v7.session.UserSession;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserForm extends AbstractForm<User> {
	UserData userData;
	TextField id;
	PasswordField password;
	BeanFieldGroup<User> fieldGroup;
	Button save;
	Button delete;
	
	public UserForm() {
		userData = UserData.getInstance();
		fieldGroup = new BeanFieldGroup<User>(User.class);
		VerticalLayout root = new VerticalLayout();
		root.addComponent(createContent()); // 폼 필드
		root.addComponent(createFooter()); // 저장, 삭제 버튼
		// CustomComponent 상속시 반드시 setCompositionRoot 최종 객체를 채워야 함.
		setCompositionRoot(root);
	}
	public void lazyInit(User user) {
		User item = new User(user);
		// item 정보를 fieldGroup bind 처리
		// fieldGroup.bind(id, "id");
		// fieldGroup.bind(password, "password");
		// fieldGroup.bind(role, "role");
		fieldGroup.bindMemberFields(item); // 개별적 bind 처리를 한번에 적용
		fieldGroup.setItemDataSource(new BeanItem<User>(item)); // fieldGroup에 데이터 그룹화
		
		// 만약 신규 유저로 값이 없으면 null 대신 ""로 표시
		id.setNullRepresentation("");
		password.setNullRepresentation("");
		
		// fieldGroup.commit()시 validation 처리
		id.addValidator(new NullValidator("required id", false));
		password.addValidator(new NullValidator("required password", false));
		
		// 수정 금지 처리
		id.setEnabled(item.getId() == null);
		
		// 로그인한 사용자 또는 신규 유저 생성시에는 삭제 버튼 감추기
		delete.setVisible(item.getId() != UserSession.getUser().getId() && item.getId() != null);
	}
	private Component createContent() {
		// id, pw
		HorizontalLayout content = new HorizontalLayout();
		content.setSpacing(true);
		content.setMargin(new MarginInfo(true, true, false, true));
		
		FormLayout form = new FormLayout();
		form.setSizeUndefined();
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.addComponent(id = new TextField("Id"));
		id.setValue(UserSession.getUser().getId());
		form.addComponent(password = new PasswordField("Password"));
		
		content.addComponent(form);
		return content;
	}
	private Component createFooter() {
		// save, delete button
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setSpacing(true);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100, Unit.PERCENTAGE);
		
		save = new Button("Save");
		save.addStyleName(ValoTheme.BUTTON_PRIMARY);
		save.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// user Profile Edit Process
				save(event); // save Button 클릭 시 save(Button.ClickEvent e) 메서드 호출
			}
		});
		
		delete = new Button("Delete");
		delete.addStyleName(ValoTheme.BUTTON_DANGER);
		delete.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// user Profile Delete Process
				delete(event);
			}
		});
		
		footer.addComponents(save, delete);
		footer.setExpandRatio(save, 1);
		footer.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
		
		return footer;
	}
	@Override
	protected void save(ClickEvent e) {
		DBConnection dbconn = new DBConnection();
		try {
			fieldGroup.commit();
			User item = fieldGroup.getItemDataSource().getBean();
			User entity = userData.save(item);
			entity.setPw(password.getValue()); // 변경된 비밀번호
			
			if (UserSession.getUser().getId() == entity.getId()) {
				UserSession.setUser(entity);
			}
			String sql = "SET NOCOUNT ON; EXEC update_reg_user '" + entity.getId() + "', '" + entity.getPw() + "';";
			// System.out.println(sql);
			dbconn.conn = dbconn.getConnection();
			dbconn.pst = dbconn.conn.prepareStatement(sql);
			dbconn.pst.executeUpdate();
			
			getSaveHandler().onSave(entity);
		} catch (CommitException | IllegalArgumentException ex) {
			Notification.show("Error while updating profile", ex.getMessage(), Type.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbconn.close();
		}
	}
	@Override
	protected void delete(ClickEvent e) {
		DBConnection dbconn = null;
		try {
			User item = fieldGroup.getItemDataSource().getBean();
			userData.delete(item.getSession());
			getDeleteHandler().onDelete(item);
			
			dbconn = new DBConnection();
			String input_id = UserSession.getUser().getId();
			String sql = "EXEC delete_reg_user '" + input_id + "';";
			System.out.println("NEKO] EXECUTE SQL : " + sql);
			dbconn.conn = dbconn.getConnection();
			dbconn.pst = dbconn.conn.prepareStatement(sql);
			dbconn.pst.execute();
		} catch (Exception ex) {
			Notification.show("Error while deleting profile", ex.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			dbconn.close();
		}
	}
}