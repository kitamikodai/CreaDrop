package management_systemFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 管理システム画面の3枚目を構成するクラス. <br>
 * ステージ選択を行い、選択されたステージをプロジェクタに投影する。
 */
public class LayoutPC3 {

    /**
     * LayoutPC3クラスのシーン
     */
    public Scene pc3Scene;
    /**
     * LayoutPC3クラスのペイン
     */
    private Pane root;
    /**
     * イメージ
     */
    private Image backImage, stage1Image, stage2Image, stage3Image;
    /**
     * イメージビュー
     */
    private ImageView backImageView, stage1ImageView, stage2ImageView, stage3ImageView;
    /**
     * ステージ1ボタン
     */
    private Button stage1Button;
    /**
     * ステージ2ボタン
     */
    private Button stage2Button;
    /**
     * ステージ3ボタン
     */
    private Button stage3Button;
    /**
     * 投影ボタン
     */
    private Button projectionButton;
    /**
     * 戻るボタン
     */
    private Button backButton;

    /**
     * ステージの選択状態 true == 選択中 を表す。
     * 生成時は、falseで初期化。
     */
    private boolean checkStage1, checkStage2, checkStage3;

    public MainClass mainclass;

    //private Game game;
    //コンポーネントのパラメータ//
    private final double scale = 1.0;
    private final double sceneWidth = 800.0 * scale;
    private final double sceneHeight = 600.0 * scale;
    private final double ButtonFontSize = 36.0 * scale;
    private final double ButtonPrefWidth = 350.0 * scale, ButtonPrefHeight = 150.0 * scale;
    private final double stageButtonImageWidth = 140.0 * scale, stageButtonImageHeight = 140.0 * scale;
    private final double stage1ButtonX = 200.0 * scale, stage1ButtonY = 30.0 * scale;
    private final double stage2ButtonX = 200.0 * scale, stage2ButtonY = 215.0 * scale;
    private final double stage3ButtonX = 200.0 * scale, stage3ButtonY = 400.0 * scale;
    private final double projectionButtonX = 665.0 * scale, projectionButtonY = 515.0 * scale;
    private final double backButtonX = 25.0 * scale, backButtonY = 25.0 * scale;
    private final double backButtonImageWidth = 92.0 * scale, backButtonImageHeight = 60.0 * scale;
    //---------------------------------/

    /**
     * MainClassクラスの参照のセッター. <br>
     *
     * @param mc_main MainClassクラスの参照
     */
    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }

    /**
     * LayoutPC3クラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。 各ボタンに適した、ボタンイベントの登録を行う。
     *
     */
    public LayoutPC3() {
        root = new Pane();
        root.setStyle("-fx-background-color: blue");
        pc3Scene = new Scene(root, sceneWidth, sceneHeight); //ウィンドウサイズ

        //game = new Game(new Pane());
        checkStage1 = false;
        checkStage2 = false;
        checkStage3 = false;

        stage1Image = new Image(getClass().getResourceAsStream("Stage1_crop2.jpg"));
        stage1ImageView = new ImageView(stage1Image);
        stage1ImageView.setFitWidth(stageButtonImageWidth);
        stage1ImageView.setFitHeight(stageButtonImageHeight);
        stage1Button = new Button("ステージ1", stage1ImageView);
        stage1Button.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        stage1Button.setLayoutX(stage1ButtonX);
        stage1Button.setLayoutY(stage1ButtonY);
        stage1Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        stage1Button.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * ステージ1ボタンのイベント. <br>
             * stage1Buttonの表示文字を黄色に、stage2Buttonとstage3Buttonの表示文字を黒色にする。
             * checkStage1にtrueを代入し、その他のcheckStageをfalseにする。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                stage1Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: yellow");
                stage2Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                stage3Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                checkStage1 = true;
                checkStage2 = false;
                checkStage3 = false;
            }
        });

        stage2Image = new Image(getClass().getResourceAsStream("Stage2_crop.jpg"));
        stage2ImageView = new ImageView(stage2Image);
        stage2ImageView.setFitWidth(stageButtonImageWidth);
        stage2ImageView.setFitHeight(stageButtonImageHeight);
        stage2Button = new Button("ステージ2", stage2ImageView);
        stage2Button.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        stage2Button.setLayoutX(stage2ButtonX);
        stage2Button.setLayoutY(stage2ButtonY);
        stage2Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        stage2Button.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * ステージ2ボタンのイベント. <br>
             * stage2Buttonの表示文字を黄色に、stage1Buttonとstage3Buttonの表示文字を黒色にする。
             * checkStage2にtrueを代入し、その他のcheckStageをfalseにする。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                stage1Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                stage2Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: yellow");
                stage3Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                checkStage1 = false;
                checkStage2 = true;
                checkStage3 = false;
            }
        });

        stage3Image = new Image(getClass().getResourceAsStream("Stage3_crop.jpg"));
        stage3ImageView = new ImageView(stage3Image);
        stage3Button = new Button("ステージ3", stage3ImageView);
        stage3ImageView.setFitWidth(stageButtonImageWidth);
        stage3ImageView.setFitHeight(stageButtonImageHeight);
        stage3Button.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        stage3Button.setLayoutX(stage3ButtonX);
        stage3Button.setLayoutY(stage3ButtonY);
        stage3Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        stage3Button.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * ステージ3ボタンのイベント. <br>
             * stage3Buttonの表示文字を黄色に、stage1Buttonとstage2Buttonの表示文字を黒色にする。
             * checkStage3にtrueを代入し、その他のcheckStageをfalseにする。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                stage1Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                stage2Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                stage3Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: yellow");
                checkStage1 = false;
                checkStage2 = false;
                checkStage3 = true;
            }
        });

        projectionButton = new Button("投影");
        projectionButton.setLayoutX(projectionButtonX);
        projectionButton.setLayoutY(projectionButtonY);
        projectionButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        projectionButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 投影ボタンのイベント. <br>
             * ステージの選択状態(checkStage)を判断。
             * checkStage == trueのステージをプロジェクタに投影。
             * checkStage = false　に戻してリセット。
             * stageButtonのフォントを元に戻す(黒色に)。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                if (checkStage1 == true) {
                    mainclass.pc2.fxSample.start(new Stage());
                    checkStage1 = false;
                    stage1Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                } else if (checkStage2 == true) {
                    mainclass.pc2.fxSample.game.changeStage(2);
                    checkStage2 = false;
                    stage2Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                } else if (checkStage3 == true) {
                    checkStage3 = false;
                    stage3Button.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
                }
            }
        });

        backImage = new Image(getClass().getResourceAsStream("back_greenyellow.png"));
        backImageView = new ImageView(backImage);
        backImageView.setFitWidth(backButtonImageWidth);
        backImageView.setFitHeight(backButtonImageHeight);
        backButton = new Button("", backImageView);
        backButton.setStyle("-fx-background-color: blue");
        backButton.setLayoutX(backButtonX);
        backButton.setLayoutY(backButtonY);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 戻るボタンのイベント. <br>
             * メニュー画面に戻る操作を行う。
             * replaceSceneメソッドを呼び出し、引数にpc2.pc2Sceneを渡す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc2.pc2Scene);
            }
        });

        root.getChildren().addAll(stage1Button, stage2Button, stage3Button,
                projectionButton, backButton);
    }

}
