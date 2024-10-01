package main;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.LoginPage;

public class Main extends Application {
	public static Stage currentStage;
	public static String currentUser = "UA001";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		currentStage = arg0;
		currentStage.setResizable(false);
		new LoginPage();
	}

}
