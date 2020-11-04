package com.neko.v7.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SessionView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "session";
	
	public SessionView() {
		addComponent(createHeader());
		addComponent(createGrid());
	}
	
	public HorizontalLayout createTopBar() {
		Label title = new Label("Session");
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
	
	public HorizontalLayout createHeader() {
		HorizontalLayout header = new HorizontalLayout();
		
		return header;
	}
	
	public HorizontalLayout createGrid() {
		HorizontalLayout layout = new HorizontalLayout();
		
		Grid grid = new Grid();
		
		grid.addColumn("name", String.class);
		grid.addColumn("born", Integer.class);
		
		grid.addRow("Nicolaus Copernicus", 1543);
		grid.addRow("Galileo Galilei", 1564);
		grid.addRow("Johannes Kepler", 1571);
		
		layout.addComponent(grid);
		
		return layout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
