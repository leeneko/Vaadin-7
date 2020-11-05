package com.neko.v7.view;

import com.neko.v7.login.UserData;
import com.neko.v7.session.OPOR;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PurchaseOrderForm extends AbstractForm<OPOR> {
	UserData userData;
	BeanFieldGroup<OPOR> fieldGroup;
	TextField docentry;	// private int DocEntry;
	ComboBox status;	// private String Status; // Open 생성됨, Canceled 취소됨, Closed 완료됨
	ComboBox docType;	// private String DocType; // I = Item, S = Service
	DateField docDate;	// private Date DocDate; // 생성일
	DateField docDueDate;	// private Date DocDueDate; // 종료일
	// private String Creator; // 생성자
	TextField description;	// private String Description; // 내용
	Button save;
	Button delete;
	
	public PurchaseOrderForm() {
		userData = UserData.getInstance(); // 현재 접속중인 계정 정보 불러오기
		fieldGroup = new BeanFieldGroup<OPOR>(OPOR.class);
		VerticalLayout root = new VerticalLayout();
		root.addComponent(createContent()); // 폼 필드
		root.addComponent(createFooter()); // 저장, 삭제 버튼
		setCompositionRoot(root);
	}
	
	public void lazyInit(OPOR order) {
		OPOR opor = new OPOR(order);
		fieldGroup.bindMemberFields(opor);
		fieldGroup.setItemDataSource(new BeanItem<OPOR>(opor));
		// Status(문서 상태값)에 따라 수정 가능, 불가능 처리
		docentry.setEnabled(false); // 기본적으로 DocEntry 는 수정 불가능
		if (!opor.getStatus().equals("Open")) { // Status가 Open이 아닐 경우 전부 수정 불가
			status.setEnabled(false);
			docType.setEnabled(false);
			docDate.setEnabled(false);
			docDueDate.setEnabled(false);
			description.setEnabled(false);
			delete.setVisible(false);
		}
		// 새 오더 생성일 경우, 마지막 DocEntry를 DB로부터 계산해서 가져온다.
		System.out.println("" + opor.getDocEntry());
		
	}
	
	private Component createContent() {
		HorizontalLayout content = new HorizontalLayout();
		content.setSpacing(true);
		content.setMargin(new MarginInfo(true, true, false, true));
		
		FormLayout form = new FormLayout();
		form.setSizeUndefined();
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.addComponent(docentry = new TextField("DocEntry"));
		
		
		return content;
	}
	
	private Component createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		
		return footer;
	}

	@Override
	protected void save(ClickEvent e) {
	}

	@Override
	protected void delete(ClickEvent e) {
	}

}
