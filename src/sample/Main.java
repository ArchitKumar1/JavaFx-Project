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
        window.setTitle("Grade Calculator Portal");
        text.setText("Welcome to the portal");
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

            pane.setStyle("-fx-background-color: red;"); // Background color
            pane.setHgap(10); // Horizontal gap
            pane.setVgap(10); // Vertical gap
            pane.setPadding(new Insets(10, 10, 10, 10));

            // Create and add title
            Label nameLabel = new Label("Assignment");
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD,
                    FontPosture.ITALIC, 15));
            pane.add(nameLabel, 0, 0);

            Label gradeLabel = new Label("My Score");
            gradeLabel.setFont(Font.font("Arial", FontWeight.BOLD,
                    FontPosture.ITALIC, 15));
            pane.add(gradeLabel, 1, 0);

            Label scoreLabel = new Label("Full Score");
            scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD,
                    FontPosture.ITALIC, 15));
            pane.add(scoreLabel, 2, 0);


            Label weightLabel = new Label("% of Grade");
            weightLabel.setFont(Font.font("Arial", FontWeight.BOLD,
                    FontPosture.ITALIC, 15));
            pane.add(weightLabel, 3, 0);

            int num_assignments = 3;
            // Create and add grade fields
            for (int i = 1; i < num_assignments; i++) {

                pane.add( new Label (""+i), 0, i);
                pane.add( new TextField(), 1, i);
                pane.add( new TextField(), 2, i);
                pane.add( new TextField(), 3, i);
            }

            Button addAssignment = new Button("+");
            Button calculate = new Button("Calculate");
            Button needButton = new Button("To get:");
            //TextField needGrade = new TextField();
            TextField result = new TextField();

            pane.add(addAssignment, 0, num_assignments);
            pane.add(calculate, 1, num_assignments);

            HBox needBox = new HBox(10, needButton);
            //needGrade.setMaxWidth(50);
            pane.add(needBox, 2, num_assignments);

            pane.add(result, 3, num_assignments);

            // Create and register add assignment button handler
            addAssignment.setOnAction(ee -> {

                // Move buttons
                int row = pane.getRowIndex(addAssignment) + 1;

                pane.getChildren().remove(addAssignment);
                pane.getChildren().remove(calculate);
                pane.getChildren().remove(needBox);
                pane.getChildren().remove(result);

                pane.add(addAssignment, 0, row);
                pane.add(calculate, 1, row);
                pane.add(needBox, 2, row);
                pane.add(result, 3, row);

                // Add new row of text fields
                row--;
                pane.add( new Label (""+row), 0, row);
                pane.add( new TextField(), 1, row);
                pane.add( new TextField(), 2, row);
                pane.add( new TextField(), 3, row);

            });


            // Create and register needButton button handler
            needButton.setOnAction(ee -> {

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
                Double desiredGrade = 100.0;
//                try {
//                    desiredGrade = Double.parseDouble(((TextField)needGrade).getText());
//                } catch (NumberFormatException ex) {
//                    // do nothing
//                }

                int min = scores.size();
//                if (min > percents.size())
//                    min = percents.size();
//
//                for (int i = 0; i < min; i++) {
//                    grade = scores.get(i) * percents.get(i) + grade;
//                    percentTotal = percentTotal + percents.get(i);
//                }
//
//                grade = desiredGrade - grade;
//                percentTotal = 100 - percentTotal;
//                grade = grade/percentTotal;
                int cnt = 0;
                for (int i = 0; i < min; i++) {
                    grade += scores.get(i) / percents.get(i) ;
                    cnt++;
                }

                grade = 100*(grade)/cnt;

                result.clear();
                result.appendText(grade*100+"%");

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
                result.appendText(100*(grade/percentTotal)+"%");
            });

            Button Exit = new Button("Exit the program ");
            Exit.setOnAction(t -> {

                System.exit(0);
            });
            pane.add(Exit, 5, num_assignments);
            // Create a scene and place it in the stage
            Scene scene = new Scene(scroll);
            primaryStage.setTitle("Grade Calculator"); // Set the stage title
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
        open.setOnAction(l -> System.out.println("Open the Game"));
        fileMenu.getItems().add(open);
        //fileMenu.getItems().add(new MenuItem("Open..."));
        // fileMenu.getItems().add(new MenuItem("Save..."));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(l -> System.out.println("Game Saved"));
        fileMenu.getItems().add(save);
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
            text.setText("This grade calculator portal calculates the marks of all the subjects" +
                    " that are " + " \n" +
                    "required to get a particular percentage.\n." +
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
        scene.getStylesheets().add("Viper.css");

        Text welcome = new Text();
        text.setText("GRADE CALCULATOR PORTAL.");
        text.setFont(Font.font ("Verdana", 26));
        layout.setCenter(text);

        window.setScene(scene);
        window.show();
    }

}
