package scenes;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import menubar.UserMenuBar;
import models.Cart;
import utils.AlertHandler;
import utils.ConnectHandler;
import utils.Sizes;

public class CartPage {
	Scene scene;
	BorderPane bp, bp1;
	GridPane gp;
	VBox vbox;
	
	Label cartLbl, nameLbl, brandLbl, priceLbl, totalLbl;
	
	MenuBar menuBar;
	
	Button ckoutBtn, deleteBtn;
	
	TableView<Cart> cartTable;
	Cart selected;
	int total = 0;
	
	WindowPopUp window;
	
	AlertHandler alert = new AlertHandler();
	ConnectHandler con = new ConnectHandler();
	
	//methods
	private void initialize() {
		bp = new BorderPane();
		bp1 = new BorderPane();
		gp = new GridPane();
		vbox = new VBox();
		
		menuBar = new UserMenuBar("CART");
		
		cartLbl = new Label("Your Cart List");
		nameLbl = new Label("Name\t\t :");
		brandLbl = new Label("Brand\t\t :");
		priceLbl = new Label("Total\t\t :");
		totalLbl = new Label("Total Price\t :");
		totalLbl.setWrapText(false);
		
		ckoutBtn = new Button("Checkout");
		deleteBtn = new Button("Delete Product");
		
		cartTable = new TableView<>();
		
		scene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setNode() {
		TableColumn<Cart, String> nameCol = new TableColumn<Cart, String>("Name");
		TableColumn<Cart, String> brandCol = new TableColumn<Cart, String>("Brand");
		TableColumn<Cart, Integer> priceCol = new TableColumn<Cart, Integer>("Price");
		TableColumn<Cart, Integer> qtyCol = new TableColumn<Cart, Integer>("Quantity");
		TableColumn<Cart, Integer> totalCol = new TableColumn<Cart, Integer>("Total");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<Cart, String>("cartName"));
		brandCol.setCellValueFactory(new PropertyValueFactory<Cart, String>("cartBrand"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("cartPrice"));
		qtyCol.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("cartQty"));
		totalCol.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("cartTotal"));

		cartTable.setPrefWidth(400);
		nameCol.setPrefWidth(cartTable.getPrefWidth()/5);
		brandCol.setPrefWidth(cartTable.getPrefWidth()/5);
		priceCol.setPrefWidth(cartTable.getPrefWidth()/5);
		qtyCol.setPrefWidth(cartTable.getPrefWidth()/5);
		totalCol.setPrefWidth(cartTable.getPrefWidth()/5);
		
		cartTable.getColumns().addAll(nameCol, brandCol, priceCol, qtyCol, totalCol);
		
		refreshPage();
	}
	
	private void setComponent() {
		bp.setTop(menuBar);
		bp.setCenter(bp1);
		
		bp1.setTop(cartLbl);
		bp1.setCenter(gp);
		bp1.setBottom(vbox);
		bp1.setLeft(cartTable);

		gp.add(nameLbl, 0, 0);
		gp.add(brandLbl, 0, 1);
		gp.add(priceLbl, 0, 2);
		gp.add(totalLbl, 0, 3);
		
		vbox.getChildren().addAll(ckoutBtn, deleteBtn);
	}
	
	private void setStyle() {
		cartLbl.setFont(new Font(20));
		cartLbl.setPadding(new Insets(5));
		
		ckoutBtn.setPrefWidth(500);
		deleteBtn.setPrefWidth(500);

		vbox.setSpacing(10);

		gp.setVgap(10);
		
		bp1.setPadding(new Insets(50));
		BorderPane.setMargin(cartLbl, new Insets(0,0,0,200));
		BorderPane.setMargin(cartTable, new Insets(0,0,20,200));
		BorderPane.setMargin(gp, new Insets(0,0,0,20));
		BorderPane.setMargin(vbox, new Insets(0,0,50,0));
		
		vbox.setAlignment(Pos.CENTER);
	}
	
	private void setAction() {
		cartTable.setOnMouseClicked(e -> {
			if (cartTable.getSelectionModel().isEmpty()) {
				return;
			}
			else {
				selected = cartTable.getSelectionModel().getSelectedItem();
				changeNode(selected.getCartName(), selected.getCartBrand(), String.valueOf(selected.getCartPrice()));
			}
		});
		
		deleteBtn.setOnMouseClicked(e -> {
			if (validateDelete()) {
				con.removeCartData(Main.currentUser, selected.getCartId());
				con.updateStockData(selected.getCartId(), selected.getCartQty(), true);
				refreshPage();
			}
		});
		
		ckoutBtn.setOnAction(e -> {
			startWindow();
		});
		
	}
	
	//extra methods
	public void startWindow() {
		WindowPopUp window = new WindowPopUp();
		window.cart = this;
		if (validateCkout()) {
			window.start();
		}
	}
	
	private void refreshPage() {
		cartTable.setItems(con.getCartData());
		changeNode("", "", "");
		getTotalPrice();
		cartTable.refresh();
	}
	
	private void changeNode(String name, String brand, String price) {
		nameLbl.setText("Name\t\t : " + name);
		brandLbl.setText("Brand\t\t : " + brand);
		priceLbl.setText("Price\t\t\t : " + price);
	}
	
	private void getTotalPrice() {
		total = 0;
		ObservableList<Cart> cart = cartTable.getItems();
		
		for (Cart cartList : cart) {
			total += cartList.getCartTotal();
		}
		
		totalLbl.setText("Total Price\t : " + String.valueOf(total));
	}
	
	private Boolean validateDelete() {
		if (selected == null) {
			alert.setAlert(AlertType.WARNING, "Warning", "Please Select Product to Delete");
			return false;
		} 
		return true;
	}
	
	private Boolean validateCkout() {
		if(cartTable.getItems().isEmpty()) {
			alert.setAlert(AlertType.WARNING, "Warning", "Please insert item to your cart");
			return false;
		}
		return true;
	}
	
	public CartPage() {
		initialize();
		setNode();
		setComponent();
		setStyle();
		setAction();
		
		Main.currentStage.setTitle("Cart");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
	}
	
}
