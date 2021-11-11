package Entities;

import Component.*;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bomb extends StaticEntity {
    private Animation mainAnimation;
    private Map map;
    private boolean exploded;

    private ArrayList<BombFlame> bombFlames;

    //  Phạm vi nổ tối đa
    private int explodeRadius;
    //  Phạm vi nổ về các hướng khi có vật cản
    private int maxLeft;
    private int maxRight;
    private int maxUp;
    private int maxDown;

    //  Thời gian tính theo nanoSecond
    //  Thời gian chờ đến khi bom nổ
    private long waitToExplode = 1500000000;
    private long waitToDelete = 2000000000;
    //  Thời gian bom được đặt
    private long startTime;
    //  Đếm thời gian từ khi bom được đặt
    private long countTime;

    public Bomb(int explodeRadius, int x, int y, int width, int height, Map map) {
        super(x, y, width, height, map.getGridSize(), null);
        createAnimation();
        startTime = System.nanoTime();
        countTime = 0;
        this.map = map;

        collision = true;

        exploded = false;
        //  Khởi tạo phạm vi nổ
        this.explodeRadius = explodeRadius;
        maxLeft = explodeRadius;
        maxRight = explodeRadius;
        maxDown = explodeRadius;
        maxUp = explodeRadius;

        createHitbox();
    }

    @Override
    public void update() {
        mainAnimation.update();
        countTime = System.nanoTime() - startTime;
        if (!exploded && countTime >= waitToExplode) {
            explode();
            countTime = 0;
        }
        if (exploded &&
                bombFlames.get(0).getAnimation().getCurrentFrame()
                == bombFlames.get(0).getAnimation().getNumberOfFrame() - 1) {
            exist = false;
            removeBombExplode();
            countTime = 0;
        }
    }

    private void explode() {
        exploded = true;
        calculateExplodeRadius();
        createBombExplode();
    }

    private void removeBombExplode() {
        for (int i = 0; i < bombFlames.size(); ++i) {
            bombFlames.get(i).setExist(false);
        }
        bombFlames.clear();
    }

    public void createAnimation() {
        mainAnimation = new Animation(this, null, this.width, this.height, 4);
        mainAnimation.addSprite(Sprite.BOMB_NORMAL_1);
        mainAnimation.addSprite(Sprite.BOMB_NORMAL_2);
        mainAnimation.addSprite(Sprite.BOMB_NORMAL_3);
    }

    private void createHitbox() {
        hitBox = new HitBox(this, 0, 0, width, height);
    }

    /**
     * Tính toán phạm vi nổ của bom theo từng hướng
     * Nếu gặp vật cản thì phạm vi nổ sẽ nhỏ lại
     */
    private void calculateExplodeRadius() {
        int gridSize = map.getGridSize();
        System.out.println(gridSize);
        int gridX = x / gridSize;
        int gridY = y / gridSize;
        System.out.println("This: " + gridX + " " + gridY);
        ArrayList<Entity> entities = map.getEntityList();
        for (int i = 0; i < entities.size(); ++i) {
            Entity currentEntity = entities.get(i);
            if (currentEntity == this || !currentEntity.collisionAble()
                || (!(currentEntity instanceof Stone)
                && !(currentEntity instanceof Brick))) continue;
            //  Lấy tọa độ grid của entity
            int entityGridX = currentEntity.x / gridSize;
            int entityGridY = currentEntity.y / gridSize;

            //  Kiểm tra bên phải
            if (currentEntity.getX() >= x + gridSize - 1
                    && currentEntity.getX() <= x + (explodeRadius + 1) * gridSize - 1
                    && gridY == entityGridY) {
                //  Có thể có nhiều vật cản cản trở phạm vi nổ
                //  Lấy phạm vi nổ nhỏ nhất
                if (maxRight > entityGridX - 1 - gridX) {
                    maxRight = entityGridX - 1 - gridX;
                    if (currentEntity instanceof Brick) {
                        currentEntity.die();
                    }
                }
                System.out.println("Other: " + entityGridX + " " + entityGridY);
            }
            //  Kiểm tra bên trái
            else if (currentEntity.getX() >= x - explodeRadius * gridSize - 1
                    && currentEntity.x <= x
                    && gridY == entityGridY) {
                if (maxLeft > gridX - 1 - entityGridX) {
                    maxLeft = gridX - 1 - entityGridX;
                    if (currentEntity instanceof Brick) {
                        currentEntity.die();
                    }
                }
                System.out.println("Other: " + entityGridX + " " + entityGridY);
            }
            //  Kiểm tra bên trên
            else if (currentEntity.getY() <= y
                    && currentEntity.getY() >= y - explodeRadius * gridSize - 1
                    && gridX == entityGridX) {
                if (maxUp > gridY - 1 - entityGridY) {
                    maxUp = gridY - 1 - entityGridY;
                    if (currentEntity instanceof Brick) {
                        currentEntity.die();
                    }
                }
                System.out.println("Other: " + entityGridX + " " + entityGridY);
            }
            //  Kiểm tra bên dưới
            else if (currentEntity.getY() >= y + gridSize - 1
                    && currentEntity.getY() <= y + (explodeRadius + 1) * gridSize - 1
                    && gridX == entityGridX) {
                if(maxDown > entityGridY - 1 - gridY) {
                    maxDown = entityGridY - 1 - gridY;
                    if (currentEntity instanceof Brick) {
                        currentEntity.die();
                    }
                }
                System.out.println("Other: " + entityGridX + " " + entityGridY);
            }
        }
        System.out.println(maxLeft + " " + maxRight + " " + maxUp + " " + maxDown);
    }

    private void createBombExplode() {
        bombFlames = new ArrayList<>();
        //  Center
        BombFlame explode1 = new BombFlame(
                x, y, gridSize, gridSize, gridSize, BombFlame.FLAME_CENTER
        );
        explode1.createHitBox(4, 4, 24, 24);
        explode1.setCollision(true);
        bombFlames.add(explode1);
        //  Bên phải
        for (int i = 1; i <= maxRight; ++i) {
            BombFlame explodeRight;
            if (i == maxRight) {
                explodeRight  = new BombFlame(
                        x + i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_RIGHT);
                explodeRight.createHitBox(8, 10, 24, 16);
            } else {
                explodeRight  = new BombFlame(
                        x + i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_HORIZON);
                explodeRight.createHitBox(0, 10, 32, 16);
            }
            explodeRight.setCollision(true);
            bombFlames.add(explodeRight);
        }

        //  Vẽ trái
        for (int i = 1; i <= maxLeft; ++i) {
            BombFlame explodeLeft;
            if (i == maxLeft) {
                explodeLeft = new BombFlame(
                        x - i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_LEFT);
                explodeLeft.createHitBox(0, 10, 24, 16);
            } else {
                explodeLeft = new BombFlame(
                        x - i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_HORIZON);
                explodeLeft.createHitBox(0, 10, 32, 16);
            }
            explodeLeft.setCollision(true);
            bombFlames.add(explodeLeft);
        }

        //  Vẽ dưới
        for (int i = 1; i <= maxDown; ++i) {
            BombFlame explodeDown;
            if (i == maxDown) {
                explodeDown = new BombFlame(
                        x, y + i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_DOWN);
                explodeDown.createHitBox(8, 0, 16, 24);
            } else {
                explodeDown = new BombFlame(
                        x, y + i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_VERTICAL);
                explodeDown.createHitBox(8, 0, 16, 32);
            }
            explodeDown.setCollision(true);
            bombFlames.add(explodeDown);
        }

        //  vẽ trên
        for (int i = 1; i <= maxUp; ++i) {
            BombFlame explodeUp;
            if (i == maxUp) {
                explodeUp  = new BombFlame(
                        x, y - i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_UP);
                explodeUp.createHitBox(8, 8, 16, 24);
            } else {
                explodeUp  = new BombFlame(
                        x, y - i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_VERTICAL);
                explodeUp.createHitBox(8, 0, 16, 32);
            }
            explodeUp.setCollision(true);
            bombFlames.add(explodeUp);
        }

        for (int i = 0; i < bombFlames.size(); ++i) {
            map.addEntity(bombFlames.get(i));
        }
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (exist) {
            if (!exploded) mainAnimation.render(x, y, graphicsContext);
        }
    }
}
