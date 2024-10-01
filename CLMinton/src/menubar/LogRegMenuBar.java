package menubar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import scenes.LoginPage;
import scenes.RegisterPage;

public class LogRegMenuBar extends MenuBar {
	public static String CURRENT_LOCATION;
	
	Menu menu;
	MenuItem loginMI;
	MenuItem registerMI;
	
	private void initialize() {
		menu = new Menu("Page");
		loginMI = new MenuItem("Login");
		registerMI = new Menu("Register");
	}
	
	private void setComponent() {
		getMenus().add(menu);
		menu.getItems().addAll(loginMI, registerMI);
	}
	
	private void setAction() {
		loginMI.setOnAction(e -> {
			if (CURRENT_LOCATION.equals("REGISTER")) new LoginPage();
		});
		
		registerMI.setOnAction(e -> {
			if (CURRENT_LOCATION.equals("LOGIN")) new RegisterPage();
		});
		
	}
	
	public LogRegMenuBar(String loc) {
		initialize();
		setComponent();
		setAction();
		LogRegMenuBar.CURRENT_LOCATION = loc;
	}
}
