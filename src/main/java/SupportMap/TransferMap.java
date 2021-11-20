package SupportMap;

import Component.Sprite;
import Component.Time;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TransferMap {
    int Width = 0;
    int Height = 0;
    int sizeImage;
    Time time;
    int percent = 0;
    int display = 0;
    Sprite[] sprites = new Sprite[3];

    // Thông tin màn trước
    int scorePlayer = 0;
    int timeMap = 0;

    private boolean loading = false;

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

    public void reset(int scorePlayer, int timeMap) {
        display = 0;
        percent = 0;
        time.reset();
        loading = true;
        this.timeMap = timeMap;
        this.scorePlayer = scorePlayer;
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
        gc.fillRect(Width / 12, Height * 2 / 5, Width / 12 * percent / 10, Height / 10);
        sprites[display / 3 % 3].render(Width / 12 * percent / 10, Height * 2 / 5 - sizeImage, sizeImage, sizeImage, gc);
        Font font = Font.font("Segoe UI Black", FontWeight.BOLD, 25);
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText("Loading ... " + percent + " %", Width / 10 * 4, Height / 100 * 48);

        gc.setFill(Color.YELLOW);
        gc.fillText("Score: " + scorePlayer, Width / 10 * 4, Height / 10 * 6);

        gc.setFill(Color.YELLOW);
        gc.fillText("Time: " + timeMap, Width / 10 * 4, Height / 10 * 7);

    }
}
