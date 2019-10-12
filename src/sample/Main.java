package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

//        Line line = new Line();
//        line.setStartX(100.0);
//        line.setStartY(150.0);
//        line.setEndX(500.0);
//        line.setEndY(150.0);

        Text text = new Text();
        text.setFont(new Font(45));
        text.setX(50);
        text.setY(150);
        text.setText("Welcome to Tutorialspoint");

        Group root = new Group();

        ObservableList list = root.getChildren();
        list.add(text);

        Scene scene = new Scene(root,600,300);
        scene.setFill(Color.BROWN);
        primaryStage.setTitle("Sample application");
        primaryStage.setScene(scene);
        primaryStage.show();
        //

    }


    public static void main(String[] args) {
        launch(args);
    }
}
