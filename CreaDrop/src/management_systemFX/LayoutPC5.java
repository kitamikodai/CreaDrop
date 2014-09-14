package management_systemFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;

/**
 * 管理システム画面の5枚目を構成するクラス. <br>
 * Android端末の使用状況(creadropを開いているか否か)を把握するためのクラス。
 * Android端末のhomeボタンが押されていた場合、警告メッセージを表示する。
 */
public class LayoutPC5 {

    /**
     * LayoutPC5クラスのシーン
     */
    public Scene pc5Scene;
    /**
     * LayoutPC5クラスのペイン
     */
    private Pane root;

    /**
     * ラベル
     */
    private Label nameLabel, statusLabel;
    /**
     * ステータスラベル. <br>
     * Android端末の使用状況を表示するラベル。 Android端末でホームボタンが押されたか否かを判断し、
     * 押された場合は警告メッセージを表示する。
     *
     */
    private Label userStatusLabel;
    /**
     * ユーザネームラベル. <br>
     * Android端末上で入力された、ユーザ名を表示するラベル。
     *
     */
    public Label userNameLabel;
    /**
     * 戻るボタンのイメージ
     */
    private Image backImage;
    /**
     * 戻るボタンのイメージビュー
     */
    private ImageView backImageView;
    /**
     * 更新ボタン
     */
    private Button updataButton;
    /**
     * 戻るボタン
     */
    private Button backButton;
    /**
     * Andoroid端末でホームボタンが押されたときの情報を格納。 押された場合 == home　が格納される。
     */
    private String strHome;

    private MainClass mainclass;

    //コンポーネントのパラメータ//
    private final double scale = 0.8;
    private final double sceneWidth = 800.0 * scale;
    private final double sceneHeight = 600.0 * scale;
    private final double LabelFontSize1 = 48.0 * scale;
    private final double LabelFontSize2 = 36.0 * scale;
    private final double nameLabelX = 150.0 * scale, nameLabelY = 150.0 * scale;
    private final double statusLabelX = 420.0 * scale, statusLabelY = 150.0 * scale;
    private final double userNameLabelX = 170.0 * scale, userNameLabelY = 220.0 * scale;
    private final double userStatusLabelX = 440.0 * scale, userStatusLabelY = 220.0 * scale;
    private final double updataButonX = 15.0 * scale, updataButonY = 515.0 * scale;
    private final double ButtonFontSize2 = 36.0 * scale;
    private final double backButtonX = 25.0 * scale, backButtonY = 25.0 * scale;
    private final double backButtonImageWidth = 92.0 * scale, backButtonImageHeight = 60.0 * scale;

//コンポーネント(スクリーンショット)のパラメータ//    
    double label1X = 250.0 * scale, label1Y = 30.0 * scale;
    double label2X = 250.0 * scale, label2Y = 210.0 * scale;
    double label3X = 250.0 * scale, label3Y = 390.0 * scale;
    double label4X = 500.0 * scale, label4Y = 30.0 * scale;
    double label5X = 500.0 * scale, label5Y = 210.0 * scale;
    double label6X = 500.0 * scale, label6Y = 390.0 * scale;
//-----------------------------//

    /**
     * MainClassクラスの参照のセッター. <br>
     *
     * @param mc_main MainClassクラスの参照
     */
    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }

    /**
     * LayoutPC5クラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。 各ボタンに適した、ボタンイベントの登録を行う。
     */
    public LayoutPC5() {
        root = new Pane();
        root.setStyle("-fx-background-color: blue");
        pc5Scene = new Scene(root, sceneWidth, sceneHeight);
        strHome = "";

        nameLabel = new Label("ユーザ名");
        nameLabel.setLayoutX(nameLabelX);
        nameLabel.setLayoutY(nameLabelY);
        nameLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize1) + "; -fx-text-fill: black;");

        statusLabel = new Label("使用状況");
        statusLabel.setLayoutX(statusLabelX);
        statusLabel.setLayoutY(statusLabelY);
        statusLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize1) + "; -fx-text-fill: black;");

        userNameLabel = new Label();
        userNameLabel.setLayoutX(userNameLabelX);
        userNameLabel.setLayoutY(userNameLabelY);
        userNameLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize2) + "; -fx-text-fill: black; -fx-background-color: white");

        userStatusLabel = new Label("異常なし");
        userStatusLabel.setLayoutX(userStatusLabelX);
        userStatusLabel.setLayoutY(userStatusLabelY);
        userStatusLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize2) + "; -fx-text-fill: black; -fx-background-color: white");

        updataButton = new Button("更新");
        updataButton.setLayoutX(updataButonX);
        updataButton.setLayoutY(updataButonY);
        updataButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize2) + "; -fx-text-fill: black");
        updataButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 更新ボタンのイベント. <br>
             * Android端末の使用状況を更新する。 strHomeにホームボタンが押されたか否かの情報を受け取る。
             * strHome == home　であれば、警告メッセージを表示する。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                strHome = mainclass.pc2.userList.get(0).getString();
                System.out.println("ishome == " + strHome);
                if (strHome.equals("home")) {
                    userStatusLabel.setText("警告");
                    userStatusLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize2) + "; -fx-text-fill: red; -fx-background-color: yellow");
                    userNameLabel.setStyle("-fx-font-size: " + Double.toString(LabelFontSize2) + "; -fx-text-fill: black; -fx-background-color: yellow");
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
             * メニュー画面に戻る操作を行う。 replaceSceneメソッドを呼び出し、引数にpc2.pc2Sceneを渡す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc2.pc2Scene);
            }
        });

        root.getChildren().addAll(updataButton, backButton, userNameLabel, userStatusLabel, nameLabel, statusLabel);
    }

}
