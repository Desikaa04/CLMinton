package menubar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import scenes.CartPage;
import scenes.HistoryPage;
import scenes.HomePage;
import scenes.LoginPage;

public class UserMenuBar extends MenuBar {
	public static String CURRENT_LOCATION;
	
	Menu menu;
	MenuItem home,cart,history,logout;
	
	private void initialize() {
		menu = new Menu("Page");
		home = new MenuItem("Home");
		cart = new MenuItem("Cart");
		history = new MenuItem("History");
		logout = new MenuItem("Logout");
	}
	
	private void setComponent() {
		getMenus().add(menu);
		menu.getItems().addAll(home,cart,history,logout);
	}
	
	private void setAction() {
		home.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("HOME")) new HomePage();
		});
		
		cart.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("CART")) new CartPage();
		});
		
		history.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("HISTORY")) new HistoryPage();
		});
		
		logout.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("LOGOUT")) new LoginPage();
		});
	}
	
	public UserMenuBar(String loc) {
		initialize();
		setComponent();
		setAction();
		UserMenuBar.CURRENT_LOCATION = loc;
	}
	
}
