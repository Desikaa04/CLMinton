package scenes;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import menubar.AdminMenuBar;
import models.Product;
import utils.AlertHandler;
import utils.ConnectHandler;
import utils.Sizes;

public class ManageProductPage {
	Scene scene;
	BorderPane bp; 
	
	VBox tableVbox; 
	HBox tableToInsertHbox;
	GridPane udGp;
	GridPane insertGp;
	TableView<Product> productTable;
	Label title, productNameLbl, productBrandLbl,
	productPriceLbl, udProductNameLbl, udAddStockLbl, udDeleteProductLbl;
	TextField productName, productPrice;
	ComboBox<String> productBrand;
	Spinner<Integer> udProductQty;
	Button addStockBtn, deleteProductBtn, addProductBtn;
	MenuBar menuBar;
	
	ConnectHandler con = new ConnectHandler();
	AlertHandler alert = new AlertHandler();
	
	private void initialize() {
		bp = new BorderPane();
		tableVbox = new VBox();
		tableToInsertHbox = new HBox();
		udGp = new GridPane();
		insertGp = new GridPane();
		
		title = new Label("Product List");
		productNameLbl = new Label("Product Name");
		productBrandLbl = new Label("Product Brand");
		productPriceLbl = new Label("Product Price");
		udProductNameLbl = new Label("Name : ");
		udAddStockLbl = new Label("Add Stock");
		udDeleteProductLbl = new Label("Delete Product");
		
		productName = new TextField();
		productPrice = new TextField();
		productBrand = new ComboBox<>();
		udProductQty = new Spinner<>(1, 100, 1);
		addStockBtn = new Button("Add Stock");
		deleteProductBtn = new Button("Delete");
		addProductBtn = new Button("Add Product");
		
		productTable = new TableView();
		
		menuBar = new AdminMenuBar("MANAGE");

		scene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setNode() {
		//initialize PRODUCT TABLE
		TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
		TableColumn<Product, String> brandCol = new TableColumn<Product, String>("Brand");
		TableColumn<Product, Integer> stockCol = new TableColumn<Product, Integer>("Stock");
		TableColumn<Product, Integer> priceCol = new TableColumn<Product, Integer>("Price");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		brandCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productMerk"));
		stockCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productStock"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productPrice"));

		productTable.setStyle("-fx-font-size: 10;");
		productTable.setPrefSize(400, 300);
		nameCol.setPrefWidth(productTable.getPrefWidth() / 4);
		brandCol.setPrefWidth(productTable.getPrefWidth() / 4);
		stockCol.setPrefWidth(productTable.getPrefWidth() / 4);
		priceCol.setPrefWidth(productTable.getPrefWidth() / 4);
		
		productTable.getColumns().addAll(nameCol, brandCol, stockCol, priceCol);

		//initialize comboBox
		productBrand.getItems().addAll("Yonex", "Victor", "Li-Ning");
		productBrand.getSelectionModel().selectFirst();
		
		refreshPage();
	}
	
	private void setComponent() {
		bp.setTop(menuBar);
		bp.setCenter(tableToInsertHbox);
		bp.setBottom(udGp);
		
		tableToInsertHbox.getChildren().addAll(tableVbox, insertGp);
		tableVbox.getChildren().addAll(title, productTable);
		insertGp.add(productNameLbl, 0, 0);
		insertGp.add(productName, 0, 1);
		insertGp.add(productBrandLbl, 0, 2);
		insertGp.add(productBrand, 0, 3);
		insertGp.add(productPriceLbl, 0, 4);
		insertGp.add(productPrice, 0, 5);
		insertGp.add(addProductBtn, 0, 6);
		
		
		udGp.add(udProductNameLbl, 0, 0, 2, 1);
		udGp.add(udAddStockLbl, 0, 1);
		udGp.add(udDeleteProductLbl, 1, 1);
		udGp.add(udProductQty, 0, 2);
		udGp.add(addStockBtn, 0, 3);
		udGp.add(deleteProductBtn, 1, 3);
	}
	
	private void setStyle() {
		tableToInsertHbox.setAlignment(Pos.CENTER);
		
		//Arrange MAIN COMPONENT HEIGHT N WIDTH
		tableToInsertHbox.setSpacing(30);
		
		//Arrange TableView
		tableVbox.setSpacing(20);
		tableVbox.setAlignment(Pos.CENTER);
		title.setFont(new Font(18));
			
		//Arrange InsertVbox
		insertGp.setAlignment(Pos.CENTER);
		insertGp.setVgap(10);
		
		//Arrange UPDATE DELETE 
		udGp.setAlignment(Pos.CENTER);
		udGp.setVgap(10);
		udGp.setHgap(40);
		udGp.setPadding(new Insets(0, 0, 20, 0));
		udGp.setHalignment(udAddStockLbl, HPos.CENTER);
		udGp.setHalignment(udProductNameLbl, HPos.CENTER);
		udGp.setHalignment(udDeleteProductLbl, HPos.CENTER);
		udGp.setHalignment(addStockBtn, HPos.CENTER);
		udGp.setHalignment(deleteProductBtn, HPos.CENTER);
		udProductNameLbl.setFont(new Font(16));
	}
	
	private void setAction() {
		productTable.setOnMouseClicked(e -> {
			if (productTable.getSelectionModel().isEmpty()) {
				udProductNameLbl.setText("Name : ");
			}
			else {
				udProductNameLbl.setText("Name : " + productTable.getSelectionModel().getSelectedItem().getProductName());				
			}
		});
		
		deleteProductBtn.setOnAction(e -> {
			TableViewSelectionModel<Product> productList = productTable.getSelectionModel();
			if (productList.isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Please select a column");
				return;
			}
			else {
				String productId = productList.getSelectedItem().getProductID();
				con.removeProductData(productId);
				refreshPage();
			}
		});
		
		addStockBtn.setOnAction(e -> {
			TableViewSelectionModel<Product> productList = productTable.getSelectionModel();
			if (productList.isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Please select a column");
				return;
			}
			else {
				String productId = productList.getSelectedItem().getProductID();
				con.updateStockData(productId, udProductQty.getValue(), true);
				refreshPage();
			}
		});
		
		addProductBtn.setOnAction(e -> {
			String name = productName.getText();
			String brand = productBrand.getValue();
			String price = productPrice.getText();
			
			if (name.isEmpty() || brand.isEmpty() || price.isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Please fill all the textfield!");
			}
			else {
				String productId = String.format("PD%03d", con.countData("msproduct") + 1);

				con.addData(productId, name, brand, Integer.parseInt(price), 0);
				refreshPage();
			}
			
		});
	}
	
	//extra methods
	private void refreshPage() {
		productTable.getItems().clear();
		productTable.setItems(con.getProductData(true));
		udProductNameLbl.setText("Name : ");
		productName.clear();
		productPrice.clear();
		udProductQty.getValueFactory().setValue(0);
		productTable.refresh();
		productBrand.getSelectionModel().selectFirst();
	}
	
	public ManageProductPage() {
		initialize();
		setNode();
		setComponent();
		setStyle();
		setAction();
		
		Main.currentStage.setTitle("Manage Product");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
	}
	
}
