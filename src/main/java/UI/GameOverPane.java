package UI;

import Entities.BonusIteam.*;
import Map.Map;
import SupportMap.TransferMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class GameOverPane extends AnchorPane {
    private final int DEFAULT_WIDTH = 100;
    private final int DEFAULT_HEIGHT = 100;

    private Font font;
    private Panel mainPanel;
    private int Width;
    private int Height;
    private TransferMap transferMap;

    Label gameOverLabel;
    Label scoreLabel;
    Label timeLabel;
    private Button homeButton;
    private Button newGameButton;

    private int Score = 0;
    private int countScore = 0;
    private int Time = 0;
    private int countTime = 0;

    private boolean checkHighScore = false;

    public GameOverPane(int x, int y, int width, int height, TransferMap transferMap) {
        setLayoutX(x);
        setLayoutY(y);
        this.Width = width;
        this.Height = height;
        mainPanel = new Panel(width, height);
        getChildren().add(mainPanel);

        gameOverLabel = new Label("GAME OVER");
        try {
            gameOverLabel.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 25));
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameOverLabel.setTextFill(Color.ORANGE);
        gameOverLabel.setLayoutX(width / 10 * 3);
        gameOverLabel.setLayoutY(height / 10);

        this.Score = transferMap.getScorePlayer();
        this.Time = transferMap.getTotalTime();

        countScore = 0;
        scoreLabel = new Label("Score: " + countScore++);
        try {
            scoreLabel.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scoreLabel.setLayoutX(width / 10 * 3);
        scoreLabel.setLayoutY(height / 10 * 3);


        countTime = 0;
        timeLabel = new Label("Time: " + countTime);
        try {
            timeLabel.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        timeLabel.setLayoutX(width / 10 * 3);
        timeLabel.setLayoutY(height / 10 * 5);


        homeButton = new Button("Home");
        try {
            homeButton.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 10));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        homeButton.setLayoutX(width / 10 * 7);
        homeButton.setLayoutY(height / 5 * 4);

        newGameButton = new Button("New Game");
        try {
            newGameButton.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 10));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        newGameButton.setLayoutX(width / 10 * 2);
        newGameButton.setLayoutY(height / 5 * 4);

        Rectangle rectangle = new Rectangle(0, 0, width, height);
        rectangle.setFill(Color.GRAY);

        newGameButton.setFocusTraversable(true);
        homeButton.setFocusTraversable(true);

        getChildren().addAll(rectangle, gameOverLabel, scoreLabel, timeLabel, newGameButton, homeButton);

        if (checkHighScore) {
            AnchorPane anchorPane = new AnchorPane();
            Rectangle boxHighScore = new Rectangle(width, height);
            boxHighScore.setFill(Color.YELLOWGREEN);

            Label highScore = new Label("High Score");
            try {
                highScore.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            highScore.setTextFill(Color.RED);
            highScore.setLayoutX(width / 10 * 3);
            highScore.setLayoutY(height / 10);

            Label nameLabel = new Label("Name ");
            try {
                nameLabel.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            nameLabel.setLayoutX(width / 20);
            nameLabel.setLayoutY(height / 100 * 38);

            TextField nameTextField = new TextField("your name");
            nameTextField.setLayoutX(width / 4);
            nameTextField.setLayoutY(height / 3);
            try {
                nameTextField.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            Label yourScore = new Label("Score: " + Score);
            try {
                yourScore.setFont(Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 20));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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