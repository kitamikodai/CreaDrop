/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import collision.Collision;
import collision.CollisionSquare;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx_sample.Merry;

/**
 *
 * @author Richter
 */
public abstract class Stage {

    // ------ <Constants Value> ----- ----- ----- -----
    public static final int TASK_FRAMERATE = 40;
    public static final double CREATE_POSX = 640;
    public static final double CREATE_POSY = 0;

    // ------ <Field> ----- ----- ----- -----	
    private ExecutorService service;
    // collison objects
    ObservableList<Collision> collisionObjList;
    // gamePane
    Pane gamePane;
    // player cahractor
    Merry merry;
    // start button on
    boolean play;
    boolean endTask;

    // ------ <Constructor> ----- ----- ----- -----	
    Stage(Pane gamePane) {
        this.gamePane = gamePane;
        play = false;
        endTask = false;
        service = Executors.newSingleThreadExecutor();
    }

    // ------ <Factory Method> ----- ----- ----- -----
    private Collision createObject(String name, Image image) {
        Collision newObject = null;

        switch (name) {
            case "ship":
                newObject = new Ship(image);
                break;
            case "car":
                newObject = new Car(image);
                break;
            case "ufo":
                newObject = new Ufo(image);
                break;
            default:
                System.out.println("can not create object.");
        }

        return newObject;
    }

    // ------ <Setter> ----- ----- ----- -----
    public void setPlay(boolean play) {
        this.play = play;
    }

    abstract public void setStage(Pane gamePane);

    // ------ <Original Method> ----- ----- ----- -----
    public void addCreateObject(String name, Image image) {
//		Collision newObject = createObject(name ,image);
        CollisionSquare newObject = (CollisionSquare) createObject(name, image);

        newObject.executeTask_initialize();
        collisionObjList.add(newObject);
        gamePane.getChildren().add(newObject.getImageView());
        gamePane.getChildren().add(newObject);
    }

    public void executeTask_preparation() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                for (Collision obj : collisionObjList) {
                    obj.executeTask_initialize();
                }

                while (true) {
                    int i = 0;
                    for (Collision obj : collisionObjList) {
                        obj.executeTask(collisionObjList);
                        updateMessage("update_obj" + ++i);
                    }

                    if (play || endTask) {
                        break;
                    }

                    try {
                        Thread.sleep(TASK_FRAMERATE);
                    } catch (InterruptedException e) {
                    }
                }

                return null;
            }
        };

        task.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (Collision obj : collisionObjList) {
                    obj.executeTask_changed();
                }

                System.out.println("message");
            }
        });

        service.submit(task);
    }

    public void executeTask() {
        Task<Void> task = new Task<Void>() {
            int i = 0;

            @Override
            protected Void call() throws Exception {
                merry.executeTask_initialize();
                for (Collision obj : collisionObjList) {
                    obj.executeTask_initialize();
                }

                while (true) {
                    merry.executeTask(collisionObjList);
                    for (Collision obj : collisionObjList) {
                        obj.executeTask(collisionObjList);
                    }

                    updateProgress(++i, i + 1);
                    System.out.println("update" + i);

                    if (endTask) {
                        break;
                    }

                    try {
                        Thread.sleep(TASK_FRAMERATE);
                    } catch (InterruptedException e) {
                    }

                }

                return null;
            }

            @Override
            protected void succeeded() {
                System.out.println("end task");
            }
        };

        task.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				System.out.println("changed Stage");
                merry.executeTask_changed();
                for (Collision obj : collisionObjList) {
                    obj.executeTask_changed();
                }
            }
        });

        service.submit(task);
    }

    public void shutdownTask() {
        endTask = true;
        service.shutdown();
    }
	// ------ <Override> ----- ----- ----- -----
}
