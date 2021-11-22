package Entities;

import Component.Sprite;
import Component.Time;
import Entities.PowerUp.PowerUp;
import Map.Map;

public class Portal extends PowerUp {
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    public static boolean checkColaBottle = false;

    private Time time = null;
    private Bomber bomber = null;
    private boolean checkHitBox = false;

    public Portal(int x, int y, int width, int height, int gridSize, Map map) {
        super(x, y, width, height, gridSize, Sprite.PORTAL, map);
        collision = true;
        time = new Time();
    }

    @Override
    public void update() {
    }

    public boolean ifCollideDo(Entity other) {
        if (hasBrick()) {
            return false;
        }
        if (other instanceof Bomber) {
            if (collision(other)) {
                bomber = map.getPlayer();
                if (!checkHitBox) {
                    checkHitBox = true;
                    time.reset();
                    left = false;
                    right = false;
                    up = false;
                    down = false;
                }
                if (time.countSecond() >= 3) {
                    map.newMap();
                    checkHitBox = false;
                    checkColaBottle = false;
                } else {
                    if (!checkColaBottle) {
                        if (up && down && left && right) {
                            checkColaBottle = true;
                            System.out.println("Cola true");
                        }
                        if (bomber.isMoveDown()) {
                            down = true;
                        } else if (bomber.isMoveLeft()) {
                            left = true;
                        } else if (bomber.isMoveRight()) {
                            right = true;
                        } else if (bomber.isMoveUp()) {
                            up = true;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}