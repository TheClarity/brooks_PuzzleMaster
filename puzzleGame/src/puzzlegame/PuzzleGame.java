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
    int playerX;
    int steps;
    int playerY;
    int col = 40;
    int row = 40;
    boolean movingWallLR = false;
    boolean movingWallUD = false;
    String looking = "right";
int[][] walls = new int[sizeY + 1][sizeX + 1];
int[][] moveBoxes = new int[sizeY + 1][sizeX + 1];
int[][] plus5 = new int[sizeY + 1][sizeX + 1];
int[][] set5 = new int[sizeY + 1][sizeX + 1];
int[][] plus8 = new int[sizeY + 1][sizeX + 1];
int[][] set8 = new int[sizeY + 1][sizeX + 1];
int[][] plus10 = new int[sizeY + 1][sizeX + 1];
int[][] set10 = new int[sizeY + 1][sizeX + 1];
int[][] win = new int[sizeY + 1][sizeX + 1];
        Text t = new Text(10.0D, 20.0D, "Looking: ");
        Text stepText = new Text(10.0D, 60.0D, "Steps Left: " + steps);
Rectangle[][] boxes = new Rectangle[sizeY][sizeX];
    public void start(Stage stage1) {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                moveBoxes[i][j] = 0;
            }
        }
        playerX = 0;
        playerY = 4;
        steps = 30;
        stage1.setTitle("STAGE 1");

        Group root = new Group();
        Scene theScene = new Scene(root);
        stage1.setScene(theScene);

        Canvas canvas = new Canvas(1600.0D, 900.0D);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();


        t.setText("Looking: " + looking);
        root.getChildren().addAll(new Node[]{t, stepText});
        
        int boxPosition = 0;
        
        
       
        fillRowCol(0,2);
        fillRowCol(6, sizeY-1);
        
        plus5[4][9] = 1;
        moveBoxes[3][9] = 1;
        set5[4][12] = 1;
        plus5[4][15] = 1;
        win[winY][winX] = 1;

        int y = 110;
        for (int i = 0; i < sizeY; i++) {
            int x = 110;
            for (int j = 0; j < sizeX; j++) {
                boxes[i][j] = new Rectangle(x, y, 50.0D, 50.0D);
                if ((i == playerY) && (j == playerX)) {
                    boxes[i][j].setFill(Color.CRIMSON);
                    boxes[i][j].setStroke(Color.BLACK);
                } else if (walls[i][j] == 1) {
                    boxes[i][j].setFill(Color.BLACK);
                    boxes[i][j].setStroke(Color.WHITE);
                }
                else if(moveBoxes[i][j] == 1){
                    boxes[i][j].setFill(Color.DARKGRAY);
                    boxes[i][j].setStroke(Color.WHITE);
                }else if (plus5[i][j] == 1) {
                    boxes[i][j].setFill(Color.LIGHTBLUE);
                    boxes[i][j].setStroke(Color.BLACK);
                } 
                else if(set5[i][j] == 1){
                    boxes[i][j].setFill(Color.CORAL);
                    boxes[i][j].setStroke(Color.BLACK);
                }
                else if (win[i][j] == 1) {
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
                if(movingWallLR){
                   if(input.contains("LEFT")){
                   pullWall("left");
                }else{
                       pullWall("right");
                   }
                }
                else if(movingWallUD){
                    
                }
                    else{
                if (input.contains("LEFT")) {
                    looking = "left";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                    if ((playerX - 1 > -1) && (walls[playerY][(playerX - 1)] != 1) && (moveBoxes[playerY][(playerX - 1)] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerX -= 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
                    looking = "left";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if ((input.contains("RIGHT")) ) {
                    looking = "right";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                    if (playerX + 1 < sizeX && (walls[playerY][(playerX + 1)] != 1) && (moveBoxes[playerY][(playerX + 1)] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerX += 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
                    looking = "right";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if (input.contains("UP")) {
                    looking = "up";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                    if ((playerY - 1 > -1) && (walls[(playerY - 1)][playerX] != 1) && (moveBoxes[playerY - 1][(playerX)] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerY -= 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
                    looking = "up";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                } else if (input.contains("DOWN")) {
                    looking = "down";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                    if ((playerY + 1 < sizeY) && (walls[(playerY + 1)][playerX] != 1) && (moveBoxes[playerY + 1][(playerX)] != 1)) {
                        boxes[playerY][playerX].setFill(Color.WHITE);
                        playerY += 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
                    looking = "down";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                }
                else if (input.contains("SHIFT")) {
                    if (looking.equals("right") && (moveBoxes[playerY][(playerX + 1)] == 1)) {
                        movingWallLR = true;
                        System.out.println("movablewall");
                    }else if (looking.equals("left") && (moveBoxes[playerY][(playerX - 1)] == 1)) {
                        movingWallLR = true;
                        System.out.println("movablewall");
                    }
                    
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
                }
                }
                if (win[playerY][playerX] == 1) {
                    stage1.hide();
                    Stage stage2 = new Stage();
                    //start2(stage2);
                } else if (steps <= 0) {
                    System.out.println("YOU DIED");
                    stage1.hide();
                    start(stage1);
                }
                if (plus5[playerY][playerX] == 1) {
                    plus5[playerY][playerX] = 0;
                    steps += 6;
                    stepText.setText("Steps Left: " + steps);
                }
                else if(set5[playerY][playerX] == 1){
                    steps = 5;
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
     public void fillRowCol(int y1, int y2) {
         for(int j = y1; j<= y2; j++){
         wallRow(j);
         }
    }
     public void fillRect(int y1, int y2,int x1, int x2){
         for(int j = y1; j<= y2; j++){
         for(int i = x1; i<= x2; i++){
        walls[j][i] = 1;
            }
         }
     }
     public void pushWall(String direction){
         if(direction.equals("right")){
             
         }
     }
     public void pullWall(String direction){
        if(direction.equals("left")){
            if(walls[playerY][playerX-1]!= 1 && moveBoxes[playerY][playerX-1]!= 1){
                   boxes[playerY][playerX].setFill(Color.WHITE);
                   moveBoxes[playerY][playerX] = 1;
                   boxes[playerY][playerX].setFill(Color.DARKGRAY);
                    boxes[playerY][playerX].setStroke(Color.WHITE);
                   moveBoxes[playerY][playerX +1] = 0;
                   boxes[playerY][playerX +1].setFill(Color.WHITE);
                   boxes[playerY][playerX +1].setStroke(Color.BLACK);
                   
                        playerX -= 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
            
                    looking = "left";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
            }
        if(direction.equals("right")){
            if(walls[playerY][playerX+1]!= 1 && moveBoxes[playerY][playerX+1]!= 1){
                   boxes[playerY][playerX].setFill(Color.WHITE);
                   moveBoxes[playerY][playerX] = 1;
                   boxes[playerY][playerX].setFill(Color.DARKGRAY);
                    boxes[playerY][playerX].setStroke(Color.WHITE);
                   moveBoxes[playerY][playerX -1] = 0;
                   boxes[playerY][playerX -1].setFill(Color.WHITE);
                   boxes[playerY][playerX -1].setStroke(Color.BLACK);
                   
                        playerX += 1;
                        boxes[playerY][playerX].setFill(Color.CRIMSON);
                        steps -= 1;
                    }
            
                    looking = "right";
                    t.setText("Looking: " + looking);
                    stepText.setText("Steps Left: " + steps);
            }
        movingWallLR = false;
        }

     
    public static void main(String[] args) {
        launch(args);
    }
}
