/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collision;

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
public class CollisionSquare extends Rectangle implements Collision {

	// ------ <Constants Value> ----- ----- ----- -----
    // ------ <Field> ----- ----- ----- -----
    // preparation or playing or clear
    public static short playStatus;
    final private String objectName;
    protected DoubleProperty col_x, col_y;
    protected DoubleProperty col_sizeX, col_sizeY;
    protected DoubleProperty posX_property, posY_property;

    // collision type
    protected boolean ground;
    protected boolean water;
    protected boolean create;

    ImageView view;

    // ------ <Constructor> ----- ----- ----- -----	
    public CollisionSquare(double x1, double y1, double x2, double y2,
            double posX, double posY, double sizeX, double sizeY,
            boolean isGround, boolean isWater, boolean isCreate,
            Image image, String objectName) {
//		super(x1 ,y1 ,x2 ,y2);
        super(0, 0, x2, y2);

        view = new ImageView(image);

        initialize_collision(x1, y1, x2, y2);
        initialize_position(posX, posY);
        initialize_size(sizeX, sizeY);
        initialize_collisionType(isGround, isWater, isCreate);

        this.layoutXProperty().bind(col_x);
        this.layoutYProperty().bind(col_y);
        view.layoutXProperty().bind(posX_property);
        view.layoutYProperty().bind(posY_property);

        this.objectName = objectName;
    }

    private void initialize_collision(double x1, double y1, double x2, double y2) {
        // collision property
        col_x = new SimpleDoubleProperty(x1);
        col_y = new SimpleDoubleProperty(y1);
        col_sizeX = new SimpleDoubleProperty(x2);
        col_sizeY = new SimpleDoubleProperty(y2);

        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
//		this.setStroke(Color.DARKBLUE);
    }

    private void initialize_position(double posX, double posY) {
        // position property
        posX_property = new SimpleDoubleProperty(posX);
        posY_property = new SimpleDoubleProperty(posY);
    }

    private void initialize_size(double sizeX, double sizeY) {
        view.setFitWidth(sizeX);
        view.setFitHeight(sizeY);
    }

    private void initialize_collisionType(boolean isGround, boolean isWater, boolean isCreate) {
        this.ground = isGround;
        this.water = isWater;
        this.create = isCreate;
    }

    // ------ <Getter> ----- ----- ----- -----
    @Override
    public boolean isGround() {
        return ground;
    }

    @Override
    public boolean isWater() {
        return water;
    }

    @Override
    public boolean isCreate() {
        return create;
    }

    @Override
    public double getX1() {
        return col_x.get();
    }

    @Override
    public double getY1() {
        return col_y.get();
    }

    @Override
    public double getColSizeX() {
        return col_sizeX.get();
    }

    @Override
    public double getColSizeY() {
        return col_sizeY.get();
    }

    @Override
    public ImageView getImageView() {
        return view;
    }

    // ------ <Setter> ----- ----- ----- -----
    public void setPosition(double x, double y) {
        posX_property.set(x);
        posY_property.set(y);
    }

    public void setSize(double x, double y) {
        view.setFitWidth(x);
        view.setFitHeight(y);
    }

    public void setPlayStatus(short s) {
        playStatus = s;
    }
	// ------ <Original Method> ----- ----- ----- -----
    // ------ <Override> ----- ----- ----- -----

    @Override
    public boolean isCollision(double x, double y, double x2, double y2) {

        return (col_x.get() < x + x2) && (x < col_x.get() + col_sizeX.get())
                && (col_y.get() < y + y2) && (y < col_y.get() + col_sizeY.get());
    }

    @Override
    public boolean isCollision_ground(double y, double h) {
        return (y < col_y.get()) && (col_y.get() < y + h);
    }

    @Override
    public boolean isCollision_left(double x, double sizeX) {
        return (x < col_x.get() + col_sizeX.get());
    }

    @Override
    public boolean isCollision_right(double x, double sizeX) {
        return (col_x.get() < x + sizeX);
    }

    @Override
    public void executeTask_initialize() {
    }

    @Override
    public void executeTask(ObservableList<Collision> collisionObjList) {
    }

    @Override
    public void executeTask_changed() {
    }

    @Override
    public boolean releaseMerry() {
        return false;
    }

    @Override
    public void catchMerry() {
    }

}
