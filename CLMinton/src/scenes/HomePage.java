package scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import menubar.UserMenuBar;
import models.Product;
import utils.AlertHandler;
import utils.ConnectHandler;
import utils.Sizes;

public class HomePage {
	Scene scene;
	BorderPane bp;
	GridPane gp, insertGp;
	
	Label productList, productName, productBrand, productStock, productPrice, totalPrice;
	TableView<Product> productTbl;
	MenuBar menuBar;

	Spinner<Integer> quantity;
	Button addToCart;
	HBox tableToInsertHbox;
	VBox tableVbox;
	
	AlertHandler alert = new AlertHandler();
	ConnectHandler con = new ConnectHandler();
	
	//methods
	private void initialize() {
		bp = new BorderPane();
		tableVbox = new VBox();
		tableToInsertHbox = new HBox();
		gp = new GridPane();
		insertGp = new GridPane();
		
		productList = new Label("Product List");
		productName = new Label("Product Name\t : ");
		productBrand = new Label("Product Brand\t : ");
		productStock = new Label("Product Stock\t : ");
		productPrice = new Label("Product Price\t : ");
		totalPrice = new Label("Total Price\t : ");

		productTbl = new TableView<Product>();
		quantity = new Spinner<>();
		addToCart = new Button("Add to Cart");
		menuBar = new UserMenuBar("HOME");
		
		scene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setNode() {
		TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
		TableColumn<Product, String> brandCol = new TableColumn<Product, String>("Brand");
		TableColumn<Product, Integer> stockCol = new TableColumn<Product, Integer>("Stock");
		TableColumn<Product, Integer> priceCol = new TableColumn<Product, Integer>("Price");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
		brandCol.setCellValueFactory(new PropertyValueFactory<>("ProductMerk"));
		stockCol.setCellValueFactory(new PropertyValueFactory<>("ProductStock"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
		
		productTbl.setPrefSize(400, 400);
		nameCol.setPrefWidth(productTbl.getPrefWidth()/4);
		brandCol.setPrefWidth(productTbl.getPrefWidth()/4);
		stockCol.setPrefWidth(productTbl.getPrefWidth()/4);
		priceCol.setPrefWidth(productTbl.getPrefWidth()/4);
		
		productTbl.getColumns().addAll(nameCol, brandCol, stockCol, priceCol);
		
		refreshPage();
	}
	
	private void setComponent() {
		bp.setTop(menuBar);
		bp.setCenter(tableToInsertHbox);
		
		tableToInsertHbox.getChildren().addAll(tableVbox, insertGp);
		tableVbox.getChildren().addAll(productList, productTbl);
		insertGp.add(productName, 0, 0);
		insertGp.add(productBrand, 0, 1);
		insertGp.add(productPrice, 0, 2);
		insertGp.add(quantity, 0, 3);
		insertGp.add(totalPrice, 0, 4);
		insertGp.add(addToCart, 0, 5);
	}
	
	private void setStyle() {
		tableToInsertHbox.setAlignment(Pos.CENTER);
		
		//Arrange Main Component Height n Widht
		tableToInsertHbox.setSpacing(25);
		
		//Arrange Tableview
		tableVbox.setSpacing(15);
		tableVbox.setAlignment(Pos.CENTER);
		productList.setFont(new Font(18));
		
		//Arrange InsertVbox
		insertGp.setAlignment(Pos.CENTER);
		insertGp.setVgap(10);
	}
	
	private void setAction() {
		quantity.valueProperty().addListener(new ChangeListener<Integer>() {
			
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				TableViewSelectionModel<Product> selectedTbl = productTbl.getSelectionModel();
				if (selectedTbl.isEmpty()) return;
				else {
					int newTotalPrice = selectedTbl.getSelectedItem().getProductPrice() * arg2;
					totalPrice.setText("Total Price\t : " + newTotalPrice);
				}
			}
		});
		
		productTbl.setOnMouseClicked(e -> {
			TableViewSelectionModel<Product> selectedTbl = productTbl.getSelectionModel();
			if (selectedTbl.isEmpty()) return;
			else {
				changeNode(selectedTbl.getSelectedItem().getProductName(), selectedTbl.getSelectedItem().getProductMerk(), Integer.toString(selectedTbl.getSelectedItem().getProductPrice()), selectedTbl.getSelectedItem().getProductStock());
				int newTotalPrice = selectedTbl.getSelectedItem().getProductPrice();
				totalPrice.setText("Total Price\t : " + newTotalPrice);
			}
		});
		
		addToCart.setOnAction(e -> {
			TableViewSelectionModel<Product> selectedTbl = productTbl.getSelectionModel();
			if (selectedTbl.isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Please choose 1 item");
				return;
			}
			else {
				String productId = selectedTbl.getSelectedItem().getProductID();
				int qty = quantity.getValue();
				
				if (con.validateCartData(Main.currentUser, productId)) {
					con.updateCartData(Main.currentUser, productId, qty);
					con.updateStockData(productId, qty, false);
					alert.setAlert(AlertType.INFORMATION, "Information", "Product quantity in cart have been added!");
					refreshPage();
				}
				else {
					con.addData(Main.currentUser, productId, qty);
					con.updateStockData(productId, qty, false);
					refreshPage();
				}
			}
		});
	}
	
	//extra methods
	private void refreshPage() {
		productTbl.setItems(con.getProductData(false));
		changeNode("", "", "", 100);
		totalPrice.setText("Total Price\t : ");
		productTbl.refresh();
	}
	
	private void changeNode(String name, String brand, String price, int stock) {
		productName.setText("Product Name\t : " + name);
		productBrand.setText("Product Brand\t : " + brand);
		productPrice.setText("Product Price\t : " + price);
		
		quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, stock, 1));
	}
	
	public HomePage() {
		initialize();
		setNode();
		setComponent();
		setStyle();
		setAction();
		Main.currentStage.setTitle("Home");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
	}
}
