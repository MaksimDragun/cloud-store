package net.dragberry.cloudstore.navigation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.dragberry.cloudstore.auth.LoginBean;
import net.dragberry.cloudstore.menu.MainMenu;
import net.dragberry.cloudstore.menu.MenuServiceLocal;

@Named("menuBean")
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -5171353882026148821L;
	
	private MainMenu mainMenu;
	
	@Inject
	private MenuServiceLocal menuService;
	
	@Inject
	private LoginBean loginBean;
	
	@PostConstruct
	public void initialise() {
		mainMenu = menuService.initMainMenu(loginBean.getCustomer());
	}
	
	public void reloadMenu() {
		mainMenu = menuService.initMainMenu(loginBean.getCustomer());
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}

}
