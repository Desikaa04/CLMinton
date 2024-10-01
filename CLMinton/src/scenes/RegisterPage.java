package scenes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import menubar.LogRegMenuBar;
import utils.AlertHandler;
import utils.ConnectHandler;
import utils.Sizes;


public class RegisterPage {
	Scene scene;
	
	BorderPane borderPane;
	GridPane gridPane;
	FlowPane flowPane;
	VBox vBox;
	
	TextField emailField;
	PasswordField passwordField, confirmField;
	
	Label registerLbl, emailLbl, passwordLbl, confirmLbl, ageLbl, genderLbl, nationalityLbl;
	
	RadioButton maleRadio, femaleRadio;
	ToggleGroup genderGroup;
	
	Spinner<Integer> ageSpinner;
	
	ComboBox<String> nationalityBox;
	
	Button registerButton;
	
	MenuBar menuBar;
	
	AlertHandler alert = new AlertHandler();
	ConnectHandler con = new ConnectHandler();
	
	public void initialize() {
		borderPane = new BorderPane();
		scene = new Scene(borderPane, Sizes.SCENE_SIZES.getWidth(), Sizes.SCENE_SIZES.getHeight());
		gridPane = new GridPane();
		flowPane = new FlowPane();
		vBox = new VBox();
		
		emailField = new TextField();
		passwordField = new PasswordField();
		confirmField = new PasswordField();
		
		registerLbl = new Label("Register Form");
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		confirmLbl = new Label("Confirm Password");
		ageLbl = new Label("Age");
		genderLbl = new Label("Gender");
		nationalityLbl = new Label("Nationality");
		
		genderGroup = new ToggleGroup();
		maleRadio = new RadioButton("Male");
		femaleRadio = new RadioButton("Female");
		
		ageSpinner = new Spinner<>();
		nationalityBox = new ComboBox<>();
		
		registerButton = new Button("Register");
		
		menuBar = new LogRegMenuBar("REGISTER");
	}
	
	public void setNode() {		
		//combo box
		nationalityBox.getItems().add("Indonesia");
		nationalityBox.getItems().add("Inggris");
		nationalityBox.getItems().add("Irlandia");
		nationalityBox.getSelectionModel().selectFirst();
		
		//radio button
		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);
		maleRadio.setSelected(true);
		flowPane.getChildren().addAll(maleRadio, femaleRadio);
		
		//spinner
		SpinnerValueFactory<Integer> ageSpinnerInterval = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		ageSpinner.setValueFactory(ageSpinnerInterval);
	}
	
	public void setComponent() {
		gridPane.add(emailLbl, 0, 0);
		gridPane.add(emailField, 0, 1);
		
		gridPane.add(passwordLbl, 0, 2);
		gridPane.add(passwordField, 0, 3);
		
		gridPane.add(confirmLbl, 0, 4);
		gridPane.add(confirmField , 0, 5);
		
		gridPane.add(ageLbl, 0, 6);
		gridPane.add(ageSpinner, 0, 7);
		
		gridPane.add(genderLbl, 1, 0);
		gridPane.add(flowPane, 1, 1);
		
		gridPane.add(nationalityLbl, 1, 2);
		gridPane.add(nationalityBox, 1, 3);
	
		gridPane.add(registerButton, 1, 4);
		
		borderPane.setTop(menuBar);
		borderPane.setCenter(vBox);
		vBox.getChildren().addAll(registerLbl, gridPane);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(20);
		gridPane.setHgap(20);
	}
	
	private void setStyle() {
		vBox.setPadding(new Insets(75));
		vBox.setSpacing(25);
		vBox.setAlignment(Pos.TOP_CENTER);
		GridPane.setHalignment(registerLbl, HPos.CENTER);
		
		gridPane.setHgap(20);
		gridPane.setVgap(10);
		flowPane.setPrefWidth(150);
		flowPane.setHgap(20);
		
		registerLbl.setFont(new Font(20));
		emailLbl.setFont(new Font(14));
		passwordLbl.setFont(new Font(14));
		confirmLbl.setFont(new Font(14));
		ageLbl.setFont(new Font(14));
		genderLbl.setFont(new Font(14));
		nationalityLbl.setFont(new Font(14));

	}
	
	
	public void setAction() {
		registerButton.setOnMouseClicked(e -> {
			if (emailField.getText().isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Email must be filled");
			} 
			else if (!emailField.getText().endsWith("@gmail.com")) {
				alert.setAlert(AlertType.WARNING, "Warning", "Email must ends with '@gmail.com'");
			} 
			else if (passwordField.getText().isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Password must be filled");
			} 
			else if (passwordField.getLength() < 6) {
				alert.setAlert(AlertType.WARNING, "Warning", "Password must contain minimum 6 characters");
			}
			else if (confirmField.getText().isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Confirm password must be fill");
			}
			else if (!confirmField.getText().equals(passwordField.getText())) {
				alert.setAlert(AlertType.WARNING, "Warning", "Confirm password must be the same as Password");
			} 
			else if (ageSpinner.getValue() < 1) {
				alert.setAlert(AlertType.WARNING, "Warning", "Age must be greater than 0");
			} 
			else if (((RadioButton)genderGroup.getSelectedToggle()).getText().isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Gender must be selected");
			} 
			else if (nationalityBox.getValue().isEmpty()) {
				alert.setAlert(AlertType.WARNING, "Warning", "Nationality must be selected");
			} 
			else if(con.validateRegisterData(emailField.getText())) {
				alert.setAlert(AlertType.WARNING, "Warning", "Email already been registered");
			}
			else {
				String id = String.format("UA%03d", con.countData("msuser") + 1);
				con.addData(id, emailField.getText(), passwordField.getText(), ((RadioButton) genderGroup.getSelectedToggle()).getText(),nationalityBox.getValue(), ageSpinner.getValue());
				
				new LoginPage();
			}
		});
		
	}

	public RegisterPage() {
		initialize();
		setNode();
		setComponent();
		setStyle();
		setAction();
		Main.currentStage.setTitle("Register");
		Main.currentStage.setScene(scene);
		Main.currentStage.show();
	}
	
	
}
