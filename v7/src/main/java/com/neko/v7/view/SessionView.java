package com.neko.v7.view;

import com.neko.v7.login.User;
import com.neko.v7.session.OPOR;
import com.neko.v7.session.UserSession;
import com.neko.v7.view.AbstractForm.DeleteHandler;
import com.neko.v7.view.AbstractForm.SaveHandler;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SessionView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "session";
	BeanItemContainer<OPOR> container;
	PurchaseOrderForm oporForm;
	
	public SessionView() {
		setHeight(100, Unit.PERCENTAGE);
		Grid grid = createGrid();
		grid.setSelectionMode(SelectionMode.SINGLE);
		
		createForm(); // Save, DeleteHandler 구현
		findBean(); // Grid의 BeanItemContainer에 데이터를 채워준다.
		
		addComponent(createHeader());
		addComponent(grid);
		setExpandRatio(grid, 1);
	}
	
	public HorizontalLayout createHeader() {
		Label title = new Label("Purchase Order");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		Button newBtn = new Button("New");
		newBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newBtn.setIcon(FontAwesome.PLUS_CIRCLE);
		newBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				oporForm.lazyInit(new OPOR());
				oporForm.openPopup("Purchase Order Create");
			}
		});
		
		HorizontalLayout header = new HorizontalLayout();
		header.setSpacing(true);
		header.setWidth(100, Unit.PERCENTAGE);
		header.addComponent(title);
		header.addComponent(newBtn);
		header.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		header.setExpandRatio(title, 1);
				
		return header;
	}
	
	public Grid createGrid() {		
		Grid grid = new Grid();
		grid.setSizeFull();
		grid.setContainerDataSource(container = new BeanItemContainer<>(OPOR.class));
		
		grid.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				oporForm.lazyInit((OPOR) event.getItemId());
				oporForm.openPopup("Edit Order");
			}
		});
		
		return grid;
	}
	
	private void createForm() {
		oporForm = new PurchaseOrderForm();
		oporForm.setSaveHandler(new SaveHandler<OPOR>() {
			@Override
			public void onSave(OPOR entity) {
				
			}
		});
		
		oporForm.setDeleteHandler(new DeleteHandler<OPOR>() {
			@Override
			public void onDelete(OPOR entity) {
				
			}
		});
	}
	
	private void findBean() {
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
