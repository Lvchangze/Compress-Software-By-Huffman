import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private boolean can = true;
    private File file1;
    private File file2;
    private File dir1;
    @Override
    public void start(Stage primaryStage) {
        GUI pane = new GUI();
        Scene scene0 = new Scene(pane, 800, 500);
        pane.button[0].setOnMouseClicked(event -> {//压缩里的选择文件
            if (can) {
                pane.labels[8].setText("");//提示所选文件的绝对路径
                pane.labels[6].setVisible(false);//提示压缩的文字
                pane.labels[7].setVisible(false);//提示解压的文字
                compressFile(primaryStage, pane);
            }
        });
        pane.button[4].setOnMouseClicked(event -> {//压缩里的选择文件夹
            if (can) {
                pane.labels[8].setText("");
                pane.labels[6].setVisible(false);
                pane.labels[7].setVisible(false);
                compressDir(primaryStage, pane);
            }
        });
        pane.button[5].setOnMouseClicked(event -> {//压缩里的选择存放路径
            if (can) {
                pane.labels[10].setText("");//提示存放位置的绝对路径
                pane.labels[6].setVisible(false);
                pane.labels[7].setVisible(false);
                decompressDir(primaryStage, pane);
            }
        });
        pane.button[1].setOnMouseClicked(event -> {//压缩按钮
            if (can) {
                String str = pane.labels[8].getText();//获得文件/文件夹的绝对路径
                String str2 = pane.text[0].getText();//获得新文件名
                String str3 = pane.labels[11].getText();//获得存放位置绝对路径

                if (!str.equals("") && !str2.equals("") && !str3.equals("")) {
                    can = false;
                    pane.labels[8].setText("");
                    pane.labels[11].setText("");
                    pane.text[0].setText("");
                    pane.labels[6].setText("正在压缩");
                    pane.labels[6].setVisible(true);
                    try {
                        System.out.println(pane.labels[8].getText());
                        compress.compress(new File(str), str3, str2);
                    } catch (Exception ignored) {
                    }
                    can = true;
                    pane.labels[6].setText("压缩成功");
                }

            }
        });



        pane.button[2].setOnMouseClicked(event -> {//解压里的选择文件
            if (can) {
                pane.labels[9].setText("");//所选文件的路径
                pane.labels[6].setVisible(false);
                pane.labels[7].setVisible(false);
                decompressFile(primaryStage, pane);
            }
        });
        pane.button[6].setOnMouseClicked(event -> {//解压的存放路径
            if (can) {
                pane.labels[11].setText("");
                pane.labels[6].setVisible(false);
                pane.labels[7].setVisible(false);
                directoryChooser = new DirectoryChooser();
                dir1 = directoryChooser.showDialog(primaryStage);
                pane.labels[11].setText(dir1.getAbsolutePath());
            }
        });
        pane.button[3].setOnMouseClicked(event -> {//解压按钮
            if (can) {
                String str = pane.labels[9].getText();//得到所选文件的路径
                String str2 = pane.labels[10].getText();//所选文件的父路径
                if (!str.equals("") && !str2.equals("")) {
                    can = false;
                    pane.labels[9].setText("");
                    pane.labels[10].setText("");
                    pane.labels[7].setText("正在解压");
                    pane.labels[7].setVisible(true);
                    try {
                        decompress.decompress(new File(str), str2);
                    } catch (Exception ignored) {
                    }
                    can = true;
                    pane.labels[7].setText("解压成功");
                }
            }
        });
        primaryStage.setTitle("Project 1");
        primaryStage.setScene(scene0);
        primaryStage.show();
    }

    public void compressFile(Stage stage, GUI sp){
        fileChooser=new FileChooser();
        file1=fileChooser.showOpenDialog(stage);//弹出选择文件的对话框
        sp.labels[8].setText(file1.getAbsolutePath());//获得所选需压缩的文件的绝对路径
    }

    public void compressDir(Stage stage, GUI sp){
        directoryChooser=new DirectoryChooser();
        file1=directoryChooser.showDialog(stage);//弹出选择文件夹的对话框
        sp.labels[8].setText( file1.getAbsolutePath());
    }

    public void decompressDir(Stage stage, GUI sp){
        directoryChooser=new DirectoryChooser();
        file2=directoryChooser.showDialog(stage);//弹出选择文件的对话框
        sp.labels[10].setText( file2.getAbsolutePath());//得到父路径
    }

    public void decompressFile(Stage stage, GUI sp){
        fileChooser=new FileChooser();
        file2=fileChooser.showOpenDialog(stage);//弹出选择文件夹的对话框
        sp.labels[9].setText( file2.getAbsolutePath());//获得解压文件的绝对路径
    }
}