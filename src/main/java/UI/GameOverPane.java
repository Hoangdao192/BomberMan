package UI;

import Map.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameOverPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Font headerFont;
    private Font contentFont;

    private Panel headPanel;
    private Panel mainPanel;
    private int width;
    private int height;

    private Label gameOverLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private UIButton newGameButton;
    private UIButton menuButton;

    private int playerScore = 0;
    private int countScore = 0;
    private int mapTime = 0;
    private int countTime = 0;

    private MediaPlayer gameOverSoundPlayer;

    private boolean checkHighScore = false;

    private Map map;

    public GameOverPane(int x, int y, int width, int height, Map map) {
        setLayoutX(x);
        setLayoutY(y);
        this.map = map;
        this.width = width;
        this.height = height;

        loadFont();
        createHeader();
        mainPanel = new Panel(width, height - 40);
        mainPanel.setLayoutY(40);
        getChildren().add(mainPanel);

        this.playerScore = map.getPlayer().getScore().getScore();
        this.mapTime = map.getTime().countSecond();

        countScore = 0;
        countTime = 0;


        createLabel();
        createButton();

        createSound();

        if (checkHighScore) {
            AnchorPane anchorPane = new AnchorPane();
            Rectangle boxHighScore = new Rectangle(width, height);
            boxHighScore.setFill(Color.YELLOWGREEN);

            Label highScore = new Label("High Score");
            highScore.setFont(contentFont);
            highScore.setTextFill(Color.RED);
            highScore.setLayoutX(width / 10 * 3);
            highScore.setLayoutY(height / 10);

            Label nameLabel = new Label("Name ");
            nameLabel.setFont(contentFont);
            nameLabel.setLayoutX(width / 20);
            nameLabel.setLayoutY(height / 100 * 38);

            TextField nameTextField = new TextField("your name");
            nameTextField.setLayoutX(width / 4);
            nameTextField.setLayoutY(height / 3);
            nameTextField.setFont(contentFont);


            Label yourScore = new Label("Score: " + playerScore);
            yourScore.setFont(contentFont);
            yourScore.setLayoutX(width / 10 * 3);
            yourScore.setLayoutY(height / 10 * 6);

            Button okButton = new Button("OK");
            try {
                okButton.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 10));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            okButton.setMinWidth(width / 10);
            okButton.setMinHeight(height / 10);
            okButton.setLayoutX(width / 100 * 45);
            okButton.setLayoutY(height / 5 * 4);

            anchorPane.getChildren().addAll(boxHighScore, highScore, nameLabel, nameTextField, yourScore, okButton);

            Scene scene = new Scene(anchorPane, width, height);

            Stage highScoreStage = new Stage();
            highScoreStage.setScene(scene);
            highScoreStage.initModality(Modality.APPLICATION_MODAL);

            highScoreStage.show();
        }
    }

    private void createHeader() {
        headPanel = new Panel(width, 45);
        headPanel.setColor(
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
        headPanel.getChildren().add(gameOverLabel);
        getChildren().add(headPanel);
    }

    private void createLabel() {
        scoreLabel = new Label("Score: " + countScore);
        scoreLabel.setFont(contentFont);
        scoreLabel.setLayoutX(width / 10 * 3);
        scoreLabel.setLayoutY(height / 10 * 3);

        timeLabel = new Label("Time: " + countTime);
        timeLabel.setFont(contentFont);
        timeLabel.setLayoutX(width / 10 * 3);
        timeLabel.setLayoutY(height / 10 * 5);

        getChildren().addAll(scoreLabel, timeLabel);
    }

    private void createButton() {
        newGameButton = new UIButton(152, 39, "NEW GAME");
        setBottomAnchor(newGameButton, 20.0);
        setLeftAnchor(newGameButton, 20.0);

        menuButton = new UIButton(152, 39, "MENU");
        setBottomAnchor(menuButton, 20.0);
        setRightAnchor(menuButton, 20.0);
        getChildren().addAll(newGameButton, menuButton);
    }

    private void createSound() {
        Media gameOverSound = new Media(new File("src/main/resources/Sound/game_over.mp3").toURI().toString());
        gameOverSoundPlayer = new MediaPlayer(gameOverSound);
        //gameOverSoundPlayer.play();
    }

    private void loadFont() {
        try {
            headerFont = Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 25);
        } catch (Exception e) {
            System.out.println("Cannot load file: src/main/resources/Font/kenvector_future.ttf");
            headerFont = Font.loadFont("Arial", 25);
        }

        try {
            contentFont = Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20);
        } catch (Exception e) {
            System.out.println("Cannot load file: src/main/resources/Font/kenvector_future.ttf");
            headerFont = Font.loadFont("Arial", 25);
        }
    }

    public void update() {
        //gameOverSoundPlayer.seek(Duration.ZERO);
        gameOverSoundPlayer.play();
        this.playerScore = map.getPlayer().getScore().getScore();
        this.mapTime = map.getTime().countSecond();
        if (countTime < 10) {
            countTime++;
            int t = countTime * mapTime / 10;
            timeLabel.setText("Time: " + t);
        }
        if (countScore < 10) {
            countScore++;
            int s = countScore * playerScore / 10;
            scoreLabel.setText("Score: " + s);
        }
    }

    public UIButton getMenuButton() {
        return menuButton;
    }

    public UIButton getNewGameButton() {
        return newGameButton;
    }
}