package Entities;

import Component.*;
import Map.Map;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Bomb extends StaticEntity {
    private Animation animation;
    private Map map;

    //  Phạm vi nổ tối đa
    private int explodeRadius;
    //  Phạm vi nổ về các hướng khi có vật cản
    private int maxLeft;
    private int maxRight;
    private int maxUp;
    private int maxDown;

    //  Thời gian chờ đến khi bom nổ (nano second)
    private long waitToExplode = 2000000000;
    //  Thời gian bom được đặt
    private long startTime;
    //  Đếm thời gian từ khi bom được đặt
    private long countTime;

    //  CONSTRUCTOR
    public Bomb(int explodeRadius, int x, int y, int width, int height, Map map) {
        super(x, y, width, height, map.getGridSize(), null);
        createAnimation();
        startTime = System.nanoTime();
        countTime = 0;
        this.map = map;

        collision = true;

        //  Khởi tạo phạm vi nổ
        this.explodeRadius = explodeRadius;
        maxLeft = explodeRadius;
        maxRight = explodeRadius;
        maxDown = explodeRadius;
        maxUp = explodeRadius;

        createHitBox();
    }

    public void createAnimation() {
        sprite = Sprite.BOMB_NORMAL_1;
        animation = new Animation(
                this, this.width, this.height, 4,
                Sprite.BOMB_NORMAL_1, Sprite.BOMB_NORMAL_2, Sprite.BOMB_NORMAL_3
        );
    }

    private void createHitBox() {
        hitBox = new HitBox(
                this,
                width / sprite.getWidth(), 2 * height / sprite.getHeight(),
                width / sprite.getWidth() * 14, height / sprite.getHeight() * 14
        );
    }

    //  FUNCTIONS
    @Override
    public void update() {
        animation.update();
        countTime = System.nanoTime() - startTime;
        if (countTime >= waitToExplode) {
            explode();
            destroy();
        }
    }

    private void explode() {
        calculateExplodeRadius();
        createBombExplode();
    }

    /**
     * Kiểm tra xem entity có thể chặn phạm vi nổ của bom không.
     */
    private boolean entityCanBlockBomb(Entity entity) {
        if (entity instanceof Brick || entity instanceof Stone || entity instanceof Bomb) {
            if (entity instanceof Brick) {
                entity.die();
            }
            return true;
        }
        return false;
    }

    /**
     * Tính toán phạm vi nổ của bom theo từng hướng
     * Nếu gặp vật cản thì phạm vi nổ sẽ nhỏ lại
     */
    private void calculateExplodeRadius() {
        ArrayList<ArrayList<ArrayList<Entity>>> staticEntityList = map.getStaticEntityList();
        //  Duyệt bên phải
        for (int col = gridX + 1; col <= gridX + explodeRadius; ++col) {
            if (col >= staticEntityList.get(gridY).size()) {
                continue;
            }
            for (int j = 0; j < staticEntityList.get(gridY).get(col).size(); ++j) {
                Entity entity = staticEntityList.get(gridY).get(col).get(j);
                if (entityCanBlockBomb(entity)) {
                    maxRight = col - gridX - 1;
                    //  Phá vỡ vòng lặp
                    col = gridX + explodeRadius + 1;
                    break;
                }
            }
        }
        //  Duyệt bên trái
        for (int col = gridX - 1; col >= gridX - explodeRadius; --col) {
            if (col < 0) {
                continue;
            }
            for (int j = 0; j < staticEntityList.get(gridY).get(col).size(); ++j) {
                Entity entity = staticEntityList.get(gridY).get(col).get(j);
                if (entityCanBlockBomb(entity)) {
                    maxLeft = gridX - col - 1;
                    //  Phá vỡ vòng lặp
                    col = gridX - explodeRadius - 1;
                    break;
                }
            }
        }
        //  Duyệt bên dưới
        for (int row = gridY + 1; row <= gridY + explodeRadius; ++row) {
            if (row >= staticEntityList.size()) {
                continue;
            }
            for (int j = 0; j < staticEntityList.get(row).get(gridX).size(); ++j) {
                Entity entity = staticEntityList.get(row).get(gridX).get(j);
                //  Danh sách entity có thể chặn được bom
                if (entityCanBlockBomb(entity)) {
                    maxDown = row - gridY - 1;
                    //  Phá vỡ vòng lặp
                    row = gridY + explodeRadius + 1;
                    break;
                }
            }
        }
        //  Duyệt bên trên
        for (int row = gridY - 1; row >= gridY - explodeRadius; --row) {
            if (row >= staticEntityList.size()) {
                continue;
            }
            for (int j = 0; j < staticEntityList.get(row).get(gridX).size(); ++j) {
                Entity entity = staticEntityList.get(row).get(gridX).get(j);
                //  Danh sách entity có thể chặn được bom
                if (entityCanBlockBomb(entity)) {
                    maxUp = gridY - row - 1;
                    //  Phá vỡ vòng lặp
                    row = gridY - explodeRadius - 1;
                    break;
                }
            }
        }
    }

    /**
     * Tạo ra các quầng lửa khi bom nổ.
     */
    private void createBombExplode() {
        //  Center
        BombFlame explodeCenter = new BombFlame(
                x, y, gridSize, gridSize, gridSize, BombFlame.FLAME_CENTER
        );
        map.addEntity(explodeCenter);
        //  Bên phải
        for (int i = 1; i <= maxRight; ++i) {
            BombFlame explodeRight = null;
            if (i == maxRight) {
                explodeRight  = new BombFlame(
                        x + i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_RIGHT);
            } else {
                explodeRight  = new BombFlame(
                        x + i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_HORIZON);
            }
            map.addEntity(explodeRight);
        }

        //  Vẽ trái
        for (int i = 1; i <= maxLeft; ++i) {
            BombFlame explodeLeft = null;
            if (i == maxLeft) {
                explodeLeft = new BombFlame(
                        x - i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_LEFT);
            } else {
                explodeLeft = new BombFlame(
                        x - i * gridSize, y, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_HORIZON);
            }
            map.addEntity(explodeLeft);
        }

        //  Vẽ dưới

        for (int i = 1; i <= maxDown; ++i) {
            BombFlame explodeDown = null;
            if (i == maxDown) {
                explodeDown = new BombFlame(
                        x, y + i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_DOWN);
            } else {
                explodeDown = new BombFlame(
                        x, y + i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_VERTICAL);
            }
            map.addEntity(explodeDown);
        }

        //  vẽ trên

        for (int i = 1; i <= maxUp; ++i) {
            BombFlame explodeUp = null;
            if (i == maxUp) {
                explodeUp = new BombFlame(
                        x, y - i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_UP);
            } else {
                explodeUp = new BombFlame(
                        x, y - i * gridSize, gridSize, gridSize, gridSize,
                        BombFlame.FLAME_VERTICAL);
            }
            map.addEntity(explodeUp);
        }
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        if (exist) {
            animation.render(x, y, graphicsContext);
        }
    }
}
