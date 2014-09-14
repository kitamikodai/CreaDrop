package management_systemFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 管理システム画面の4枚目を構成するクラス. <br>
 * 接続しているタブレットのユーザ名をリストとして表示し、管理を行う画面。
 */
public class LayoutPC4 {

    /**
     * LayoutPC4クラスのシーン
     */
    public Scene pc4Scene;
    /**
     * LayoutPC4クラスのペイン
     */
    private Pane root;
    /**
     * 戻るボタンのイメージ
     */
    private Image backImage;
    /**
     * 戻るボタンのイメージビュー
     */
    private ImageView backImageView;
    /**
     * 『ユーザ名』という文字を保持するラベル
     */
    private Label label1;
    /**
     * 削除ボタン
     */
    private Button deleteButton;
    /**
     * 戻るボタン
     */
    private Button backButton;
    /**
     * オブザーバリスト. <br>
     * オブザーバリストにリストデータ(listData)をセットする。 リストにオブザーバリストを引数で渡し、リストを生成。
     * オブザーバリストの内容が変更されると自動的にリストの内容も更新する。
     */
    public ObservableList observableList;
    /**
     * リスト. <br>
     * ユーザ名を表示する。 ユーザ名を選択すると、青色で示され選択状態を表す。
     */
    public ListView listView;
    /**
     * リストデータ. <br>
     * リストに表示するユーザ名を保持する。 『"Tab1", "Tab2", "Tab3", "Tab4"』で初期化。
     */
    public String[] listData = {"Tab1", "Tab2", "Tab3", "Tab4"};

    private MainClass mainclass;

    //コンポーネントのパラメータ//
    private final double scale = 1.0;
    private final double sceneWidth = 800.0 * scale;
    private final double sceneHeight = 600.0 * scale;
    private final double label1X = 250.0 * scale, label1Y = 70.0 * scale;
    private final double ButtonFontSize = 36.0 * scale;
    private final double listViewPrefWidth = 400.0 * scale, listViewPrefHeight = 400.0 * scale;
    private final double listViewX = 250.0 * scale, listViewY = 120.0 * scale;
    private final double listViewFontSize = 36.0 * scale;
    private final double deleteButonX = 15.0 * scale, deleteButonY = 515.0 * scale;
    private final double backButtonX = 25.0 * scale, backButtonY = 25.0 * scale;
    private final double backButtonImageWidth = 92.0 * scale, backButtonImageHeight = 60.0 * scale;
    //-------------------------------------/

    /**
     * MainClassクラスの参照のセッター. <br>
     *
     * @param mc_main MainClassクラスの参照
     */
    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }

    /**
     * LayoutPC4クラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。 各ボタンに適した、ボタンイベントの登録を行う。
     */
    public LayoutPC4() {
        root = new Pane();
        root.setStyle("-fx-background-color: blue");
        pc4Scene = new Scene(root, sceneWidth, sceneHeight);

        label1 = new Label();
        label1.setText("ユーザ名");
        label1.setLayoutX(label1X);
        label1.setLayoutY(label1Y);
        label1.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");

        observableList = FXCollections.observableArrayList(listData);
        listView = new ListView(observableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setStyle("-fx-font-size: " + Double.toString(listViewFontSize) + "; -fx-text-fill: black; -fx-background-cell-color: red");
        listView.setPrefSize(listViewPrefWidth, listViewPrefHeight);
        listView.setLayoutX(listViewX);
        listView.setLayoutY(listViewY);

        deleteButton = new Button("削除");
        deleteButton.setLayoutX(deleteButonX);
        deleteButton.setLayoutY(deleteButonY);
        deleteButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 削除ボタンのイベント. <br>
             * オブザーバリスト内のデータを削除(removeメソッドを読み出す)する。
             * 自動的にリスト内のデータも更新される。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                try {
                    observableList.remove(listView.getSelectionModel().getSelectedIndex());
                } catch (ArrayIndexOutOfBoundsException arrayException) {
                    System.out.println("Delete miss!!");
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

        root.getChildren().addAll(label1, listView, deleteButton, backButton);
    }

}
