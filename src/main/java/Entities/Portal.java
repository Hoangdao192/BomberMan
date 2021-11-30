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

    //   Kiểm tra xem có bom nổ trên Portal ko
    private int hasBomExplosion = 0;
    private int timeBomExplosion = 0;
    private int numBomExplosion = 0;

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
        if(!hasBomber()) {
            time.reset();
        }
        if (hasBomExplosion > 0) {
            if (map.getTime().countMilliSecond() - timeBomExplosion > 500) {
                int x = (int) (Math.random() * 8);
                char c = String.valueOf(x).charAt(0);
                for (int i = 0; i < 6; i++) {
                    map.createEntity(c, gridX, gridY);
                }
                hasBomExplosion--;
            }
        }
    }

    public void die() {
        if (hasBrick()) {
            return;
        }
        hasBomExplosion++;
        numBomExplosion++;
        timeBomExplosion = map.getTime().countMilliSecond();
        System.out.println("hasBombExplosion");
    }

    public boolean hasBomber() {
        bomber = map.getPlayer();
        int x = bomber.getX() + gridSize / 2;
        int y = bomber.getY() + gridSize / 2;
        int gridX = x / gridSize;
        int gridY = y / gridSize;
        if (gridX == bomber.getGridX() && gridY == bomber.getGridY()) {
            return true;
        }
        return false;
    }

    public boolean ifCollideDo(Entity other) {
        if (hasBrick()) {
            return false;
        }
        if (other instanceof Bomber) {
            if (collision(other)) {
                if (map.getDynamicEntityList().size() != 0) {
                    return false;
                }

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

    //   GETTER, SETTER
    public int getHasBomExplosion() {
        return hasBomExplosion;
    }

    public void setHasBomExplosion(int hasBomExplosion) {
        this.hasBomExplosion = hasBomExplosion;
    }

    public int getNumBomExplosion() {
        return numBomExplosion;
    }

    public void setNumBomExplosion(int numBomExplosion) {
        this.numBomExplosion = numBomExplosion;
    }
}