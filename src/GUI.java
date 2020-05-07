import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GUI extends Pane {
    Label[] labels = new Label[14];
    TextField[] text = new TextField[1];
    Button[] button = new Button[7];
    Image image1 = new Image("背景星空.jpg");
    ImageView background = new ImageView(image1);
    GUI() {
        for (int i = 0; i < labels.length; i++)
            labels[i]=new Label();
        for (int i = 0; i < text.length; i++)
            text[i]=new TextField();
        for (int i = 0; i < button.length; i++)
            button[i]=new Button();

        background.setFitHeight(500);
        background.setFitWidth(800);

        labels[0].setLayoutX(300.0);
        labels[0].setLayoutY(0.0);
        labels[0].setText("Project1");
        labels[0].setFont(Font.font(40));
        labels[0].setStyle("-fx-text-fill: #f8fff4");


//压缩部分
        button[1].setLayoutX(660);
        button[1].setLayoutY(130);
        button[1].setFont(Font.font(25));
        button[1].setText("压缩");

        labels[6].setLayoutX(660);
        labels[6].setLayoutY(250);
        labels[6].setText("压缩成功");
        labels[6].setFont(Font.font(20));
        labels[6].setStyle("-fx-text-fill: #ff1f1c");
        labels[6].setVisible(false);

        labels[3].setLayoutX(21.0);
        labels[3].setLayoutY(85.0);
        labels[3].setText("文件路径:");
        labels[3].setFont(Font.font(20));
        labels[3].setStyle("-fx-text-fill: #f8fff4");
        button[4].setLayoutX(115);
        button[4].setLayoutY(85);
        button[4].setText("选择文件夹");
        button[4].setFont(Font.font(15));
        button[0].setLayoutX(115);
        button[0].setLayoutY(125);
        button[0].setText(" 选择文件  ");
        button[0].setFont(Font.font(15));
        labels[8].setLayoutX(230.0);
        labels[8].setLayoutY(85);
        labels[8].setText("");
        labels[8].setFont(Font.font(15));
        labels[8].setStyle("-fx-text-fill: #f8fff4");

        labels[12].setLayoutX(21.0);
        labels[12].setLayoutY(180.0);
        labels[12].setText("存放路径:");
        labels[12].setFont(Font.font(20));
        labels[12].setStyle("-fx-text-fill: #f8fff4");
        button[6].setLayoutX(115);
        button[6].setLayoutY(180);
        button[6].setText("选择文件夹");
        button[6].setFont(Font.font(15));
        labels[11].setLayoutX(230);
        labels[11].setLayoutY(180);
        labels[11].setFont(Font.font(15));
        labels[11].setText("");
        labels[11].setStyle("-fx-text-fill: #f8fff4");

        labels[4].setLayoutX(21);
        labels[4].setLayoutY(250);
        labels[4].setText("新文件名:");
        labels[4].setFont(Font.font(20));
        labels[4].setStyle("-fx-text-fill: #f8fff4");


        text[0].setLayoutX(115);
        text[0].setLayoutY(250);

//解压部分
        button[3].setLayoutX(660);
        button[3].setLayoutY(380);
        button[3].setFont(Font.font(25));
        button[3].setText("解压");

        labels[7].setLayoutX(660);
        labels[7].setLayoutY(450.0);
        labels[7].setText("解压成功");
        labels[7].setFont(Font.font(20));
        labels[7].setStyle("-fx-text-fill: #ff1f1c");
        labels[7].setVisible(false );

        labels[5].setText("文件路径:");
        labels[5].setLayoutX(21);
        labels[5].setLayoutY(360);
        labels[5].setFont(Font.font(20));
        labels[5].setStyle("-fx-text-fill: #f8fff4");
        button[2].setLayoutX(115);
        button[2].setLayoutY(360);
        button[2].setText(" 选择文件  ");
        button[2].setFont(Font.font(15));
        labels[9].setLayoutX(230);
        labels[9].setLayoutY(360);
        labels[9].setFont(Font.font(15));
        labels[9].setText("");
        labels[9].setStyle("-fx-text-fill: #f8fff4");


        labels[13].setText("存放路径:");
        labels[13].setLayoutX(21.0);
        labels[13].setLayoutY(420);
        labels[13].setFont(Font.font(20));
        labels[13].setStyle("-fx-text-fill: #f8fff4");
        button[5].setLayoutX(115);
        button[5].setLayoutY(420);
        button[5].setText("选择文件夹");
        button[5].setFont(Font.font(15));
        labels[10].setLayoutX(230);
        labels[10].setLayoutY(420);
        labels[10].setFont(Font.font(15));
        labels[10].setText("");
        labels[10].setStyle("-fx-text-fill: #f8fff4");

        this.getChildren().add(background);
        for (int i = 0; i < labels.length; i++)
            this.getChildren().add(labels[i]);
        for (int i = 0; i < text.length; i++)
            this.getChildren().add(text[i]);
        for (int i = 0; i < button.length; i++)
            this.getChildren().add(button[i]);

    }
}