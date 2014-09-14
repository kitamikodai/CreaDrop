/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collision;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 *
 * @author Richter
 */
public interface Collision {

    public void executeTask_initialize();

    public void executeTask(ObservableList<Collision> collisionObjList);

    public void executeTask_changed();

    public void catchMerry();

    public boolean releaseMerry();

    public boolean isCollision(double x, double y, double x2, double y2);

    public boolean isCollision_ground(double y, double sizeY);

    public boolean isCollision_left(double x, double sizeX);

    public boolean isCollision_right(double x, double sizeX);

    public boolean isGround();

    public boolean isWater();

    public boolean isCreate();

    public double getX1();

    public double getY1();

    public double getColSizeX();

    public double getColSizeY();

    public ImageView getImageView();
}
