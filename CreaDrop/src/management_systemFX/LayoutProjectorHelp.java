package management_systemFX;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * IPアドレス入力の補助説明画面を構成するクラス. <br>
 * Android端末にIPアドレスを入力する操作に関し、補助説明画面をプロジェクタに投影する。
 */
public class LayoutProjectorHelp {

    /**
     * LayoutProjectorHelpクラスのシーン
     */
    private Scene projectorHelpScene;
    /**
     * LayoutProjectorHelpクラスのペイン
     */
    private Pane root;
    /**
     * LayoutProjectorHelpクラスのステージ
     */
    public Stage projectorHelpStage;

    /**
     * イメージ
     */
    private Image forwardImage, returnImage, helpImage;
    /**
     * イメージビュー
     */
    private ImageView forwardImageView, returnImageView, helpImageView;
    /**
     * 『IPアドレス』という文字を保持するラベル
     */
    private Label label1;
    /**
     * IPラベル. <br>
     * このプログラムが動作している環境のIPアドレス(IPv4)を取得し、表示する。
     * 表示させたIPアドレスは、Android端末との通信環境を構築に使用する。
     */
    private Label IPLabel;
    /**
     * ヘルプラベル. <br>
     * 説明画像(helpImage)に適した説明文章を保持するラベル。 初期化時は、文字の折り返し(setWrapText())をtrueに、
     * 可視化(setVisible())をfalseにする。但し、添字0番目のみtrueにする。
     */
    private Label[] helpLabel;
    /**
     * 進むボタン. <br>
     * 『進む』という文字とfowardImageViewを表示する。
     */
    private Button forwardButton;
    /**
     * 戻るボタン. <br>
     * 『戻る』という文字とreturnImageViewを表示する。
     */
    private Button returnButton;
    /**
     * 入力回数. <br>
     * ボタンの入力回数を保持する。初期値==0。
     */
    private int inputCountNum;
    /**
     * 変化前のシーンサイズ(幅)
     */
    private double beforeSceneWidth;
    /**
     * 変化後のシーンサイズ(幅)
     */
    private double afterSceneWidth;
    /**
     * シーンサイズ(幅)の変化量
     */
    private double changeWidthRatio;
    /**
     * 変化前のシーンサイズ(高さ)
     */
    private double beforeSceneHeight;
    /**
     * 変化後のシーンサイズ(高さ)
     * changeWidthRatio = afterSceneWidth / beforeSceneWidth; で求める。
     */
    private double afterSceneHeight;
    /**
     * シーンサイズ(高さ)の変化量
     * changeHeightRatio = afterSceneHeight / beforeSceneHeight で求める。
     */
    private double changeHeightRatio;

//コンポーネントのパラメータ//
    private final double scale = 0.75;//倍率
    private double sceneWidth = 1280.0 * scale;
    private double sceneHeight = 960.0 * scale;
    private double label1FontSize = 80.0 * scale;
    private double IPLabelFontSize = 120.0 * scale;
    private double label1X = 15.0 * scale, label1Y = 0.0 * scale;
    private double IPLabelX = 15.0 * scale, IPLabelY = 70.0 * scale;
    private double helpLabelX = 35.0 * scale, helpLabelY = 750.0 * scale;
    private double helpLabelPrefWidth = 1200.0 * scale, helpLabelPrefHeight = 200.0 * scale;
    private double helpLabelFontSize = 50.0 * scale;
    private double helpImageViewX = 225.0 * scale, helpImageViewY = 200.0 * scale;
    private double fowardButtonX = 1120.0 * scale, fowardButtonY = 680.0 * scale;
    private double returnButtonX = 35.0 * scale, returnButtonY = 680.0 * scale;
    private double helpImageWidth = 800.0 * scale, helpImageHeight = 533 * scale;
    private double arrowImageWidth = 70.0 * scale, arrowImageHeight = 50.0 * scale;
    private double arrowFontSize = 12.0 * scale;
//-----------------------------//

