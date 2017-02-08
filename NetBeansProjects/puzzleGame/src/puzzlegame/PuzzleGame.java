package puzzlegame;

import java.io.PrintStream;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PuzzleGame extends Application {

    int winX = 19;
    int winY = 4;
    int sizeX = 20;
    int sizeY = 15;
    int playerX = 0;
    int steps = 30;
    int playerY = 4;
    int col = 40;
        int row = 40;
    String looking = "right";
int[][] walls = new int[sizeY + 1][sizeX + 1];
int[][] plus5 = new int[sizeY + 1][sizeX + 1];
int[][] win = new int[sizeY + 1][sizeX + 1];
    public void start(Stage stage1) {
        stage1.setTitle("STAGE 1");

        Group root = new Group();
        Scene theScene = new Scene(root);
        stage1.setScene(theScene);

        Canvas canvas = new Canvas(1600.0D, 900.0D);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Text t = new Text(10.0D, 20.0D, "Looking: ");
        Text stepText = new Text(10.0D, 60.0D, "Steps Left: " + steps);
        t.setText("Looking: " + looking);
        root.getChildren().addAll(new Node[]{t, stepText});
        
        int boxPosition = 0;
        Rectangle[][] boxes = new Rectangle[sizeY][sizeX];

        
        wallRow(0);
        wallRow(1);
        wallRow(2);
        wallRow(6);
        wallRow(7);
        wallFill(9,12);

        
        plus5[8][4] = 1;
        
        win[winY][winX] = 1;

        int y = 110;
        for (int i = 0; i < sizeY; i++) {
            int x = 110;
            for (int j = 0; j < sizeX; j++) {
                boxes[i][j] = new Rectangle(x, y, 50.0D, 50.0D);
                if ((i == playerY) && (j == playerX)) {
                    boxes[i][j].setFill(Color.RED);
                    boxes[i][j].setStroke(Color.BLACK);
                } else if (walls[i][j] == 1) {
                    boxes[i][j].setFill(Color.BLACK);
                    boxes[i][j].setStroke(Color.WHITE);
                } else if (plus5[i][j] == 1) {
                    boxes[i][j].setFill(Color.LIGHTBLUE);
                    boxes[i][j].setStroke(Color.BLACK);
                } else if (win[i][j] == 1) {
                    boxes[i][j].setFill(Color.GREEN);
                    boxes[i][j].setStroke(Color.BLACK);
                } else {
                    boxes[i][j].setFill(null);
                    boxes[i][j].setStroke(Color.BLACK);
                }
                
                root.getChildren().add(boxes[i][j]);
                x += 50;
            }
            y += 50;
        }
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
            }

        }.start();

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {

                String input = e.getCode().toString();
                if (input.contains("LEFT")) {
                    if ((playerX - 1 > -1) && (walls[playerY][(playerX - 1)] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerX -= 1;
                        boxes[playerY][playerX].setFill(Color.RED);
                        steps -= 1;
                    }
                    looking = "left";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if ((input.contains("RIGHT")) && (walls[playerY][(playerX + 1)] != 1)) {
                    if (playerX + 1 < sizeX) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerX += 1;
                        boxes[playerY][playerX].setFill(Color.RED);
                        steps -= 1;
                    }
                    looking = "right";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if (input.contains("UP")) {
                    if ((playerY - 1 > -1) && (walls[(playerY - 1)][playerX] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerY -= 1;
                        boxes[playerY][playerX].setFill(Color.RED);
                        steps -= 1;
                    }
                    looking = "up";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if (input.contains("DOWN")) {
                    if ((playerY + 1 < sizeY) && (walls[(playerY + 1)][playerX] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerY += 1;
                        boxes[playerY][playerX].setFill(Color.RED);
                        steps -= 1;
                    }
                    looking = "down";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                }
                if (win[playerY][playerX] == 1) {
                    stage1.hide();
                } else if (steps <= 0) {
                    System.out.println("YOU DIED");
                    stage1.hide();
                }
                if (plus5[playerY][playerX] == 1) {
                    plus5[playerY][playerX] = 0;
                    steps += 5;
                    stepText.setText("Steps Left: " + steps);
                }
            }

        });
        stage1.show();
    }

    public void wallRow(int y) {
        for(int i = 0; i<sizeX;i++){
            walls[y][i] = 1;
        }
    }
     public void wallFill(int y1, int y2) {
         for(int j = y1; j< y1; y1++){
        for(int i = 0; i<sizeX;i++){
            walls[y1][i] = 1;
        }
         }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
