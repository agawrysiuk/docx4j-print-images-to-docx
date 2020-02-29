package com.agawrysiuk.docx4jprintimagestodocx.view;

import com.agawrysiuk.docx4jprintimagestodocx.controller.Controller;
import com.agawrysiuk.docx4jprintimagestodocx.data.Card;
import com.agawrysiuk.docx4jprintimagestodocx.downloader.Downloader;
import com.agawrysiuk.docx4jprintimagestodocx.service.DeckLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Data
public class View {
    private BorderPane mainView;
    private int marginStackPane = 25;
    private int rowCards = 25;
    private DeckLoader deckLoader;
    private TextArea area;

    private Controller controller ;

    public View(Controller controller) {
        this.controller = controller ;
        this.deckLoader = new DeckLoader(new Downloader());
        createAndConfigurePane();
    }

    public Parent asParent() {
        return mainView;
    }

    private void createAndConfigurePane() {
        mainView = new BorderPane();

        area = new TextArea();
        mainView.centerProperty().set(area);

        Button button = new Button("Print!");
        button.setOnMouseClicked(mouseEvent -> lookUpDeck());
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().add(button);

        mainView.bottomProperty().set(hBox);
    }

//    private void showDialog() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setTitle("Downloaded cards");
//        dialog.setHeaderText(null);
//
//        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);
//
//        GridPane gridPane = new GridPane();
//        gridPane.setVgap(10);
//        gridPane.setHgap(10);
//        gridPane.setAlignment(Pos.CENTER);
//
//        List<Boolean> previousStates = new ArrayList<>();
//
//        for(int i = 0; i<listCheckBox.size();i++) {
//            gridPane.add(listCheckBox.get(i),0,i);
//            previousStates.add(listCheckBox.get(i).isSelected());
//        }
//
//        dialog.getDialogPane().setContent(gridPane);
//        dialog.initModality(Modality.APPLICATION_MODAL); //this is the only window you can use
//        dialog.initOwner(mainView.getScene().getWindow());
//        dialog.setWidth(800);
//        dialog.setHeight(800);
//        Optional<ButtonType> result = dialog.showAndWait();
//        if(result.isPresent() && result.get() == confirmButton) {
//            controller.printDeck(new TextArea());
//        }
//    }

    public void lookUpDeck() {
        this.rowCards = 25;
        this.marginStackPane = 25;
        StackPane stackPane = new StackPane();

        stackPane.setAlignment(Pos.TOP_LEFT);

        Stage secondStage = new Stage();
        secondStage.setTitle("Downloaded cards");
        secondStage.setScene(new Scene(stackPane, 1600, 900));
        List<Card> cardList;

        try {
            cardList = deckLoader.loadDeck(area.getText());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Card card : cardList) {
            printingCard(stackPane, card, marginStackPane, rowCards);
        }

        secondStage.initModality(Modality.APPLICATION_MODAL); //this is the only window you can use
        secondStage.initOwner(mainView.getScene().getWindow());
        secondStage.show();
    }

    private void printingCard(StackPane stackPane, Card activeCard, int marginStackPane, int rowCards) {
        ImageView addedCard = new ImageView();
        addedCard.setImage(activeCard.getCardImage());
        addedCard.setFitWidth(250);
        addedCard.setPreserveRatio(true);
        addedCard.setSmooth(true);
        addedCard.setCache(true);
        StackPane.setMargin(addedCard, new Insets(marginStackPane, 0, 0, rowCards)); //sets the place where the card image will be printed

        Tooltip tooltip = new Tooltip();
        tooltip.setGraphic(new ImageView(activeCard.getCardImage()));
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setShowDuration(Duration.seconds(30));
        Tooltip.install(addedCard, tooltip);

        Label label = new Label();
        label.setText(activeCard.getName());
        label.setStyle("-fx-text-fill:transparent;");//so it cant be seen
        StackPane.setMargin(addedCard, new Insets(marginStackPane, 0, 0, rowCards));

        stackPane.getChildren().add(label);
        stackPane.getChildren().add(addedCard);

        this.marginStackPane += 35; //changing horizontal space
        if (this.marginStackPane % 550 == 0) { //checking if we are at the bottom
            this.rowCards += 260; //changing vertical space
            this.marginStackPane = 25; //starting from the top
        }
    }
}
