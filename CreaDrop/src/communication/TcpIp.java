/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Socketクライアントクラス
 * 文字列の送信、受信、画像の受信のメソッドが存在する
 *
 */
public class TcpIp {

    /**
     *
     */
    private Socket client = null;
    //受信したStringを入れる

    //画像を受信するための変数
    // private BufferedImage img;
    //受信した画像を格納する変数
    private BufferedImage bmp = null;
    private BufferedReader reader = null;
    //受信した文字列を返す時の変数
    private String string;
    String name;
    String modeltype;
    // private InputStream in;
    //スレッドのためのフラグ
    private boolean flag = true;

    /**
     * コンストラクタ
     *
     * @param aClientSocket　接続したSocket
     */
    public TcpIp(Socket aClientSocket) {
        this.client = aClientSocket;
    }

    /**
     * 文字列、画像の受信を行います
     * １つのスレッドを生成している
     */
    public void start() {
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Thread th3 = new Thread() {
            @Override
            public void run() {
                String str1 = " ";
                BufferedImage img;
                InputStream in = null;
                int i = 0;
                while (flag) {
                    if (!client.isClosed()) {
                        try {
                            if (!((str1 = reader.readLine()) == null)) {
                                if (i == 0) {
                                    name = str1;
                                    i++;
                                }
                                if (str1.equals("ship") || str1.equals("car") || str1.equals("ufo")) {
                                    try {
                                        modeltype = str1;
                                        in = client.getInputStream();
                                        img = ImageIO.read(in);
                                        if (!(img == null)) {
                                            bmp = img;
                                            System.out.println("setImg");

                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(TcpIp.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //残ってる画像を吐き出させる
                                    str1 = reader.readLine();
                                } else if (str1.equals("ok")) {
                                    string = "ok";
                                    System.out.println("okモード");
                                } else {
                                    string = str1;
                                    System.out.println(str1);
                                }
                            }
                        } catch (IOException ex) {
                            if (str1 == null) {

                            }
                        }
                    }
                }
            }
        };
        System.out.println("th3 on START");
        th3.start();
    }

    /**
     * 文字列の送信を行います
     *
     * @param str 送信する文字列
     * @throws IOException
     */
    public void sendString(String str) throws IOException {
        String str2 = str;
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
        out.write(str2);
        out.newLine();  // 改行　・・・（1）
        out.flush();   // 溜まっているデータを一気に押し出す（送信）
    }

    /**
     * 受信した文字列を返します
     *
     * @return　String 受信した文字列
     */
    public String getString() {
        System.out.println(string);
        return string;
    }

    /**
     * 受信した画像を返します
     *
     * @return BufferdImage 受信した画像
     * @throws IOException
     */
    public BufferedImage getBmp() throws IOException {
        return bmp;
    }

    /**
     * Socketを閉じます
     *
     * @throws IOException
     */
    public void close() throws IOException {
        sendString("close");
        flag = false;
        client.close();
        System.out.println("切断");
    }

    public String getStringName() {
        return name;
    }

    public String getModelType() {
        return modeltype;
    }

    public void setBmpNull() throws IOException {
        bmp = null;
    }

}
