package management_systemFX;

import communication.TcpIp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx_sample.JavaFX_sample;

/**
 * 管理システム画面の2枚目を構成するクラス. <br>
 * 管理システム画面のメニューとなる画面。 各ボタンが押されると、そのボタンに適したシーンをセットする。
 */
public class LayoutPC2 {

    /**
     * LayoutPC2クラスのシーン
     */
    public Scene pc2Scene;
    /**
     * LayoutPC2クラスのペイン
     */
    private Pane root;
    /**
     * ステージボタン
     */
    private Button stageButton;
    /**
     * リストボタン
     */
    private Button listButton;
    /**
     * 管理ボタン
     */
    private Button managementButton;

    /**
     * バッファードイメージ
     * Android端末上で描かれた絵を格納するイメージ。 格納されたイメージはライタブルイメージに変換する。
     */
    private BufferedImage buffImage;
    /**
     * ライタブルイメージ
     */
    public WritableImage writeImage;
    /**
     * 描画像取得スレッド. <br>
     * Android端末で描かれた絵を取得するスレッド。
     * 端末から絵が送られてくるまで、動作する。
     * creatDrawnGetThredメソッドで初期化を行う。
     */
    private Thread drawnGetThred;

    private Thread thread;

    /**
     * TcpIpクラスのオブジェクトを格納するList
     */
    public ArrayList<TcpIp> userList;
    private ServerSocket serversocket;
    /**
     * Receiveメソッドの呼び出しの可能状態を保持する
     * 可能 == true, 不可能 == false
     */
    private boolean isAvailableReceive;
    /**
     * ユーザ名
     */
    private String userName;
    /**
     * ポート番号
     */
    public final int port = 50001;

    private MainClass mainclass;
    public JavaFX_sample fxSample;
    private TcpIp user;

    //コンポーネントのパラメータ//
    private final double scale = 1.0;//倍率
    private final double sceneWidth = 800.0 * scale;
    private final double sceneHeight = 600.0 * scale;
    private final double stageButtonX = 225.0 * scale, stageButtonY = 25.0 * scale;
    private final double ButtonFontSize = 48.0 * scale;
    private final double ButtonPrefWidth = 350.0 * scale, ButtonPrefHeight = 150.0 * scale;
    private final double listButtonX = 225.0 * scale, listButtonY = 225.0 * scale;
    private final double managementButtonX = 225.0 * scale, managementButtonY = 425.0 * scale;
    //--------------------------/

    /**
     * MainClassクラスの参照のセッター. <br>
     *
     * @param mc_main MainClassクラスの参照
     */
    public void setMainClass(MainClass mc_main) {
        mainclass = mc_main;
    }

