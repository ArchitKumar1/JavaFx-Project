package sample;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Main extends Application {

    Stage window;
    BorderPane layout;
    Text text = new Text();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("VIT Grade Calculator Portal");
        text.setText("Welcome to the VIT Grade Calculator portal");
        text.setX(400);
        text.setY(250);
        Group root = new Group(text);

        //File menu
        Menu fileMenu = new Menu("Start Menu");
        MenuItem newFile = new MenuItem("New...");
        newFile.setOnAction(e -> {

            GridPane pane = new GridPane();
            ScrollPane scroll = new ScrollPane(pane);
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            pane.setStyle("-fx-background-color: lightblue;"); // Background color
            pane.setHgap(10); // Horizontal gap
            pane.setVgap(10); // Vertical gap
            pane.setPadding(new Insets(10, 10, 10, 10));

            // Create and add title
            Label nameLabel = new Label("Assignment");
            nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD,
                     15));
            pane.add(nameLabel, 0, 0);

            Label gradeLabel = new Label("My Score");
            gradeLabel.setFont(Font.font("Verdana", FontWeight.BOLD,
                     15));
            pane.add(gradeLabel, 1, 0);

            Label scoreLabel = new Label("Full Score");
            scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD,
                    15));
            pane.add(scoreLabel, 2, 0);


            Label weightLabel = new Label("% of Grade");
            weightLabel.setFont(Font.font("Verdana", FontWeight.BOLD,
                   15));
            pane.add(weightLabel, 3, 0);

            int num_assignments = 7;
            // Create and add grade fields
            for (int i = 1; i <num_assignments; i++) {
                if(i == 1) pane.add( new Label (i + " CAT1 (50): "), 0, i);
                if(i == 2) pane.add( new Label (i + " CAT2 (50): "), 0, i);
                if(i == 3) pane.add( new Label (i + " DA1 (10): "), 0, i);
                if(i == 4) pane.add( new Label (i + " QUIZ1 (10): "), 0, i);
                if(i == 5) pane.add( new Label (i + " QUIZ1 (10) : "), 0, i);
                if(i == 6) pane.add( new Label (i + " FAT (100): "), 0, i);
                //pane.add( new Label (""+i), 0, i);
                pane.add( new TextField(), 1, i);
                pane.add( new TextField(), 2, i);
                pane.add( new TextField(), 3, i);
            }

            Button addAssignment = new Button("+");
            Button calculate = new Button("Calculate");
            //Button needButton = new Button(":");
            TextField Gradeacq = new TextField();
            Text Konsagrade = new Text();
            Konsagrade.setText("               Grade Acquired");
            TextField result = new TextField();

            pane.add(addAssignment, 0, num_assignments);
            pane.add(calculate, 1, num_assignments);


            pane.add(result, 3, num_assignments);
            pane.add(Gradeacq, 4, num_assignments);
            pane.add(Konsagrade,4,num_assignments-1);
            // Create and register add assignment button handler
            addAssignment.setOnAction(ee -> {

                // Move buttons
                int row = pane.getRowIndex(addAssignment) + 1;

                pane.getChildren().remove(addAssignment);
                pane.getChildren().remove(calculate);
                //pane.getChildren().remove(needBox);
                pane.getChildren().remove(result);

                pane.add(addAssignment, 0, row);
                pane.add(calculate, 1, row);
                //pane.add(needBox, 2, row);
                pane.add(result, 3, row);

                // Add new row of text fields
                row--;
                pane.add( new Label (""+row), 0, row);
                pane.add( new TextField(), 1, row);
                pane.add( new TextField(), 2, row);
                pane.add( new TextField(), 3, row);

            });


            calculate.setOnAction(ee -> {
                ArrayList<Double> scores = new ArrayList<Double>();
                ArrayList<Double> percents = new ArrayList<Double>();
                boolean bool = false;

                // Reaccess grade fields
                for (Node node: pane.getChildren()) {
                    if (node instanceof TextField) {
                        try {
                            Double doub = Double.parseDouble(((TextField)node).getText());
                            if (pane.getColumnIndex(node) == 1) {
                                scores.add(doub);
                                bool = true;
                            }
                            else if (bool && pane.getColumnIndex(node) == 2) {
                                Double newScore = scores.get(scores.size()-1)/doub;
                                scores.set(scores.size()-1, newScore);
                                bool = false;
                            }
                            else {
                                percents.add(doub);
                                bool = false;
                            }
                        } catch (NumberFormatException ex) {
                            //skip
                        }
                    }
                }

                // Calculate grade
                double grade = 0;
                double percentTotal = 0;

                int min = scores.size();
                if (min > percents.size())
                    min = percents.size();

                for (int i = 0; i < min; i++) {
                    grade = scores.get(i) * percents.get(i) + grade;
                    percentTotal = percentTotal + percents.get(i);
                }
                result.clear();
                grade = 100*(grade/percentTotal);
                result.appendText(grade+"%");


                String gg = "2";
                grade = (int)grade;
                if(grade >90) {
                    gg = "S";
                }else if(grade >80) {
                    gg = "A";
                }else if(grade >70) {
                    gg = "B";
                }else if(grade >60) {
                    gg = "C";
                }else if(grade >50) {
                    gg = "D";
                }else {
                    gg = "F";
                }
                Gradeacq.clear();
                Gradeacq.appendText("GRADE : " + gg);
            });

            Button Exit = new Button("Exit the program ");
            Exit.setOnAction(t -> {

                System.exit(0);
            });
            pane.add(Exit, 4, 1);
            // Create a scene and place it in the stage
            Scene scene = new Scene(scroll);
            primaryStage.setTitle("VIT Grade Calculator"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage

            scroll.requestFocus();
            System.out.println("Start a new Calculation");

        });
        fileMenu.getItems().add(newFile);
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().add(exit);
        exit.setOnAction(t -> {
            System.exit(0);
        });
        MenuItem open = new MenuItem("Open");
        Text portal = new Text("Portal is already Open");
        portal.setFont(Font.font("Verdana",15));

        open.setOnAction(l -> layout.setCenter(portal));
        fileMenu.getItems().add(open);
        //fileMenu.getItems().add(new MenuItem("Open..."));
        // fileMenu.getItems().add(new MenuItem("Save..."));

        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Settings..."));
        fileMenu.getItems().add(new SeparatorMenuItem());


        //Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem HELP = new MenuItem("New...");
        helpMenu.getItems().add(HELP);
        HELP.setOnAction(e-> {

            Text text = new Text();

            //Setting the text to be added.
            text.setText("This grade calculator portal calculates the marks of all the subjects \n" +
                    " and calculates the grade you have obtained \n" +
                    "* Click on the New Calculation Tab to start doing calculations. \n " +
                    "* Click on exit to exit the portal.");
            text.setFont(Font.font ("Verdana", 15));

            layout.setCenter(text);
        });


        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);



        layout = new BorderPane();
        layout.setTop(menuBar);
        Scene scene = new Scene(layout, 600, 400);


        Text welcome = new Text();
        text.setText("VIT - GRADE CALCULATOR PORTAL.");
        text.setFont(Font.font ("Verdana", 26));
        layout.setCenter(text);
        layout.setStyle("-fx-background-color: lightblue;");
        window.setScene(scene);
        window.show();
    }

}
