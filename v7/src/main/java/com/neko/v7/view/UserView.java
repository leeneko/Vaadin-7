package com.neko.v7.view;

import java.util.List;

import com.neko.v7.login.User;
import com.neko.v7.login.UserData;
import com.neko.v7.session.UserSession;
import com.neko.v7.view.AbstractForm.DeleteHandler;
import com.neko.v7.view.AbstractForm.SaveHandler;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "user";
	UserData userData;
	BeanItemContainer<User> container;
	UserForm userForm;
	
	public UserView() {
		userData = UserData.getInstance();
		
		setHeight(100, Unit.PERCENTAGE);
		Table table = createTable();
		createForm(); // UserForm의 Save, DeleteHandler를 구현
		findBean(); // Table의 BeanItemContainer에 데이터를 채워준다.
		
		addComponent(createTopBar());
		addComponent(table);
		// setExpandRatio(table, 1); // 화면에서 테이블 영역이 나머지 영역 모두 차지
		setExpandRatio(table, 1);
	}
	
	public HorizontalLayout createTopBar() {
		Label title = new Label("User");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		Button newBtn = new Button("New"); // 신규 유저 추가 버튼
		newBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newBtn.setIcon(FontAwesome.PLUS_CIRCLE);
		newBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// UserForm을 여기서 sub-window로 팝업 연동
				User user = new User();
				userForm.lazyInit(new User());
				userForm.openPopup("New User");
			}
		});
		
		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth(100, Unit.PERCENTAGE);
		topLayout.addComponents(title);
		topLayout.addComponents(newBtn);
		topLayout.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		topLayout.setExpandRatio(title, 1);
		
		return topLayout;
	}
	
	public Table createTable() {
		Table table = new Table();
		table.setSizeFull();
		// 빈 BeanItemContainer를 생성 후 테이블에 넘겨줘 기본 정보를 그리게 한다.
		table.setContainerDataSource(container = new BeanItemContainer<>(User.class));
		// setVisibleColumns 컬럼 순서 결정됨
		table.setVisibleColumns("id", "pw", "role");
		table.setColumnHeaders("ID", "PW", "권한");
		// table.setColumnWidth("role",  60);// 특정 컬럼 가로폭 지정
		
		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				userForm.lazyInit((User)event.getItemId());
				userForm.openPopup("Edit Profile");
			}
		});
		
		return table;
	}
	
	private void createForm() {
		userForm = new UserForm();
		userForm.setSaveHandler(new SaveHandler<User>() {
			@Override
			public void onSave(User entity) {
				userForm.closePopup();
				// 로그인한 사용자면 왼쪽 메뉴의 사용자명을 갱신하기 위해 화면 리로드
				if (UserSession.getUser().getId() == entity.getId()) {
					Page.getCurrent().reload();
					return;
				}
				findBean(); // 변경된 데이터를 동적 갱신
			}
		});
		
		userForm.setDeleteHandler(new DeleteHandler<User>() {
			@Override
			public void onDelete(User entity) {
				userForm.closePopup();
				findBean(); // 변경된 데이터를 동적 갱신
			}
		});
	}
	
	private void findBean() {
		List<User> users = userData.findAll();
		if (users.size() > 0) {
			container.removeAllItems();
		}
		container.addAll(users);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
