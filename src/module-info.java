module coloretto {
	exports gui;
	exports main;
	exports domein;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires java.sql;
	
	opens gui to javafx.graphics;
}