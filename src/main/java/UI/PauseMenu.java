package UI;

import Entities.Bomber;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class PauseMenu extends AnchorPane {
    private final int DEFAULT_WIDTH = 200;
    private final int DEFAULT_HEIGHT = 230;
    private Panel header;
    private Panel mainPanel;
    private Font headerFont;

    private UIButton continueButton;
    private UIButton restartButton;
    private UIButton menuButton;

    private int width;
    private int height;

    public PauseMenu(int x, int y, int width, int height) {
        setLayoutX(x);
        setLayoutY(y);
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
        Label gameOverLabel = new Label("PAUSE");
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
        continueButton = new UIButton(152, 39, "CONTINUE");
        continueButton.setLayoutX(30);
        mainPanel.setBottomAnchor(continueButton, height / 1.91);
        mainPanel.getChildren().add(continueButton);

        restartButton = new UIButton(152, 39, "RESTART");
        restartButton.setLayoutX(30);
        mainPanel.setBottomAnchor(restartButton, height / 3.28);
        mainPanel.getChildren().add(restartButton);

        menuButton = new UIButton(152, 39, "MENU");
        menuButton.setLayoutX(30);
        mainPanel.setBottomAnchor(menuButton, height / 11.5);
        mainPanel.getChildren().add(menuButton);
    }

    public UIButton getContinueButton() {
        return continueButton;
    }

    public UIButton getMenuButton() {
        return menuButton;
    }

    public UIButton getRestartButton() {
        return restartButton;
    }

    public void resize(int newWidth, int newHeight) {

    }
}
