package management_systemFX;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * 管理システム画面の1枚目を構成するクラス. <br>
 * プロジェクタのIPアドレスを入力し、プロジェクタとの接続を行う画面。<br>
 * 正しいIPアドレスが入力されると、次の画面への遷移と、IPアドレス入力の説明画面を表示する。<br>
 */
public class LayoutPC1 {

    /**
     * LayoutPC1クラスのシーン
     */
    public Scene pc1Scene;
    /**
     * LayoutPC1クラスのペイン
     */
    private Pane root;
    /**
     * 『プロジェクタ IPアドレス 入力』という文字を保持するラベル
     */
    private Label label1;
    /**
     * プロジェクタのIPアドレスを入力するテキストフィールド
     */
    private TextField inputIPTextField;
    /**
     * OKボタン
     */
    private Button okButton;

    private MainClass mainclass;

    //コンポーネントのパラメータ//
    private final double scale = 1.0;//倍率
    private final double sceneWidth = 800.0 * scale;
    private final double sceneHeight = 600.0 * scale;
    private final double label1X = 130.0 * scale, label1Y = 150.0 * scale;
    private final double label1FontSize = 48.0 * scale;
    private final double inputIPTextFieldFontSize = 48.0 * scale;
    private final double inputIPTextFieldX = 130.0 * scale, inputIPTextFieldY = 250.0 * scale;
    private final double okButtonX = 680.0 * scale, okButtonY = 515.0 * scale;
    private final double okButtonFontSize = 36.0 * scale;
    //-------------------------------/

    /**
     * MainClassクラスの参照のセッター. <br>
     *
     * @param mc_main MainClassクラスの参照
     */
    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }

    /**
     * LayoutPC1クラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。<br>
     * テキストフィールドに入力されたIPアドレスが正しいか判断し、
     * 正しければ次の画面への遷移と、IPアドレス入力の説明画面を表示する。<br>
     * 正しくなければ、再入力を促す。
     */
    public LayoutPC1() {
        root = new Pane();
        root.setStyle("-fx-background-color: blue");
        pc1Scene = new Scene(root, sceneWidth, sceneHeight); //ウィンドウサイズ

        label1 = new Label("プロジェクタ IPアドレス 入力");
        label1.setLayoutX(label1X);
        label1.setLayoutY(label1Y);
        label1.setStyle("-fx-font-size: " + Double.toString(label1FontSize) + "; -fx-text-fill: black");

        inputIPTextField = new TextField();
        inputIPTextField.setLayoutX(inputIPTextFieldX);
        inputIPTextField.setLayoutY(inputIPTextFieldY);
        inputIPTextField.setStyle("-fx-font-size: " + Double.toString(inputIPTextFieldFontSize) + "; -fx-text-fill: black");

        okButton = new Button("OK");
        okButton.setLayoutX(okButtonX);
        okButton.setLayoutY(okButtonY);
        okButton.setStyle("-fx-font-size: " + Double.toString(okButtonFontSize) + "; -fx-text-fill: black");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * OKボタンのイベント. <br>
             * テキストフィールドに入力された値が、
             * 正しければ次のシーン(pc3Scene)をセット(シーンの入れ替えを行う)し、
             * 説明画面のステージを可視化して、画面を表示する。
             * 正しくなければ、再入力を促す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc2.pc2Scene);
                mainclass.proHelp.projectorHelpStage.show();
                try {
                    mainclass.pc2.setServer();
                } catch (IOException ex) {
                }
                mainclass.pc2.connect();

            }
        });

        root.getChildren().addAll(label1, inputIPTextField, okButton);
    }

}
