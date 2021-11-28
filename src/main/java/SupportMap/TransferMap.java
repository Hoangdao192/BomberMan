package SupportMap;

import Component.Sprite;
import Component.Time;
import Entities.Bomber;
import Entities.BonusIteam.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    List<Bonus> bonuses = new LinkedList<>();

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

    public void addBonus() {
        numBonus = 0;
        if (checkTarget) {
            System.out.println("checkTarget");
            numBonus++;
            numTarget++;
        }
        if (checkColaBottle) {
            System.out.println("checkColaBottle");
            numBonus++;
            numColaBottle++;
        }
        if (checkDezeniman_san) {
            System.out.println("checkDezeniman_san");
            numBonus++;
            numDezeniman_san++;
        }
        if (checkFamicom) {
            System.out.println("checkFamicom");
            numBonus++;
            numFamicom++;
        }
        if (checkGoddessMask) {
            System.out.println("checkGoddessMask");
            numBonus++;
            numGoddessMask++;
        }
        if (checkNakamoto_san) {
             System.out.println("checkNakamoto_san");
            numBonus++;
            numNakamoto_san++;
        }

//        checkTarget = true;
//        checkColaBottle = true;
//        checkDezeniman_san = true;
//        checkFamicom = true;
//        checkGoddessMask = true;
//        checkNakamoto_san = true;
//        numBonus = 6;

        int other = 0;
        int width = Width / 3;
        if (checkTarget) {
            BonusTarget bonusTarget = new BonusTarget(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(bonusTarget);
            bomber.getScore().addScore(bonusTarget.getScore());
            other++;
        }
        if (checkColaBottle) {
            ColaBottle colaBottle = new ColaBottle(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(colaBottle);
            bomber.getScore().addScore(colaBottle.getScore());
            other++;
        }
        if (checkDezeniman_san) {
            Dezeniman_san dezeniman_san = new Dezeniman_san(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(dezeniman_san);
            bomber.getScore().addScore(dezeniman_san.getScore());
            other++;
        }
        if (checkFamicom) {
            Famicom famicom = new Famicom(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(famicom);
            bomber.getScore().addScore(famicom.getScore());
            other++;
        }
        if (checkGoddessMask) {
            GoddessMask goddessMask = new GoddessMask(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(goddessMask);
            bomber.getScore().addScore(goddessMask.getScore());
            other++;
        }
        if (checkNakamoto_san) {
            Nakamoto_san nakamoto_san = new Nakamoto_san(width, Height / 20 * (2 * other + 1), sizeImage, sizeImage);
            bonuses.add(nakamoto_san);
            bomber.getScore().addScore(nakamoto_san.getScore());
            other++;
        }
    }

    public void reset(Bomber bomber, int timeMap, boolean[] checkBonus) {
        display = 0;
        percent = 0;
        time.reset();
        loading = true;
        this.timeMap = timeMap;
        this.bomber = bomber;
        bonuses.clear();
        checkTarget = checkBonus[0];
        checkColaBottle = checkBonus[1];
        checkDezeniman_san = checkBonus[2];
        checkFamicom = checkBonus[3];
        checkGoddessMask = checkBonus[4];
        checkNakamoto_san = checkBonus[5];
        addBonus();
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
        for (Bonus bonus : bonuses) {
            bonus.render(gc);
            gc.setFill(Color.YELLOW);
            gc.fillText("x 1 : " + bonus.getScore(), Width / 3 + 2 * sizeImage, Height / 20 * (2 * other + 2));
            other++;
        }
    }
}