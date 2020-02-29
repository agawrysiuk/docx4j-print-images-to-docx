package com.agawrysiuk.docx4jprintimagestodocx.view;

import com.agawrysiuk.docx4jprintimagestodocx.controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import lombok.Data;

@Data
public class View {
    private BorderPane view;

    private Controller controller ;

    public View(Controller controller) {
        this.controller = controller ;
        createAndConfigurePane();
    }

    public Parent asParent() {
        return view;
    }

    private void createAndConfigurePane() {
        view = new BorderPane();

        TextArea area = new TextArea();
        view.centerProperty().set(area);

        Button button = new Button("Print!");
        button.setOnMouseClicked(mouseEvent -> controller.printDeck(area));
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().add(button);

        view.bottomProperty().set(hBox);
    }
}
