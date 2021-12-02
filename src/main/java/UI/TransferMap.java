package UI;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.BonusIteam.Bonus;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class TransferMap extends AnchorPane {
    int width = 0;
    int height = 0;
    int sizeImage;
    Time time;
    int display = 0;

    // Thông tin màn trước
    private Bomber bomber = null;
    int scorePlayer = 0;
    int timeMap = 0;
    int totalTime = 0;

    //Bonus
    private int numBonus = 0;
    ArrayList<Bonus> bonusArrayList = new ArrayList<>();

    private boolean checkTarget = false;
    private boolean checkColaBottle = false;
    private boolean checkDezeniman_san = false;
    private boolean checkFamicom = false;
    private boolean checkGoddessMask = false;
    private boolean checkNakamoto_san = false;

    private int numTarget = 0;
    private int numColaBottle = 0;
    private int numDezeniman_san = 0;
    private int numFamicom = 0;
    private int numGoddessMask = 0;
    private int numNakamoto_san = 0;

    private boolean loading = false;

    public int getNumBonus() {
        return numBonus;
    }

    public int getNumTarget() {
        return numTarget;
    }

    public int getNumColaBottle() {
        return numColaBottle;
    }

    public int getNumDezeniman_san() {
        return numDezeniman_san;
    }

    public int getNumFamicom() {
        return numFamicom;
    }

    public int getNumGoddessMask() {
        return numGoddessMask;
    }

    public int getNumNakamoto_san() {
        return numNakamoto_san;
    }

    public int getScorePlayer() {
        return scorePlayer;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public TransferMap(int width, int height, int size) {
        time = new Time();
        sizeImage = size;
        width = width;
        Height = height;
        sprites[0] = Sprite.BOMBER_WALK_RIGHT_1;
        sprites[1] = Sprite.BOMBER_WALK_RIGHT_2;
        sprites[2] = Sprite.BOMBER_WALK_RIGHT_3;
    }

    public int getPercent() {
        return percent;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void reset(Bomber bomber, int timeMap, ArrayList<Bonus> bonusArrayList) {
        display = 0;
        percent = 0;
        time.reset();
        loading = true;
        this.timeMap = timeMap;
        this.bomber = bomber;
        this.bonusArrayList = bonusArrayList;
        this.scorePlayer = bomber.getScore().getScore();
        this.totalTime += timeMap;
    }

    public void update() {
        display++;
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, width, Height);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, width, Height);
        gc.setFill(Color.BLUE);
        gc.fillRect(width / 12, Height * 9 / 10, width / 12 * percent / 10, Height / 10);
        Font font = Font.font("Segoe UI Black", FontWeight.BOLD, 25);
        gc.setFill(Color.WHITE);
        gc.setFont(font);

        gc.setFill(Color.YELLOW);
        gc.fillText("Score: " + scorePlayer, width / 10 * 4, Height / 10 * 7);

        gc.setFill(Color.YELLOW);
        gc.fillText("Time: " + timeMap, width / 10 * 4, Height / 10 * 8);

    }
}