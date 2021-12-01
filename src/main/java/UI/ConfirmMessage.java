package UI;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class ConfirmMessage extends AnchorPane {
    private int width;
    private int height;

    private Panel header;
    private Panel mainPanel;
    private Font headerFont;

    private UIButton yesButton;
    private UIButton noButton;

    public ConfirmMessage(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            headerFont = Font.loadFont(
                    new FileInputStream("src/main/resources/Font/kenvector_future_thin.ttf"),
                    20);
        }catch (Exception e) {}
        createHeader();
        mainPanel = new Panel(width, height - (int) header.getHeight());
        mainPanel.setLayoutY(40);
        getChildren().add(mainPanel);

        createButton();
    }

    private void createHeader() {
        header = new Panel(width, 45);
        header.setColor(
                Color.rgb(25, 137, 184),
                Color.rgb(53, 186, 243),
                Color.rgb(30, 167, 225)
        );
        Label gameOverLabel = new Label("ARE YOU SURE ?");
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

    private void createButton() {
        yesButton = new UIButton(152, 39, "YES");
        setBottomAnchor(yesButton, height / 2.0);
        getChildren().add(yesButton);

        noButton = new UIButton(152, 39, "NO");
        setBottomAnchor(noButton, height / 2.0);
        getChildren().add(noButton);
    }
}
