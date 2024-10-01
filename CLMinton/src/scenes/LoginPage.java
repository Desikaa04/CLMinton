package scenes;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Main;
import menubar.LogRegMenuBar;
import utils.AlertHandler;
import utils.ConnectHandler;
import utils.Sizes;

public class LoginPage {
	private Scene scene;
	private VBox vb;
	private BorderPane bp;
	private GridPane gp;
	
	private Label loginLbl, emailLbl, passwordLbl;
	private TextField emailField;
	private PasswordField passwordField;
	
	private Button loginButton;

	private MenuBar menuBar;
	
	private AlertHandler alert = new AlertHandler();
	private ConnectHandler con = new ConnectHandler();
	
	//methods
	private void initialize() {
		bp = new BorderPane();
		vb = new VBox();
		gp = new GridPane();
		
		emailField = new TextField();
		passwordField = new PasswordField();
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		loginLbl = new Label("Login Form");
		loginButton = new Button("Login");
		
		menuBar = new LogRegMenuBar("LOGIN");
		scene = new Scene(bp, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
	}
	
	private void setComponent() {
		//email
		gp.add(emailLbl, 0, 0);
		gp.add(emailField, 0, 1);
		
		//password
		gp.add(passwordLbl, 0, 2);
		gp.add(passwordField, 0, 3);
		 
		//button
		gp.add(loginButton, 0, 4);
	
		//borderpane
		bp.setTop(menuBar);
		bp.setCenter(vb);
		vb.getChildren().addAll(loginLbl, gp);
	}
	
	private void setStyle() {
		vb.setPadding(new Insets(125));
		vb.setSpacing(25);
		vb.setAlignment(Pos.TOP_CENTER);
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(20);
		gp.setVgap(20);
		GridPane.setHalignment(loginButton, HPos.CENTER);
		
		//Title
		loginLbl.setStyle("-fx-font-size: 20px;");
	}
	
	private void setAction() {
		loginButton.setOnMouseClicked(e -> {
			String email = emailField.getText();
			String pass = passwordField.getText();
			
			if (email.isEmpty() || pass.isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Email or Password must be Filled!");
				return;
			}
			
			boolean result = con.validateLoginData(email, pass);
			if(result) {
				if(email.contains("admin@gmail.com") && pass.equals("admin1234")) new ManageProductPage();
				else new HomePage();
			}
			else alert.setAlert(AlertType.ERROR, "Warning", "Wrong Email or Password!");
		});	
	}
	
	public LoginPage() {
		initialize();
		setComponent();
		setStyle();
		setAction();
		Main.currentStage.setTitle("Login");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
		
	}
}
