/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_sample;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import stages.Stage;
import stages.Stage1;

/**
 *
 * @author Richter
 */
public class Game extends Task {

	// ------ <Constants Value> ----- ----- ----- -----
    // ------ <Field> ----- ----- ----- -----
    Pane gamePane;
    // choose stage
    int stageNumber;
    public Stage stage;

    // ------ <Constructor> ----- ----- ----- -----	
    Game(Pane gamePane) {
        this.gamePane = gamePane;
        stageNumber = 1;
        stage = createStage(stageNumber);
        stage.setStage(gamePane);
        stage.executeTask_preparation();
    }

    // ------ <Factory Method> ----- ----- ----- -----
    private Stage createStage(int number) {
        Stage newStage = null;

        switch (number) {
            case 1:
                newStage = new Stage1(gamePane);
                break;
            default:
                System.out.println("can not create stage.");
        }

        return newStage;
    }

	// ------ <Getter> ----- ----- ----- -----
    // ------ <Setter> ----- ----- ----- -----
    public void setStage() {
        stage.setStage(gamePane);
    }

	// ------ <Original Method> ----- ----- ----- -----
    // press play button
    public void exec() {
        stage.setPlay(true);
        stage.executeTask();
    }

    // change stage

    public void changeStage(int stageNumber) {
        this.stageNumber = stageNumber;
        resetStage();
    }

    // push resetButton

    public void resetStage() {
        stage.shutdownTask();
        stage = createStage(stageNumber);
        stage.setStage(gamePane);
        stage.executeTask_preparation();
    }

    public void exit() {
        stage.shutdownTask();
    }

    // ------ <Override> ----- ----- ----- ----- 

    @Override
    protected Object call() throws Exception {
        stage.executeTask_preparation();
        return null;
    }
}
