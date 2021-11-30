package SupportMap;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.BonusIteam.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransferMap {
    int Width = 0;
    int Height = 0;
    int sizeImage;
    Time time;
    int percent = 0;
    int display = 0;
    Sprite[] sprites = new Sprite[3];

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
        Width = width;
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
        percent = time.countSecond() * 10;
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, Width, Height);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, Width, Height);
        gc.setFill(Color.BLUE);
        gc.fillRect(Width / 12, Height * 9 / 10, Width / 12 * percent / 10, Height / 10);
        sprites[display / 3 % 3].render(Width / 12 * percent / 10, Height * 9 / 10 - sizeImage, sizeImage, sizeImage, gc);
        Font font = Font.font("Segoe UI Black", FontWeight.BOLD, 25);
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText("Loading ... " + percent + " %", Width / 10 * 4, Height / 100 * 100);

        gc.setFill(Color.YELLOW);
        gc.fillText("Score: " + scorePlayer, Width / 10 * 4, Height / 10 * 7);

        gc.setFill(Color.YELLOW);
        gc.fillText("Time: " + timeMap, Width / 10 * 4, Height / 10 * 8);

        int other = 0;
        int width = Width / 3;
        for (Bonus bonus : bonusArrayList) {
            if (bonus.isCheckBonus()) {
                bonus.render(width, Height / 20 * (2 * other + 1), gc);
                gc.setFill(Color.YELLOW);
                gc.fillText("x 1 : " + bonus.getScore(), Width / 3 + 2 * sizeImage, Height / 20 * (2 * other + 2));
                other++;
            }
        }
    }
}