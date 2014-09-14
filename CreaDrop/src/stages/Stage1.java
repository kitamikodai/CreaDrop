/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import collision.CollisionSquare;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx_sample.Merry;

/**
 *
 * @author Richter
 */
public class Stage1 extends Stage {

    // ------ <Constants Value> ----- ----- ----- -----
    public static final int merry_posX = 0;
    public static final int merry_posY = 230;

    public static final int scaffold1_sizeX = 640;
    public static final int scaffold1_sizeY = 600;
    public static final int scaffold1_posX = 0;
    public static final int scaffold1_posY = 200;
    public static final int scaffold1_colX1 = 0;
    public static final int scaffold1_colY1 = 300;
    public static final int scaffold1_colX2 = 400;
    public static final int scaffold1_colY2 = 600;

    public static final int scaffold2_sizeX = 640;
    public static final int scaffold2_sizeY = 400;
    public static final int scaffold2_posX = 800;
    public static final int scaffold2_posY = 460;
    public static final int scaffold2_colX1 = 950;
    public static final int scaffold2_colY1 = 550;
    public static final int scaffold2_colX2 = 300;
    public static final int scaffold2_colY2 = 300;

    public static final int sea1_sizeX = 1280;
    public static final int sea1_sizeY = 800;
    public static final int sea1_posX = 0;
    public static final int sea1_posY = 100;
    public static final int sea1_colX1 = 0;
    public static final int sea1_colY1 = 700;
    public static final int sea1_colX2 = 1280;
    public static final int sea1_colY2 = 200;

    public static final int sky1_sizeX = 400;
    public static final int sky1_sizeY = 400;
    public static final int sky1_posX = 400;
    public static final int sky1_posY = 400;

    // ------ <Field> ----- ----- ----- -----	
    private CollisionSquare scaffold1;
    private CollisionSquare scaffold2;
    private CollisionSquare sea1;
    private ImageView sky1;

    // ------ <Constructor> ----- ----- ----- -----	
    public Stage1(Pane gamePane) {
        super(gamePane);
        collisionObjList = FXCollections.observableArrayList();

        merry = new Merry(merry_posX, merry_posY);

        // create scaffold1
        scaffold1 = new CollisionSquare(scaffold1_colX1, scaffold1_colY1, scaffold1_colX2, scaffold1_colY2,
                scaffold1_posX, scaffold1_posY, scaffold1_sizeX, scaffold1_sizeY,
                true, false, false,
                new Image(this.getClass().getResource("scaffold1.png").toString()),
                "scaffold1");

        // create scaffold2
        scaffold2 = new CollisionSquare(scaffold2_colX1, scaffold2_colY1, scaffold2_colX2, scaffold2_colY2,
                scaffold2_posX, scaffold2_posY, scaffold2_sizeX, scaffold2_sizeY,
                true, false, false,
                new Image(this.getClass().getResource("scaffold2.png").toString()),
                "scaffold2");
		//tabret size : 1920 ,850
        // create sea1
        sea1 = new CollisionSquare(sea1_colX1, sea1_colY1, sea1_colX2, sea1_colY2,
                sea1_posX, sea1_posY, sea1_sizeX, sea1_sizeY,
                false, true, false,
                new Image(this.getClass().getResource("sea1.png").toString()),
                "sea1");

		// create test object ship
        // size : 100 * 100
        // col	: 100 * 50
/*		test1 = new Ship(CREATE_POSX+40 ,CREATE_POSY+200 ,250 , 100,
         CREATE_POSX, CREATE_POSY ,300, 300 ,
         true ,false ,true ,
         new Image(this.getClass().getResource("test1.png").toString()) );
         */
		// setup sky1
/*		image_sky1 = new Image(this.getClass().getResource("sky1.png").toString());
         sky1 = new ImageView(image_sky1);
         sky1.setFitWidth(sky1_sizeX);
         sky1.setFitHeight(sky1_sizeY);
         sky1.setPreserveRatio(true);
         */
        collisionObjList.add(scaffold1);
        collisionObjList.add(scaffold2);
        collisionObjList.add(sea1);
    }

	// ------ <Getter> ----- ----- ----- -----
    // ------ <Setter> ----- ----- ----- -----	
    // ------ <Original Method> ----- ----- ----- -----
    // ------ <Override> ----- ----- ----- -----
    @Override
    public void setStage(Pane gamePane) {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(scaffold1.getImageView());
        gamePane.getChildren().add(scaffold2.getImageView());
        gamePane.getChildren().add(merry.getImageView());
        gamePane.getChildren().add(sea1.getImageView());

        // test draw collision
        gamePane.getChildren().add(merry.rect);
        gamePane.getChildren().add(scaffold1);
        gamePane.getChildren().add(scaffold2);
        gamePane.getChildren().add(sea1);
    }

    @Override
    public void executeTask() {
        super.executeTask();
    }
}
