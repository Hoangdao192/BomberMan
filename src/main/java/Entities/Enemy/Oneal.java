package Entities.Enemy;

import Component.Animation;
import Component.AnimationManager;
import Component.PathFinding.ChasingPlayer;
import Component.Sprite;
import Entities.*;
import Map.Map;
import Utils.RandomInt;
import Utils.Vector2i;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Oneal extends Enemy {
    private final int DEFAULT_SPEED = 3;
    private AnimationManager animationManager;
    private Vector2i currentDirection;
    //  Phạm vi lớn nhất để xác định Player
    private final int MAX_DETECT_RADIUS = 5;
    ChasingPlayer chasingPlayer;

    public Oneal(int x, int y, int width, int height, Map map) {
        super(x, y, width, height, null, map);
        createAnimation();
        createHitBox();
        createMovement();
    }

    private void createHitBox() {
        createHitBox(2, 2, 28, 28);
    }

    private void createMovement() {
        currentDirection = new Vector2i(1, 0);
        movement.setSpeed(DEFAULT_SPEED);
        chasingPlayer = new ChasingPlayer(this, map, MAX_DETECT_RADIUS);
    }

    private void createAnimation() {
        animationManager = new AnimationManager(this);
        Animation moveLeftAnimation = new Animation(
                this, this.width, this.height, 2,
                Sprite.ONEAL_MOVE_LEFT_1, Sprite.ONEAL_MOVE_LEFT_2, Sprite.ONEAL_MOVE_LEFT_3
        );

        Animation moveRightAnimation = new Animation(
                this, this.width, this.height, 2,
                Sprite.ONEAL_MOVE_RIGHT_1, Sprite.ONEAL_MOVE_RIGHT_2, Sprite.ONEAL_MOVE_RIGHT_3
        );

        Animation dieAnimation = new Animation(
                this, this.width, this.height, 2,
                Sprite.ONEAL_DIE, Sprite.ENEMY_DIE_1, Sprite.ENEMY_DIE_2, Sprite.ENEMY_DIE_3
        );

        animationManager.addAnimation("MOVE LEFT", moveLeftAnimation);
        animationManager.addAnimation("MOVE RIGHT", moveRightAnimation);
        animationManager.addAnimation("DEAD", dieAnimation);
        animationManager.play("MOVE LEFT");
    }

    @Override
    protected void updateMovement() {
        chasingPlayer.update();
        movement.move();
    }

    private void updateAnimation() {
        animationManager.update();
    }

    @Override
    public void update() {
        updateMovement();
        hitBox.update();
        updateGridPosition();
        updateAnimation();
    }

    @Override
    public void render(int x, int y, GraphicsContext graphicsContext) {
        //hitBox.render(x + hitBox.getOffsetX(), y + hitBox.getOffsetY(), graphicsContext);
        animationManager.render(graphicsContext, x, y);
    }
}
