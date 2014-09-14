/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import management_systemFX.MainClass;

/**
 *
 * @author Richter
 */
public class JavaFX_sample extends Application {

    // ------ <Field> ----- ----- ----- -----
    public Game game;
    Button btn, resetButton;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    private MainClass mainclass;

    @Override
    public void start(Stage primaryStage) {
        StackPane rootPane = new StackPane();
        rootPane.setPrefSize(1280, 800);
        Pane gamePane = new Pane();

        game = new Game(gamePane);

        // close button
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                game.exit();
            }
        });
        btn = new Button();
        btn.setLayoutX(1200);
        btn.setLayoutY(50);
        btn.setText("Push Start");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Stert Game");
                game.exec();
                btn.setVisible(false);
                resetButton.setVisible(true);
            }
        });
        resetButton = new Button();
        resetButton.setLayoutX(1220);
        resetButton.setLayoutY(50);
        resetButton.setText("reset");
        resetButton.setVisible(false);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.resetStage();
                btn.setVisible(true);
                resetButton.setVisible(false);
                gamePane.getChildren().add(btn);
                gamePane.getChildren().add(resetButton);
                mainclass.pc2.creatDrawnGetThred();

            }
        });

        Scene scene = new Scene(rootPane);

        gamePane.getChildren().add(btn);
        gamePane.getChildren().add(resetButton);
        rootPane.getChildren().add(gamePane);

        primaryStage.setTitle("Crea Drop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }
}
