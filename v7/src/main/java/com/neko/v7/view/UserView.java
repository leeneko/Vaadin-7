package com.neko.v7.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "user";
	
	public UserView() {
		addComponent(createTopBar());
	}
	
	public HorizontalLayout createTopBar() {
		Label title = new Label("User");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth(100, Unit.PERCENTAGE);
		topLayout.addComponents(title);
		topLayout.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
		return topLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
