package UI;

import Map.Map;
import SupportMap.TransferMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameOverPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Font headerFont;
    private Font contentFont;
    private Panel mainPanel;
    private int Width;
    private int Height;
    private TransferMap transferMap;

    Label gameOverLabel;
    Label scoreLabel;
    Label timeLabel;

    private int Score = 0;
    private int countScore = 0;
    private int Time = 0;
    private int countTime = 0;

    private boolean checkHighScore = false;

    public GameOverPane(int x, int y, int width, int height, Map map) {
        setLayoutX(x);
        setLayoutY(y);
        this.Width = width;
        this.Height = height;
        mainPanel = new Panel(width, height);
        getChildren().add(mainPanel);

        loadFont();

        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(headerFont);
        gameOverLabel.setTextFill(Color.ORANGE);
        gameOverLabel.setLayoutX(width / 10 * 3);
        gameOverLabel.setLayoutY(height / 10);

        this.Score = map.getPlayer().getScore().getScore();
        this.Time = map.getTime().countSecond();

        countScore = 0;
        scoreLabel = new Label("Score: " + countScore++);
        scoreLabel.setFont(contentFont);
        scoreLabel.setLayoutX(width / 10 * 3);
        scoreLabel.setLayoutY(height / 10 * 3);

        countTime = 0;
        timeLabel = new Label("Time: " + countTime);
        timeLabel.setFont(contentFont);
        timeLabel.setLayoutX(width / 10 * 3);
        timeLabel.setLayoutY(height / 10 * 5);

        Rectangle rectangle = new Rectangle(0, 0, width, height);
        rectangle.setFill(Color.GRAY);

        getChildren().addAll(rectangle, gameOverLabel, scoreLabel, timeLabel);

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

            Label yourScore = new Label("Score: " + Score);
            yourScore.setFont(contentFont);
            yourScore.setLayoutX(width / 10 * 3);
            yourScore.setLayoutY(height / 10 * 6);

            // Need fix
            UIButton okButton = new UIButton(190, 49, "OK");
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
        if (countTime < 10) {
            countTime++;
            int t = countTime * Time / 10;
            timeLabel.setText("Time: " + t);
        }
        if (countScore < 10) {
            countScore++;
            int s = countScore * Score / 10;
            scoreLabel.setText("Score: " + s);
        }
    }
}