package menubar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import scenes.LoginPage;
import scenes.ManageProductPage;
import scenes.ViewHistoryPage;

public class AdminMenuBar extends MenuBar {
	public static String CURRENT_LOCATION;
	
	Menu menu;
	MenuItem manageProduct;
	MenuItem viewHistory;
	MenuItem logOut;
	
	private void initialize() {
		menu = new Menu("Admin");
		viewHistory = new MenuItem("View History");
		manageProduct = new MenuItem("Manage Product");
		logOut = new MenuItem("Logout");
	}
	
	private void setComponent() {
		getMenus().add(menu);
		menu.getItems().addAll(manageProduct, viewHistory, logOut);
	}
	
	private void setAction() {
		manageProduct.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("MANAGE")) new ManageProductPage();
		});
		
		viewHistory.setOnAction(e -> {
			if(!CURRENT_LOCATION.equals("VIEW")) new ViewHistoryPage();
		});
		
		logOut.setOnAction(e -> {
			new LoginPage();
		});
	}
	
	public AdminMenuBar(String loc) {
		initialize();
		setComponent();
		setAction();
		
		AdminMenuBar.CURRENT_LOCATION = loc;
	}
}
