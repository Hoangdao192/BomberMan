package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class SettingPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Panel header;
    private Panel mainPanel;
    private Font headerFont;
    private Font contentFont;

    private UIButton acceptButton;
    private UIButton closeButton;

    private int width;
    private int height;

    private boolean acceptPressed = false;
    private boolean closePressed = false;

    public SettingPane(int x, int y, int width, int height) {
        setLayoutX(x);
        setLayoutY(y);
        this.width = width;
        this.height = height;

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
        createMainPanel();
        createButton();
    }

    private void createHeader() {
        header = new Panel(width, 45);
        header.setColor(
                Color.rgb(25, 137, 184),
                Color.rgb(53, 186, 243),
                Color.rgb(30, 167, 225)
        );
        Label gameOverLabel = new Label("SETTING");
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

    private void createMainPanel() {
        Label soundLabel = new Label("ÂM THANH");
        soundLabel.setFont(contentFont);
        soundLabel.setTextFill(Color.GRAY);
        mainPanel.setTopAnchor(soundLabel, 17.0);
        mainPanel.setLeftAnchor(soundLabel, 20.0);
        mainPanel.getChildren().add(soundLabel);

        CheckBox checkBox = new CheckBox(38);
        mainPanel.setTopAnchor(checkBox, 10.0);
        mainPanel.setLeftAnchor(checkBox, 130.0);
        mainPanel.getChildren().add(checkBox);
    }

    private void createButton() {
        acceptButton = new UIButton(190, 49, "ACCEPT");
        acceptButton.setStyle(UIButton.BLUE_1);
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                acceptPressed = true;
            }
        });
        mainPanel.setLeftAnchor(acceptButton, (mainPanel.getWidth() - 190) / 2);
        mainPanel.setBottomAnchor(acceptButton, 80.0);
        mainPanel.getChildren().add(acceptButton);

        closeButton = new UIButton(190, 49, "CLOSE");
        closeButton.setStyle(UIButton.BLUE_1);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                acceptPressed = true;
            }
        });
        mainPanel.setLeftAnchor(closeButton, (mainPanel.getWidth() - 190) / 2);
        mainPanel.setBottomAnchor(closeButton, 20.0);
        mainPanel.getChildren().add(closeButton);
    }

    public boolean isAcceptPressed() {
        return acceptPressed;
    }
}
