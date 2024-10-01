package scenes;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import menubar.UserMenuBar;
import models.TransactionDetail;
import models.TransactionHeader;
import utils.ConnectHandler;
import utils.Sizes;

public class HistoryPage {
	Scene scene;
	BorderPane bp;
	VBox tableVbox;
	HBox tableHbox;
	GridPane gp, insertGp;
	
	MenuBar menuBar;
	Label myTransaction, transactionDetail, totalPrice;
	TableView<TransactionHeader> tbTransaction;
	TableView<TransactionDetail> tbDetail;
	
	ConnectHandler con = new ConnectHandler();
	
	private void initialize() {
		bp = new BorderPane();
		menuBar = new UserMenuBar("HISTORY");
		
		myTransaction = new Label("My Transaction");
		transactionDetail = new Label("Transaction Detail");
		totalPrice= new Label("Total Price");
		tbTransaction = new TableView<TransactionHeader>();
		tbDetail = new TableView<TransactionDetail>();
		tableHbox = new HBox();
		tableVbox = new VBox();
		
		gp = new GridPane();
		insertGp = new GridPane();
		
		scene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setNode() {
		TableColumn<TransactionHeader, String> idCol = new TableColumn<TransactionHeader, String>("ID");	
		TableColumn<TransactionHeader, String> emailCol = new TableColumn<TransactionHeader, String>("Email");	
		TableColumn<TransactionHeader, String> dateCol = new TableColumn<TransactionHeader, String>("Date");	
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		tbTransaction.setStyle("-fx-font-size: 10;");
		tbTransaction.setPrefSize(300, 450);
		idCol.setPrefWidth(tbTransaction.getPrefWidth()/3);
		emailCol.setPrefWidth(tbTransaction.getPrefWidth()/3);
		dateCol.setPrefWidth(tbTransaction.getPrefWidth()/3);
		
		tbTransaction.getColumns().addAll(idCol, emailCol, dateCol);
		
		TableColumn<TransactionDetail, String> idColl = new TableColumn<TransactionDetail, String>("ID");	
		TableColumn<TransactionDetail, String> productCol = new TableColumn<TransactionDetail, String>("Product");	
		TableColumn<TransactionDetail, String> priceCol = new TableColumn<TransactionDetail, String>("Price");	
		TableColumn<TransactionDetail, String> quantityCol = new TableColumn<TransactionDetail, String>("Quantity");	
		TableColumn<TransactionDetail, String> totalPriceCol = new TableColumn<TransactionDetail, String>("TotalPrice");	
		
		idColl.setCellValueFactory(new PropertyValueFactory<>("id"));
		productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalP"));
		
		tbDetail.setStyle("-fx-font-size: 10;");
		tbDetail.setPrefSize(600, 450);
		idCol.setPrefWidth(tbDetail.getPrefWidth()/5);
		productCol.setPrefWidth(tbDetail.getPrefWidth()/5);
		priceCol.setPrefWidth(tbDetail.getPrefWidth()/5);
		quantityCol.setPrefWidth(tbDetail.getPrefWidth()/5);
		totalPriceCol.setPrefWidth(tbDetail.getPrefWidth()/5);

		tbDetail.getColumns().addAll(idColl, productCol, priceCol, quantityCol, totalPriceCol);
		
		refreshTable();
	}
	
	private void setComponent() {
		bp.setTop(menuBar);
		bp.setCenter(tableHbox);
		bp.setBottom(insertGp);
		
		tableHbox.getChildren().addAll(tableVbox, gp);
		tableVbox.getChildren().addAll(myTransaction, tbTransaction);
		gp.add(transactionDetail, 0, 0);
		gp.add(tbDetail, 0, 1);
		gp.add(totalPrice, 0, 2);
	}
	
	private void setStyle() {
		tableHbox.setAlignment(Pos.CENTER);
		
		//Arrange main component height n width
		tableHbox.setSpacing(25);
		
		//Arrange tableView
		myTransaction.setFont(new Font(18));
		transactionDetail.setFont(new Font(18));
		
		//Arrange InsertVbox
		insertGp.setAlignment(Pos.CENTER);
		insertGp.setVgap(10);
		insertGp.setHgap(40);
		insertGp.setPadding(new Insets(0, 0, 50, 0));
		
		totalPrice.setFont(new Font(20));
	}
	
	private void setAction() {
		tbTransaction.setOnMouseClicked(e->{
			if (tbTransaction.getSelectionModel().getSelectedItems().isEmpty()) {
				return;
			}
			else {
				tbDetail.setItems(con.getTDData(tbTransaction.getSelectionModel().getSelectedItem().getId()));
				tbDetail.refresh();
				getTotalPrice();
			}
		});
	}
	
	//extra methods
	private void refreshTable() {
		tbTransaction.setItems(con.getTHData(Main.currentUser));
		tbTransaction.refresh();
	}
	
	private void getTotalPrice() {
		int total = 0;
		ObservableList<TransactionDetail> td = tbDetail.getItems();
		for (TransactionDetail tdList : td) {
			total += tdList.getTotalP();
		}
		totalPrice.setText("Total Price: " + total);
	}
	
	public HistoryPage() {
		initialize();
		setNode();
		setComponent();
		setStyle();
		setAction();
		
		Main.currentStage.setTitle("My History");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
	}
	
}
