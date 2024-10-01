package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHandler {
	private Alert alert;
	
	public void setAlert(AlertType at, String headerText, String contentText) {
		alert = new Alert(at);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
}
