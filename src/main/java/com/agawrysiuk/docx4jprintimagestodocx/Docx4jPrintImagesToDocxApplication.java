package com.agawrysiuk.docx4jprintimagestodocx;

import com.agawrysiuk.docx4jprintimagestodocx.controller.Controller;
import com.agawrysiuk.docx4jprintimagestodocx.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Docx4jPrintImagesToDocxApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Controller controller = new Controller();
		View view = new View(controller);
		controller.setView(view.getView());

		Scene scene = new Scene(view.asParent(), 400, 400);
		stage.setTitle("Magic Card Printer");
//		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}

}
