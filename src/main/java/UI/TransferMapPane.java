package UI;

import Component.MapManager;
import Component.Time;
import Entities.Bomber;
import Map.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TransferMapPane extends AnchorPane {
    int width;
    int height;
    Time time;
    private final int DEFAULT_WAIT_TIME = 10;

    // Thông tin màn trước
    int scorePlayer = 0;
    int timeMap = 0;
    private Map map;
    private MapManager mapManager;

    private Font headerFont;
    private Font contentFont;
    private Label headerLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label timeCountLabel;

    private boolean transfer = false;

    private MediaPlayer mediaPlayer;

    public TransferMapPane(int width, int height, Map map, MapManager mapManager) {
        this.mapManager = mapManager;
        this.map = map;
        time = new Time();
        this.width = width;
        this.height = height;

        scorePlayer = map.getPlayer().getScore().getScore();
        timeMap = map.getTime().countSecond();

        BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN, null, new Insets(-1));
        setBackground(new Background(backgroundFill));
        setPrefSize(width, height);

        loadFont();
        createLabel();
        createSound();
    }

    private void createSound() {
        Media media = new Media(new File("src/main/resources/Sound/next_level.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void createLabel() {
        headerLabel = new Label("CONGRATULATION");
        headerLabel.setFont(headerFont);
        headerLabel.setTextFill(Color.YELLOW);
        headerLabel.setPrefWidth(width);
        headerLabel.setAlignment(Pos.CENTER);
        setTopAnchor(headerLabel, height / 4.0);

        scoreLabel = new Label("SCORE: " + scorePlayer);
        scoreLabel.setFont(contentFont);
        scoreLabel.setTextFill(Color.YELLOW);
        scoreLabel.setPrefWidth(width);
        scoreLabel.setAlignment(Pos.CENTER);
        setTopAnchor(scoreLabel, height / 4.0 * 2.0);

        timeLabel = new Label("TIME: " + timeMap);
        timeLabel.setFont(contentFont);
        timeLabel.setTextFill(Color.YELLOW);
        timeLabel.setPrefWidth(width);
        timeLabel.setAlignment(Pos.CENTER);
        setTopAnchor(timeLabel, height / 4.0 * 2.0 + height / 15.8);

        timeCountLabel = new Label("Next level ..." + time.countSecond());
        timeCountLabel.setFont(contentFont);
        timeCountLabel.setPrefWidth(width);
        timeCountLabel.setAlignment(Pos.CENTER);
        setTopAnchor(timeCountLabel, height / 5.0 * 4.0);

        getChildren().addAll(headerLabel, scoreLabel, timeLabel, timeCountLabel);
    }

    private void loadFont() {
        try {
            headerFont = Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 35);
            contentFont = Font.loadFont(new FileInputStream("src/main/resources/Font/kenvector_future.ttf"), 25);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot load font: src/main/resources/Font/kenvector_future.ttf");
            headerFont = Font.font("Segoe UI Black", FontWeight.BOLD, 35);
            contentFont = Font.font("Segoe UI Black", FontWeight.BOLD, 25);
        }
    }

    public void update() {
        if (mapManager.getLevel() < mapManager.getMapPathList().size()) {
            headerLabel.setText("CONGRATULATION\n NEXT LEVEL: " + (mapManager.getLevel() + 1));
        } else {
            headerLabel.setText("CONGRATULATION\n YOU PASS ALL LEVEL.");
        }

        mediaPlayer.play();
        int timeCount = time.countSecond();
        if (timeCount > 10) {
            timeCount = 10;
        }
        timeCountLabel.setText("Next level ..." + (DEFAULT_WAIT_TIME - timeCount));
        if (timeCount >= DEFAULT_WAIT_TIME) {
            transfer = true;
        }
    }

    public void setScorePlayer(int scorePlayer) {
        this.scorePlayer = scorePlayer;
        scoreLabel.setText("SCORE: " + scorePlayer);
    }

    public void setTimeMap(int timeMap) {
        this.timeMap = timeMap;
        timeLabel.setText("TIME: " + timeMap);
    }

    public void resetTime() {
        time = new Time();
    }

    public boolean isTransfer() {
        return transfer;
    }
}