    /**
     * LayoutPC2クラスのコンストラクタ. <br>
     * 画面を構成するコンポーネントの作成・配置を行う。 各ボタンに適した、ボタンイベントの登録を行う。
     *
     */
    public LayoutPC2() {
        root = new Pane();
        root.setStyle("-fx-background-color: blue");
        pc2Scene = new Scene(root, sceneWidth, sceneHeight); //ウィンドウサイズ
        userList = new ArrayList<TcpIp>();
        userName = "";
        isAvailableReceive = true;
        fxSample = new JavaFX_sample();

        stageButton = new Button("ステージ選択");
        stageButton.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        stageButton.setLayoutX(stageButtonX);
        stageButton.setLayoutY(stageButtonY);
        stageButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        stageButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * ステージボタンのイベント. <br>
             * 押されると、ステージ選択画面のシーン(pc3Scene)を読み込み画面を遷移する。
             * isAvailableReceiveがtureであれば、受信メソッドを呼び出す。 <br>
             * creatDrawnGetFXThredメソッドを呼び出し、Android端末で描かれた絵の取得をスレッドで行う。
             * setMainClassメソッドを呼び出し、JavaFX_sampleクラスにMainClassクラスの参照をセットする。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc3.pc3Scene);
                if (isAvailableReceive) {
                    Receive();
                }
                creatDrawnGetThred();
                fxSample.setMainClass(mainclass);
            }
        });

        listButton = new Button("   　リスト　  ");
        listButton.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        listButton.setLayoutX(listButtonX);
        listButton.setLayoutY(listButtonY);
        listButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        listButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * リストボタンのイベント. <br>
             * 押されると、リスト画面のシーン(pc4Scene)を読み込み画面を遷移する。
             * isAvailableReceiveがtureであれば、受信メソッドを呼び出す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc4.pc4Scene);
                if (isAvailableReceive) {
                    Receive();
                }
            }
        });

        managementButton = new Button(" 　管理  　");
        managementButton.setPrefSize(ButtonPrefWidth, ButtonPrefHeight);
        managementButton.setLayoutX(managementButtonX);
        managementButton.setLayoutY(managementButtonY);
        managementButton.setStyle("-fx-font-size: " + Double.toString(ButtonFontSize) + "; -fx-text-fill: black");
        managementButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * 管理ボタンのイベント. <br>
             * 押されると、管理画面のシーン(pc5Scene)を読み込み画面を遷移する。
             * isAvailableReceiveがtureであれば、受信メソッドを呼び出す。
             *
             * @param actionEvent ボタンイベント
             */
            public void handle(ActionEvent actionEvent) {
                mainclass.replaceScene(mainclass.pc5.pc5Scene);
                if (isAvailableReceive) {
                    Receive();
                }
            }
        });

        root.getChildren().addAll(stageButton, listButton, managementButton);
    }

    /**
     * 受信メソッド. <br>
     * Android端末との通信で得た情報を取得を行う。但し、このメソッドの呼び出しは一度のみ。
     * 補助説明画面(LayoutProjectorHelpクラスのステージ)を隠す(hide())。
     * userNameに端末上で入力されたユーザ名を格納する。
     * userNameをLayoutPC5クラスのobservableListに登録する。(自動的にListViewも更新される)
     * userNameをLayoutPC6クラスのuserNameLabelにセットする。
     * 以降、呼び出されないようにするために、isAvailableReceive = false　とする。
     *
     * @param actionEvent ボタンイベント
     */
    public void Receive() {
        mainclass.proHelp.projectorHelpStage.hide();
        //mainclass.proHelp.projectorHelpStage.close();
        //mainclass.proHelp = null;
        userName = userList.get(0).getStringName();
        mainclass.pc4.observableList.set(0, userName);
        mainclass.pc5.userNameLabel.setText(userName);
        System.out.println("userName == " + userName);
        isAvailableReceive = false;
    }

    /**
     * 描画像取得スレッド作成メソッド. <br>
     * drawnGetThredスレッドの作成を行う。
     * Android端末で描いた絵を取得するまで、スレッドは起動する。<br>
     * 画面を構成するオブジェクトの生成は、drawnGetThredスレッド内では実行できないので、
     * Platformクラスに定義されているrunLaterを使用して、addCreateObjectメソッドを呼び出す。<br>
     *
     * whileループのフラグ(whileFlag)をtrueに初期化。<br>
     * runメソッドをオーバライド。
     * whileループを作成。userListの中からBmpデータを取得出来た場合にループを抜ける。
     * userList.get(0).getBmp()がnullでないならば、
     * userListの中からBmpデータを取得し、buffImageに格納。
     * whileFlag = false　にし、BufferedImageをWritableImageに変換する。
     * userList.get(0)のBmpデータをnullにするために、setBmpNullメソッドを呼び出す。
     *
     * runLaterメソッドを実装する。
     * 取得したBmpデータ(writeImage)とそのデータのモデルタイプ("ship","car","ufo"　のいずれか)を
     * 引数に渡して、addCreateObjectメソッドを呼び出す。
     *
     *
     * 最後に、drawnGetThredスレッドをスタートさせる。
     */
    public void creatDrawnGetThred() {
        drawnGetThred = new Thread() {
            private boolean whileFlag = true;

            @Override
            public void run() {
                System.out.println("creatDrawnGetThread start");

                while (whileFlag) {
                    try {
                        if (userList.get(0).getBmp() != null) {
                            try {
                                buffImage = userList.get(0).getBmp();
                                whileFlag = false;
                                writeImage = SwingFXUtils.toFXImage(buffImage, null);
                                userList.get(0).setBmpNull();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        fxSample.game.stage.addCreateObject(user.getModelType(), writeImage);
                                    }
                                });
                            } catch (IOException ex) {
                            }
                        }
                    } catch (IOException ex) {
                    }

                }
                System.out.println("creatDrawnGetThread stop");
            }
        };
        drawnGetThred.start();

    }

    /**
     * サーバー起動
     * クライアントの接続要求を待ち、接続が来たらTcpIpクラスのオブジェクトを一つ生成し、ArrayListni追加する。
     */
    public void connect() {

        thread = new Thread() {

            public void run() {

                System.out.println("connectThread start");
                while (!serversocket.isClosed()) {
                    //新しいクライアントの接続を待つ
                    Socket client = null;
                    try {
                        client = serversocket.accept();
                        System.out.println("seikou!");
                    } catch (IOException ex) {
                    }

                    //ユーザーオブジェクトを生成する
                    if (!(client == null)) {
                        user = new TcpIp(client);
                        addUser(user);
                    }
                }

                System.out.println("connectThread stop");
            }
        };

        thread.start();
    }

    public void connectThreadStop() throws IOException {
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).close();
        }
        userList.clear();
        serversocket.close();

    }

    /**
     * ServerSocketを作成する
     *
     * @throws IOException
     */
    public void setServer() throws IOException {
        serversocket = new ServerSocket(port);
    }

    /**
     * TcpIpクラスのオブジェクトをArrayListに追加する
     *
     * @param user TcpIpクラスのオブジェクト
     */
    public void addUser(TcpIp user) {
        user.start();
        userList.add(user);
    }

}