    /**
     * LayoutProjectorHelpクラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。
     * 各ボタンに適した、ボタンイベントの登録を行う。
     * ワイズ・へイトプロパティイベントの登録をし、画面のリサイズ時の調整を行う。
     * IPアドレスを取得し、IPLabelに格納する。
     * helpLabel[]の初期化を行う。
     * projectorHelpStageにprojectorHelpSceneをセットする。
     */
    public LayoutProjectorHelp() {
        root = new Pane();
        root.setStyle("-fx-background-color: lime");
        projectorHelpScene = new Scene(root, sceneWidth, sceneHeight);
        projectorHelpStage = new Stage();
        projectorHelpStage.setTitle("説明画面");
        /*
         projectorHelpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
         @Override
         public void handle(WindowEvent t) {
         System.out.println("Help on CLOSE");
         Platform.exit();
         }
         });*/
        inputCountNum = 0;

        //シーンの変化に応じて、コンポーネントの配置を変更
        projectorHelpScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            /**
             * ワイズプロパティイベント. <br>
             * シーン(projectorHelpScene)の幅の値が変化すると、呼び出される。
             * repaintWidthPropertyメソッドを呼び出し、
             * シーンの変化に合わせて、各コンポーネントの調整を行う。
             */
            public void changed(ObservableValue<? extends Number> ov,
                    Number oldValue, Number newValue) {
                repaintWidthProperty();
            }
        });

        projectorHelpScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            /**
             * ハイトプロパティイベント. <br>
             * シーン(projectorHelpScene)の高さの値が変化すると、呼び出される。
             * repaintHeightPropertyメソッドを呼び出し、
             * シーンの変化に合わせて、各コンポーネントの調整を行う。
             */
            public void changed(ObservableValue<? extends Number> ov,
                    Number oldValue, Number newValue) {
                repainHeightProperty();
            }
        });

        label1 = new Label();
        label1.setText("IPアドレス");
        label1.setLayoutX(label1X);
        label1.setLayoutY(label1Y);
        label1.setStyle("-fx-font-size: " + Double.toString(label1FontSize) + "; -fx-text-fill: black");

        try {
            IPLabel = new Label(InetAddress.getLocalHost().getHostAddress());//IPアドレスの取得
        } catch (UnknownHostException e) {
        }
        IPLabel.setLayoutX(IPLabelX);
        IPLabel.setLayoutY(IPLabelY);
        IPLabel.setStyle("-fx-font-size: " + Double.toString(IPLabelFontSize) + "; -fx-text-fill: red");

        //ラベルの初期化
        helpLabel = new Label[5];
        for (int i = 0; i < 5; i++) {
            helpLabel[i] = new Label();
            helpLabel[i].setLayoutX(helpLabelX);
            helpLabel[i].setLayoutY(helpLabelY);
            helpLabel[i].setPrefSize(helpLabelPrefWidth, helpLabelPrefHeight);
            helpLabel[i].setStyle("-fx-font-size: " + Double.toString(helpLabelFontSize) + "; -fx-text-fill: black;"
                    + " -fx-background-color: yellowgreen");
            helpLabel[i].setWrapText(true);
            helpLabel[i].setVisible(false);
            root.getChildren().add(helpLabel[i]);
        }
        helpLabel[0].setVisible(true);
        helpLabel[0].setText("表示されいているIPアドレスを数字のみ入力します。");
        helpLabel[1].setText("入力が完了しましたらOKボタンを押します。");
        helpLabel[2].setText("次にユーザ名を自由に入力してください。");
        helpLabel[3].setText("入力が完了しましたらOKボタンを押します。");
        helpLabel[4].setText("正しく完了できるとこのような画面になります。\nこの画面にならない場合は、再入力をお願いします。\nステージ選択ボタンを押してお絵かきを楽しんでください。");

        helpImage = new Image(getClass().getResourceAsStream("0.png"));
        helpImageView = new ImageView(helpImage);
        helpImageView.setFitWidth(helpImageWidth);
        helpImageView.setFitHeight(helpImageHeight);
        helpImageView.setLayoutX(helpImageViewX);
        helpImageView.setLayoutY(helpImageViewY);

        forwardImage = new Image(getClass().getResourceAsStream("arrowRight_crop.png"));
        forwardImageView = new ImageView(forwardImage);
        forwardImageView.setFitWidth(arrowImageWidth);
        forwardImageView.setFitHeight(arrowImageHeight);
        forwardButton = new Button("進む", forwardImageView);
        forwardButton.setLayoutX(fowardButtonX);
        forwardButton.setLayoutY(fowardButtonY);
        forwardButton.setStyle("-fx-font-size:" + Double.toString(arrowFontSize) + "; -fx-text-fill: black");
        forwardButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 進むボタンのイベント. <br>
             * 説明画像(helpImage)と説明(helpLabel[])の入れ替えを行う。<br>
             * inputCountNumの値をみて、上限値以下であれば操作を行う。(未初期化配列へのアクセス防止)<br>
             *
             * ボタンの入力回数を+1する(inputCountNum++)。
             * 説明画像(helpImage)を次の画像(画像名：0.jpg 1.jpg
             * 2.jpg….etc)に替え、helpImageViewにセットし直す。
             * 表示しているラベル(例：bottomHelpLabel[0])を不可視状態(setVisible(false))にする。
             * 次の添字のラベル(例：bottomHelpLabel[1])を可視状態(setVisible(true))にすることで、表示している文章を進める。
             * repaintWidthPropertyメソッドとrepainHeightPropertyメソッドを呼び出し、
             * helpLabel[]のりサイズを行う。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                if (inputCountNum < 4) {//上限値：4                   
                    inputCountNum++;
                    helpImage = new Image(getClass().getResourceAsStream(String.valueOf(inputCountNum) + ".png"));
                    helpImageView.setImage(helpImage);

                    helpLabel[inputCountNum - 1].setVisible(false);//ボタンが押される前のラベルを非表示
                    helpLabel[inputCountNum].setVisible(true);//押された後のラベルを表示
                    repaintWidthProperty();
                    repainHeightProperty();

                }
            }
        });

        returnImage = new Image(getClass().getResourceAsStream("arrowLeft_crop.png"));
        returnImageView = new ImageView(returnImage);
        returnImageView.setFitWidth(arrowImageWidth);
        returnImageView.setFitHeight(arrowImageHeight);
        returnButton = new Button("戻る", returnImageView);
        returnButton.setLayoutX(returnButtonX);
        returnButton.setLayoutY(returnButtonY);
        returnButton.setStyle("-fx-font-size: " + Double.toString(arrowFontSize) + "; -fx-text-fill: black");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 戻るボタンのイベント. <br>
             * 説明画像(helpImage)と説明(helpLabel[])の入れ替えを行う。<br>
             * inputCountNumの値をみて、下限値以上であれば操作を行う。(未初期化配列へのアクセス防止)<br>
             *
             * ボタンの入力回数を-1する(inputCountNum--)。
             * 説明画像(helpImage)を前の画像(画像名：0.jpg 1.jpg
             * 2.jpg….etc)に替え、helpImageViewにセットし直す。
             * 表示しているラベル(例：bottomHelpLabel[1])を不可視状態(setVisible(false))にする。
             * 前の添字のラベル(例：bottomHelpLabel[0])を可視状態(setVisible(true))にすることで、表示している文章を戻す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                if (inputCountNum > 0) {//下限値：0
                    inputCountNum--;
                    helpImage = new Image(getClass().getResourceAsStream(String.valueOf(inputCountNum) + ".png"));
                    helpImageView.setImage(helpImage);

                    helpLabel[inputCountNum + 1].setVisible(false);//ボタンが押される前のラベルを非表示
                    helpLabel[inputCountNum].setVisible(true);//押された後のラベルを表示
                    repaintWidthProperty();
                    repainHeightProperty();

                }
            }
        });

        root.getChildren().addAll(label1, IPLabel, forwardButton, returnButton, helpImageView);
        projectorHelpStage.setScene(projectorHelpScene);
    }

    /**
     * 幅のリペイント. <br>
     * 画面(シーン)の変化に合わせて、各コンポーネントのX座標及びサイズ(幅)の調整を行う。
     * 元のシーンのサイズ(幅)と変化後のシーンのサイズ(幅)から、シーンサイズの変化量(幅)を求める。
     * 求めた変化量(幅)を、各コンポーネントのX座標及びサイズ(幅)に乗算した値で、再セットする。
     */
    public void repaintWidthProperty() {
        beforeSceneWidth = sceneWidth;
        afterSceneWidth = projectorHelpScene.getWidth();
        sceneWidth = afterSceneWidth;
        changeWidthRatio = afterSceneWidth / beforeSceneWidth;

        //---helpLabel[]---------
        helpLabelPrefWidth *= changeWidthRatio;
        helpLabelX *= changeWidthRatio;
        helpLabel[inputCountNum].setLayoutX(helpLabelX);
        helpLabel[inputCountNum].setPrefSize(helpLabelPrefWidth, helpLabelPrefHeight);

        //---label1--------------
        label1X *= changeWidthRatio;
        label1.setLayoutX(label1X);

        //---IPLabel--------------
        IPLabelX *= changeWidthRatio;
        IPLabel.setLayoutX(IPLabelX);

        //---helpImageView--------------
        helpImageViewX *= changeWidthRatio;
        helpImageWidth *= changeWidthRatio;
        helpImageView.setFitWidth(helpImageWidth);
        helpImageView.setLayoutX(helpImageViewX);

        //---forwardImageView---------------
        arrowImageWidth *= changeWidthRatio;
        forwardImageView.setFitWidth(arrowImageWidth);

        //---forwardButton--------------------
        fowardButtonX *= changeWidthRatio;
        forwardButton.setLayoutX(fowardButtonX);

        //---returnImageView---------------
        returnImageView.setFitWidth(arrowImageWidth);

        //---returnButton------------------
        returnButtonX *= changeWidthRatio;
        returnButton.setLayoutX(returnButtonX);
    }

    /**
     * 高さのリペイント. <br>
     * 画面(シーン)の変化に合わせて、各コンポーネントのY座標及びサイズ(高さ)・フォントの調整を行う。
     * 元のシーンのサイズ(高さ)と変化後のシーンのサイズ(高さ)から、シーンサイズの変化量(高さ)を求める。
     * 求めた変化量(高さ)を、各コンポーネントのY座標及びサイズ(高さ)・フォントサイズに乗算した値で、再セットする。
     */
    public void repainHeightProperty() {
        beforeSceneHeight = 0.0;
        afterSceneHeight = 0.0;
        changeHeightRatio = 0.0;

        beforeSceneHeight = sceneHeight;
        afterSceneHeight = projectorHelpScene.getHeight();
        sceneHeight = afterSceneHeight;
        changeHeightRatio = afterSceneHeight / beforeSceneHeight;

        //---helpLabel[]---------
        helpLabelPrefHeight *= changeHeightRatio;
        helpLabelY *= changeHeightRatio;
        helpLabelFontSize *= changeHeightRatio;
        helpLabel[inputCountNum].setLayoutY(helpLabelY);
        helpLabel[inputCountNum].setPrefSize(helpLabelPrefWidth, helpLabelPrefHeight);
        helpLabel[inputCountNum].setStyle("-fx-font-size: " + Double.toString(helpLabelFontSize) + "; -fx-text-fill: black;"
                + " -fx-background-color: yellowgreen");

        //---label1--------------
        label1Y *= changeHeightRatio;
        label1FontSize *= changeHeightRatio;
        label1.setLayoutY(label1Y);
        label1.setStyle("-fx-font-size: " + Double.toString(label1FontSize) + "; -fx-text-fill: black");

        //---IPLabel--------------
        IPLabelY *= changeHeightRatio;
        IPLabelFontSize *= changeHeightRatio;
        IPLabel.setLayoutY(IPLabelY);
        IPLabel.setStyle("-fx-font-size: " + Double.toString(IPLabelFontSize) + "; -fx-text-fill: red");

        //---helpImageView--------------
        helpImageViewY *= changeHeightRatio;
        helpImageHeight *= changeHeightRatio;
        helpImageView.setFitHeight(helpImageHeight);
        helpImageView.setLayoutY(helpImageViewY);

        //---forwardImageView---------------
        arrowImageHeight *= changeHeightRatio;
        forwardImageView.setFitHeight(arrowImageHeight);

        //---forwardButton--------------------
        fowardButtonY *= changeHeightRatio;
        arrowFontSize *= changeHeightRatio;
        forwardButton.setLayoutY(fowardButtonY);
        forwardButton.setStyle("-fx-font-size:" + Double.toString(arrowFontSize) + "; -fx-text-fill: black");

        //---returnImageView---------------
        returnImageView.setFitHeight(arrowImageHeight);

        //---returnButton------------------
        returnButtonY *= changeHeightRatio;
        returnButton.setLayoutY(returnButtonY);
        returnButton.setStyle("-fx-font-size:" + Double.toString(arrowFontSize) + "; -fx-text-fill: black");
    }

}
