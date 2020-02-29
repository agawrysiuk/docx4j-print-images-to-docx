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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Data
@Slf4j
public class View {
    private BorderPane mainView;
    private int marginStackPane = 25;
    private int rowCards = 25;
    private DeckLoader deckLoader;
    private TextArea area;
    private ColorAdjust darkerImage;

    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
        this.deckLoader = new DeckLoader(new Downloader());
        this.darkerImage = new ColorAdjust();
        darkerImage.setBrightness(-0.5);
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

    public void lookUpDeck() {
        this.rowCards = 25;
        this.marginStackPane = 25;
        BorderPane sideView = new BorderPane();

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_LEFT);
        sideView.setCenter(stackPane);

        Stage secondStage = new Stage();
        secondStage.setTitle("Downloaded cards");
        secondStage.setScene(new Scene(sideView, 1920, 900));
        List<Card> cardList;

        try {
            cardList = deckLoader.loadDeck(area.getText());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Card card : cardList) {
            printingCard(stackPane, card, marginStackPane, rowCards, cardList, false);
            if (card.getCardImageTransform() != null) {
                printingCard(stackPane, card, marginStackPane, rowCards, cardList, true);
            }
        }

        Button button = new Button("Confirm");
        button.setOnMouseClicked(mouseEvent ->  {
            controller.printDeck(cardList);
            secondStage.close();
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().add(button);

        sideView.setBottom(hBox);

        secondStage.initModality(Modality.APPLICATION_MODAL); //this is the only window you can use
        secondStage.initOwner(mainView.getScene().getWindow());
        secondStage.show();
    }

    private void printingCard(StackPane stackPane, Card activeCard, int marginStackPane, int rowCards, List<Card> cardList, boolean transform) {
        ImageView addedCard = new ImageView();
        addedCard.setImage(transform ? activeCard.getCardImageTransform() : activeCard.getCardImage());
        if (!transform) {
            activeCard.setCardImageView(addedCard);
        } else {
            activeCard.setCardImageViewTransform(addedCard);
        }
        addedCard.setFitWidth(250);
        addedCard.setPreserveRatio(true);
        addedCard.setSmooth(true);
        addedCard.setCache(true);
        addedCard.setOnMouseClicked(mouseEvent -> {
            if (cardList.contains(activeCard)) {
                cardList.remove(activeCard);
                addedCard.setEffect(darkerImage);
                if(transform) {
                    activeCard.getCardImageView().setEffect(darkerImage);
                } else if(activeCard.getCardImageTransform()!=null) {
                    activeCard.getCardImageViewTransform().setEffect(darkerImage);
                }
            } else {
                cardList.add(activeCard);
                addedCard.setEffect(null);
                if(transform) {
                    activeCard.getCardImageView().setEffect(null);
                } else if(activeCard.getCardImageTransform()!=null) {
                    activeCard.getCardImageViewTransform().setEffect(null);
                }
            }
        });
        addedCard.setOnMouseEntered(mouseEvent -> {
            addedCard.setViewOrder(-1);
        });
        addedCard.setOnMouseExited(mouseEvent -> {
            addedCard.setViewOrder(0);
        });
        StackPane.setMargin(addedCard, new Insets(marginStackPane, 0, 0, rowCards)); //sets the place where the card image will be printed

//        Tooltip tooltip = new Tooltip();
//        tooltip.setGraphic(new ImageView(activeCard.getCardImage()));
//        tooltip.setShowDelay(Duration.millis(100));
//        tooltip.setShowDuration(Duration.seconds(30));
//        Tooltip.install(addedCard, tooltip);

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
