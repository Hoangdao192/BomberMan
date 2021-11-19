package SupportMap;

import Component.Sprite;
import Entities.Bomber;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeadMap {
    int Height = 0;
    int Width = 0;
    int MaxTime = 200;
    int score = 0;
    int time = 0;
    int HP = 3;
    Bomber bomber = null;
    Map map = null;
    Sprite sprite = Sprite.BOMBER_WALK_RIGHT_2;

    public void setMaxTime(int x) {
        MaxTime = x;
    }

    public HeadMap(Bomber player, Map map, int width, int height) {
        bomber = player;
        this.map = map;
        Width = width;
        Height = height;
        HP = bomber.getHP();
    }

    public void update() {
        score = bomber.getScore().getScore();
        time = MaxTime - map.getTime().countSecond();
        HP = bomber.getHP();
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, Width, Height);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Width, Height);

        Font font = Font.font("Segoe UI Black", FontWeight.BOLD, 25);

        gc.setFill(Color.RED);
        gc.setFont(font);
        gc.fillText(String.format("Time: %d", time),Width / 20, Height / 4 * 3);

        gc.setFill(Color.RED);
        gc.setFont(font);
        gc.fillText(String.format("Score: %d", score),Width / 3, Height / 4 * 3);

        for (int i = 0; i < HP; i++) {
            sprite.render( Width * 2 / 3 + i * bomber.getWidth(), Height / 8, bomber.getWidth(), bomber.getHeight(), gc);
        }
    }
}
