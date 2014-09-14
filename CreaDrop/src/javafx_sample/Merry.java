/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_sample;

import collision.Collision;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Richter
 */
public class Merry {

    // ------ <Constants Value> ----- ----- ----- -----

    public static final int MERRY_SIZEX = 60;
    public static final int MERRY_SIZEY = 60;
    public static final int MERRY_TASKFRAMERATE = 50;
    public static final int MERRY_MOVESPEED = 1;
    public static final int MERRY_MOVESPEED_MAX = 3;
    public static final double MERRY_GRAVITY = 0.2;
    public static final double MERRY_GRAVITY_MAX = 4;

    // ------ <Field> ----- ----- ----- -----
    private ExecutorService service = Executors.newSingleThreadExecutor();
    private int clearCount;
    private double catchX, catchY;
    private boolean catchAction;
    private Collision catchObject;
    private boolean clear;

    double moveX, moveY;
    double nextX, nextY;
    double gravity;
    double clearPos;
    DoubleProperty x_property, y_property;
    Image image;
    ImageView view;

    //test
    public Rectangle rect;

    // ------ <Constructor> ----- ----- ----- -----	
    public Merry() {
        clear = false;
        clearCount = 0;
        clearPos = 0;

        catchAction = false;

        x_property = new SimpleDoubleProperty();
        y_property = new SimpleDoubleProperty();

        image = new Image(this.getClass().getResource("silverball.png").toString());
        view = new ImageView(image);
        view.layoutXProperty().bind(x_property);
        view.layoutYProperty().bind(y_property);
        view.setFitWidth(MERRY_SIZEX);
        view.setFitHeight(MERRY_SIZEY);
        view.setPreserveRatio(true);

        rect = new Rectangle(x_property.get(), y_property.get(), MERRY_SIZEX, MERRY_SIZEY);
        rect.layoutXProperty().bind(x_property);
        rect.layoutYProperty().bind(y_property);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.TRANSPARENT);
//		rect.setStroke(Color.HOTPINK);
    }

    public Merry(double x, double y) {
        this();
        x_property.set(x);
        y_property.set(y);
    }

    // ------ <Getter> ----- ----- ----- -----
    public double getX() {
        return x_property.get();
    }

    public double getY() {
        return y_property.get();
    }

    public ImageView getImageView() {
        return view;
    }

    // ------ <Setter> ----- ----- ----- -----
    public void setCoordinate(double x, double y) {
        x_property.set(x);
        y_property.set(y);
    }

    // ------ <Original Method> ----- ----- ----- -----
    public void executeTask_initialize() {
        nextX = x_property.get();
        nextY = y_property.get();
    }

    public void executeTask(ObservableList<Collision> collisionObjList) {
        if (clear) {
            executeTask_clear();
        } else if (!catchAction) {
            executeTask_playing(collisionObjList);
        } else {
            catchAction = catchObject.releaseMerry();
            nextX = catchObject.getX1() - catchX;
            nextY = catchObject.getY1() - MERRY_SIZEY;
        }
    }

    private void executeTask_playing(ObservableList<Collision> collisionObjList) {
        //gravity
        if (gravity < MERRY_GRAVITY_MAX) {
            gravity += MERRY_GRAVITY;
        }
        if (moveX < MERRY_MOVESPEED_MAX) {
            moveX += MERRY_MOVESPEED;
        }

        moveY += gravity;

        for (Collision obj : collisionObjList) {
            // isCollision
            if (obj.isCollision(nextX, nextY, MERRY_SIZEX, MERRY_SIZEY)) {
                if (obj.isCreate()) {
                    executeTask_isCreate(obj);
                }

                if (obj.isGround()) {
                    executeTask_isGround(obj);
                }

                if (obj.isWater()) {
                    executeTask_isWater(obj);
                }
            }
        }

        nextX += moveX;
        nextY += moveY;

        // clear
        if (1100 <= nextX) {
            gravity = 0;
            moveX = 0;
            moveY = 0;
            clearPos = nextY;
            clear = true;
        }
    }

    private void executeTask_clear() {
        double angle = 0;
        clearCount = (clearCount + 20) % 360;

        if (clearCount < 180) {
            angle = Math.toRadians(clearCount);
        }
        nextY = clearPos - Math.sin(angle) * 15;
    }

    public void executeTask_changed() {
//		System.out.println("Merry_change :" + nextX + " :" +nextY);
        x_property.set(nextX);
        y_property.set(nextY);
    }

    private void executeTask_isGround(Collision obj) {
        // walk on the object
        if (obj.isCollision_ground(nextY, MERRY_SIZEY)) {
            gravity = moveY = 0;
            nextY = obj.getY1() - MERRY_SIZEY;
        } // lateral collision to the object
        else {
            if (obj.isCollision_right(nextX, MERRY_SIZEX)) {
                moveX = 0;
                nextX = obj.getX1() - MERRY_SIZEX;
            }
        }
    }

    private void executeTask_isWater(Collision obj) {
    }

    private void executeTask_isCreate(Collision obj) {
        catchX = obj.getX1() - nextX;
        catchY = obj.getY1() - nextY;
        catchObject = obj;
        catchAction = true;
        obj.catchMerry();
    }

	// ------ <Override> ----- ----- ----- -----
}
