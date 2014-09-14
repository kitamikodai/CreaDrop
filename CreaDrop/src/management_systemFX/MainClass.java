package management_systemFX;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * システム管理画面のメインクラス. <br>
 * Applicationクラスを継承<br>
 * launchメソッドによりstartメソッドが読み出される<br>
 * 各クラスのコンストラクタの起動、参照のセット、メインとなるシーン・ステージの作成、シーンの入れ替えを行う。<br>
 */
public class MainClass extends Application {

    /**
     * MainClassクラスのシーン
     */
    public Scene mainScene;
    /**
     * MainClassクラスのステージ
     */
    public Stage mainStage;
    /**
     * MainClassクラスの参照
     */
    public MainClass mainclass;
    /**
     * LayoutPC1クラスの参照
     */
    public LayoutPC1 pc1;
    /**
     * LayoutPC2クラスの参照
     */
    public LayoutPC2 pc2;
    /**
     * LayoutPC3クラスの参照
     */
    public LayoutPC3 pc3;
    /**
     * LayoutPC4クラスの参照
     */
    public LayoutPC4 pc4;
    /**
     * LayoutPC5クラスの参照
     */
    public LayoutPC5 pc5;
    /**
     * LayoutProjectorHelpクラスの参照
     */
    public LayoutProjectorHelp proHelp;

    /**
     * MainClassクラスのメインメソッド. <br>
     * launchメソッドを読み出し実行を行う。
     */
    public static void main(String[] args) {
        launch(MainClass.class, args);
    }

    @Override
    /**
     * スタートメソッド. <br>
     * 各クラスの参照の初期化を行い、各クラスの参照のセッターを呼び出す。<br>
     * mainSceneの初期化のため、LayoutPC1クラスのシーンを代入する。<br>
     *
     * @param st ステージ。mainStageの初期化（代入）を行う。
     */
    public void start(Stage st) {
        mainStage = st;
        mainStage.setTitle("PC画面");

        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (!pc1.pc1Scene.equals(mainScene)) {
                    try {
                        pc2.connectThreadStop();
                    } catch (IOException ex) {
                    }
                }
                if (pc2.fxSample.game != null) {
                    pc2.fxSample.game.exit();
                }
                Platform.exit();
            }
        });
        mainclass = new MainClass();

        pc1 = new LayoutPC1();
        pc2 = new LayoutPC2();
        pc3 = new LayoutPC3();
        pc4 = new LayoutPC4();
        pc5 = new LayoutPC5();
        proHelp = new LayoutProjectorHelp();

        pc1.setMainClass(this);
        pc2.setMainClass(this);
        pc3.setMainClass(this);
        pc4.setMainClass(this);
        pc5.setMainClass(this);

        mainScene = pc1.pc1Scene;
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    /**
     * シーンの入れ替えを行うメソッド. <br>
     * repSceneに受け取ったシーンをmainSceneへセットする。<br>
     * ステージにシーンを再セットする。<br>
     *
     * @param repScene 表示したいシーンを受け取る。
     */
    public void replaceScene(Scene repScene) {
        mainScene = repScene;
        mainStage.setScene(mainScene);
    }
}
