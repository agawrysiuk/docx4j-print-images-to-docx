package com.agawrysiuk.docx4jprintimagestodocx.view;

import com.agawrysiuk.docx4jprintimagestodocx.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lombok.Data;

@Data
public class View {
    private GridPane view;

    private Controller controller ;

    public View(Controller controller) {
        this.controller = controller ;
        createAndConfigurePane();
    }

    public Parent asParent() {
        return view;
    }

    private void createAndConfigurePane() {
        view = new GridPane();

        Button button = new Button("Print!");
        button.setOnMouseClicked(mouseEvent -> controller.printDeck());

        view.add(button,0,0);
    }
}
