module coloretto {
	exports gui;
	exports main;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	
	opens gui to javafx.graphics;
}