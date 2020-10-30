package com.neko.v7.view;

import java.io.Serializable;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class AbstractForm<T> extends CustomComponent {
	private Window window;
	protected abstract void save(Button.ClickEvent e);
	protected abstract void delete(Button.ClickEvent e);
	
	public Window openPopup(String title) {
		window = new Window(title, this);
		window.setModal(true);
		window.setResizable(true);
		window.center();
		UI.getCurrent().addWindow(window);
		
		return window;
	}
	
	public interface SaveHandler<T> extends Serializable {
		void onSave(T entity);
	}
	
	public interface DeleteHandler<T> extends Serializable {
		void onDelete(T entity);
	}
	
	private SaveHandler<T> saveHandler;
	private DeleteHandler<T> deleteHandler;
	
	public SaveHandler<T> getSaveHandler() {
		return saveHandler;
	}
	
	public DeleteHandler<T> getDeleteHandler() {
		return deleteHandler;
	}
	
	public void setSaveHandler(SaveHandler<T> saveHandler) {
		this.saveHandler = saveHandler;
	}
	
	public void setDeleteHandler(DeleteHandler<T> deleteHandler) {
		this.deleteHandler = deleteHandler;
	}
	
	public void closePopup() {
		if (window != null) {
			window.close();
			window = null;
		}
	}
}
