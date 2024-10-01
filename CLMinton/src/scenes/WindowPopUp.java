package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import models.Cart;
import utils.ConnectHandler;
import utils.Sizes;

public class WindowPopUp {
	Window window;
	Scene windowScene;
	BorderPane bp;
	VBox vbox;
	
	Label listLbl, productLbl, courierLbl, totalLbl;
	ComboBox<String>courierBox;
	CheckBox insuranceBox;
	Button ckoutBtn;
	
	CartPage cart;
	int insuranceStatus = 0;
	ConnectHandler con = new ConnectHandler();
	
	//methods
	private void initialize() {
		window = new Window("TransactionCard");
		bp = new BorderPane();
		vbox = new VBox();
		
		listLbl = new Label("List");
		listLbl.setFont(new Font(30));
		productLbl = new Label();
		
		
		for (Cart cartList : cart.cartTable.getItems()) {
			productLbl.setText(productLbl.getText() + " " + cartList.getCartName() + "  : "+cartList.getCartTotal() +"\n" );
			}
		
		courierLbl = new Label("Courier");
		totalLbl = new Label(cart.totalLbl.getText());
		
		courierBox = new ComboBox<>();
		insuranceBox = new CheckBox("Use Insurance");
		
		ckoutBtn = new Button("Checkout");
		
		windowScene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setComponent() {
		courierBox.getItems().addAll("Gejok", "J&E", "JET","Nanji Express");
		courierBox.getSelectionModel().selectFirst();
		
		bp.setCenter(window);
		
		vbox.getChildren().addAll(listLbl, productLbl, courierLbl, courierBox, insuranceBox, totalLbl, ckoutBtn);
		window.getContentPane().getChildren().add(vbox);
	}
	
	private void setStyle() {
		courierBox.setMinWidth(200);
		ckoutBtn.setMinWidth(300);
		
		window.setTitle("Transaction Card");
		vbox.setAlignment(Pos.CENTER);
		
		window.setPadding(new Insets(50));
		vbox.setSpacing(20);
	}
	
	private void setAction() {
		insuranceBox.setOnAction(e -> {
			if (insuranceBox.isSelected()) {
				int getInsurance = cart.total + 90000;
				insuranceStatus = 1;
				totalLbl.setText("Total Price\t : " + getInsurance);
			} else {
				insuranceStatus = 0;
				totalLbl.setText(cart.totalLbl.getText());
			}
		});
		
		ckoutBtn.setOnAction(e -> {
			ckoutCart();
		});
		
	}
	
	//extra methods
	private void ckoutCart() {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setHeaderText("Confirmation");
		confirm.setContentText("Are you sure to Checkout all the item?");
		confirm.show();
		
		confirm.setOnCloseRequest(e -> {
			if (confirm.getResult() == ButtonType.OK) {
				int count = con.countData("transactionheader") + 1;
				String trId = String.format("TH%03d", count);
				
				con.addData(trId, Main.currentUser, insuranceStatus, courierBox.getValue());
				
				for (Cart cartList : con.getCartData()) {
					con.addDataDetail(cartList.getCartId(), trId, cartList.getCartQty());
				}
				
				con.removeAllCartData(Main.currentUser);
				
				try {
					new CartPage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (confirm.getResult() == ButtonType.CANCEL) {
				new CartPage();
			}
		});
	}
	
	public void start() {
		initialize();
		setComponent();
		setStyle();
		setAction();
		Main.currentStage.setTitle("Window Pop Up");
		Main.currentStage.setScene(windowScene);
		Main.currentStage.show();
	}
	
}
