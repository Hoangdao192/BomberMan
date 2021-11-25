package UI;

import Entities.Bomber;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;

public class GameOverPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Panel header;
    private Panel mainPanel;
    private TextField enterName;
    private Font headerFont;
    private Font contentFont;

    private Button acceptButton;

    private int width;
    private int height;

    private int playerScore;

    public GameOverPane(int x, int y, int width, int height, int playerScore) {
        setLayoutX(x);
        setLayoutY(y);
        this.width = width;
        this.height = height;
        this.playerScore = playerScore;

        try {
            headerFont = Font.loadFont(
                    new FileInputStream("src/main/resources/Font/kenvector_future_thin.ttf"),
                    20);
            contentFont = Font.loadFont(
                    new FileInputStream("src/main/resources/Font/kenvector_future_thin.ttf"),
                    17);
        }catch (Exception e) {}

        createHeader();
        mainPanel = new Panel(width, height - (int) header.getHeight());
        mainPanel.setLayoutY(40);
        getChildren().add(mainPanel);
        createScoreView();
        createNameInput();
        createButton();
    }

    private void createHeader() {
        header = new Panel(width, 45);
        header.setColor(
                Color.rgb(25, 137, 184),
                Color.rgb(53, 186, 243),
                Color.rgb(30, 167, 225)
        );
        Label gameOverLabel = new Label("GAME OVER");
        try {
            gameOverLabel.setFont(headerFont);
        } catch (Exception e) {}
        gameOverLabel.setPrefWidth(width);
        gameOverLabel.setPrefHeight(45);
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setStyle("-fx-alignment: center");
        header.getChildren().add(gameOverLabel);
        getChildren().add(header);
    }

    private void createScoreView() {
        Label score = new Label("SCORE: " + playerScore);
        score.setFont(contentFont);
        score.setTextFill(Color.GRAY);
        mainPanel.setTopAnchor(score, 10.0);
        mainPanel.setLeftAnchor(score, 20.0);
        mainPanel.getChildren().add(score);
    }

    private void createNameInput() {
        Label nameLabel = new Label("NAME");
        nameLabel.setFont(contentFont);
        nameLabel.setTextFill(Color.GRAY);
        mainPanel.setTopAnchor(nameLabel, 50.0);
        mainPanel.setLeftAnchor(nameLabel, 20.0);
        mainPanel.getChildren().add(nameLabel);

        enterName = new TextField();
        enterName.setPrefSize(170, 40);
        enterName.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: grey;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-insets: 2 2 2 2"
        );
        mainPanel.setLeftAnchor(enterName, 80.0);
        mainPanel.setRightAnchor(enterName, 20.0);
        mainPanel.setTopAnchor(enterName, 40.0);
        mainPanel.getChildren().add(enterName);
    }

    private void createButton() {
        acceptButton = new UIButton(200);
        mainPanel.getChildren().add(acceptButton);
    }
}
