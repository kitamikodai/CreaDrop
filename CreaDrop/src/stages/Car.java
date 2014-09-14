/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import collision.Collision;
import collision.CollisionSquare;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Richter
 */
public class Car extends CollisionSquare {

    // ------ <Constants Value> ----- ----- ----- -----
    public static final double MOVESPEED = 0.1;
    public static final double MOVESPEED_MAX = 3.0;
    public static final double GRAVITY = 1;
    public static final double GRAVITY_MAX = 10.0;
    private final double SIZE_X, SIZE_Y;

    // ------ <Field> ----- ----- ----- -----
    private double moveX, moveY;
    private double nextX, nextY;
    private double catchX, catchY;
    private double gravity;
    private boolean catchMouse;
    private boolean catchMerry;
    private boolean arrival;
    Image image;
    ImageView view;

    // ------ <Constructor> ----- ----- ----- -----	

    public Car(Image image) {
        super(640, 200, 270, 70,
                640, 0, 300, 300,
                true, false, true,
                image, null);

        catchMouse = false;
        this.SIZE_X = 300;
        this.SIZE_Y = 300;

        nextX = posX_property.get();
        nextY = posY_property.get();

        setingEvents();
    }

    private void setingEvents() {
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (catchMouse) {
                    nextX = event.getSceneX() - catchX;
//					nextY = event.getSceneY() - catchY;
                    gravity = 0;
                }
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                catchMouse = true;
                catchX = event.getSceneX() - nextX;
                catchY = event.getSceneY() - nextY;
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                catchMouse = false;
            }

        });
    }

    // ------ <Override> ----- ----- ----- -----
    @Override
    public void catchMerry() {
        catchMerry = true;
    }

    @Override
    public boolean releaseMerry() {
        create = false;
        return !arrival;
    }

    @Override
    public void executeTask_initialize() {
        arrival = false;
    }

    @Override
    public void executeTask(ObservableList<Collision> collisionObjList) {
        //gravity
        if (gravity < GRAVITY_MAX) {
            gravity += GRAVITY;
        }
        if (catchMerry) {
            moveX += MOVESPEED;
        }

        moveY += gravity;

        for (Collision obj : collisionObjList) {
            // oneself
            if (obj == this) {
                continue;
            }

            // isCollision
            if (obj.isCollision(nextX, nextY, SIZE_X, SIZE_Y)) {
                if (obj.isGround()) {
                    executeTask_isGround(obj);
                }

                if (obj.isWater()) {
                    executeTask_isWater(obj);
                }

                if (obj.isCreate()) {
                    executeTask_isCreate(obj);
                }
            }
        }

        if (MOVESPEED_MAX < moveX) {
            moveX = MOVESPEED_MAX;
        }
//		if(MOVESPEED_MAX < moveY) {
//			moveY = MOVESPEED_MAX;
//		}

        nextX += moveX;
        nextY += moveY;
    }

    @Override
    public void executeTask_changed() {
        posX_property.set(nextX);
        posY_property.set(nextY);
        col_x.set(nextX + 20);
        col_y.set(nextY + 200);
    }

    private void executeTask_isGround(Collision obj) {
        // walk on the object
        if (obj.isCollision_ground(col_y.get(), SIZE_Y)) {
            moveY = 0;
            gravity = 0;
            nextY = obj.getY1() - SIZE_Y;
        } else {
            if (obj.isCollision_right(col_x.get(), SIZE_X)) {
                catchMouse = false;
                moveX = 0;
                nextX = obj.getX1() + obj.getColSizeX();
            }
            if (obj.isCollision_left(col_x.get(), SIZE_X)) {
                arrival = true;
                catchMouse = false;
                moveX = 0;
                nextX = obj.getX1() - SIZE_X;
            }
        }
    }

    private void executeTask_isWater(Collision obj) {
    }

    private void executeTask_isCreate(Collision obj) {
    }

}
