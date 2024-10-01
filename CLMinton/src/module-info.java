
module Final_Project_BAD {
	requires javafx.graphics;
	requires java.sql;
	requires javafx.controls;
	requires jfxtras.labs;
	
	opens main;
	opens menubar;
	opens connect;
	opens scenes;
	opens utils;
	opens models;
}