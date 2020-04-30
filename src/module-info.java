module coloretto {
	exports gui;
	exports main;
	exports domein;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	
	opens gui to javafx.graphics;
}