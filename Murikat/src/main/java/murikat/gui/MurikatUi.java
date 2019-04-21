package murikat.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tkoukkar
 */

import murikat.logics.Sprite;
import murikat.logics.Spaceship;
import murikat.logics.InputHandler;
import murikat.logics.SpriteHandler;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.animation.*;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MurikatUi extends Application {
    public static int w = 1280;
    public static int h = 960;
    
    private int pts;
        
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Murikat");            
        primaryStage.setScene(mainMenu(primaryStage));
        primaryStage.show();
    }
        
    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene mainMenu(Stage primaryStage) {
        Button startBtn = new Button("Uusi peli");
        Button scoreBtn = new Button("Ennätykset");
        Button exitBtn = new Button("Lopeta");
        
        startBtn.setFocusTraversable(false);
        scoreBtn.setFocusTraversable(false);
        exitBtn.setFocusTraversable(false);
        
        startBtn.setOnAction(event -> {
            pts = 0;
            run(primaryStage);
        });
        
        scoreBtn.setOnAction(event -> {
            primaryStage.setScene(hiScores(primaryStage));
        });
        
        exitBtn.setOnAction(event -> {
            Platform.exit();
        });
        
        VBox menuBox = new VBox(24);
        menuBox.setPrefSize(w, h);
        menuBox.setStyle("-fx-background-color: black;");
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(startBtn, scoreBtn, exitBtn);
        
        Scene mScene = new Scene(menuBox);
        return mScene;
    }
    
    public Scene hiScores(Stage primaryStage) {
        ListView nameList = new ListView();
        ListView scoreList = new ListView();
        
        nameList.setMouseTransparent(true);
        nameList.setFocusTraversable(false);
        
        scoreList.setMouseTransparent(true);
        scoreList.setFocusTraversable(false);
        
        nameList.getItems().add("Item 1");
        nameList.getItems().add("Item 2");
        nameList.getItems().add("Item 3");
        
        scoreList.getItems().add("10000");
        scoreList.getItems().add("9000");
        scoreList.getItems().add("8000");
        
        scoreList.setPrefWidth(64);
        
        HBox scoreBox = new HBox(24);
        scoreBox.setPrefSize(w, h / 2);
        scoreBox.setAlignment(Pos.CENTER);
        
        scoreBox.getChildren().addAll(nameList, scoreList);
        
        Button okBtn = new Button("OK");
        okBtn.setPrefSize(200, 36);
        
        okBtn.setOnAction(event -> {
            primaryStage.setScene(mainMenu(primaryStage));
        });
        
        VBox boxBox = new VBox(24);
        boxBox.setPrefSize(w, h);
        boxBox.setAlignment(Pos.CENTER);
        boxBox.setStyle("-fx-background-color: black;");
        
        boxBox.getChildren().addAll(scoreBox, okBtn);
        
        Scene sScene = new Scene(boxBox);
        return sScene;
    }
    
    public void run(Stage primaryStage) {
        // Set stage
        Pane gamePane = new Pane();
        gamePane.setStyle("-fx-background-color: black;");
        gamePane.setPrefSize(w, h);
        
        Label ptsDisplay = new Label("0");
        ptsDisplay.setFont(new Font("Courier", 20));
        ptsDisplay.setTextFill(Color.GREENYELLOW);
        
        gamePane.getChildren().add(ptsDisplay);
        
        SpriteHandler sptHdlr = new SpriteHandler(gamePane);
        
        Spaceship ship = sptHdlr.buildSpaceship();
        sptHdlr.initRocks();
        
        Scene gScene = new Scene(gamePane);
        
        // Init. controls
        InputHandler inpHdlr = new InputHandler(ship);
        
        gScene.setOnKeyPressed(event -> {
            inpHdlr.input(event.getCode());
        });
        
        gScene.setOnKeyReleased(event -> {
            inpHdlr.remove(event.getCode());
        });
        
        // Main loop
        Timeline timeline  = new Timeline();
        Random r = new Random();
        
        final Duration frDur = Duration.millis(1000/60);
        
        final KeyFrame frame = new KeyFrame(frDur, new EventHandler() {
            @Override
            public void handle(Event event) {
                inpHdlr.processControls();

                if (inpHdlr.getTriggerState() == 1) {
                    sptHdlr.processFiring(ship);
                }

                sptHdlr.processMovement();
                Boolean collision = sptHdlr.processCollisions();
                sptHdlr.processDestruction();

                if (collision) {
                    if (ship.getSprite().isDestroyed()) {               // game over
                        timeline.stop();
                        
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        
                        primaryStage.setScene(mainMenu(primaryStage));
                    } else {                                            // add points
                        pts += 100;
                        ptsDisplay.setText(Integer.toString(pts));
                    }
                }
                
                int p = r.nextInt(10000 - pts);
                if (pts >= 10000) {
                    p = r.nextInt(100);
                }
                
                if (p < 20 - (sptHdlr.getNumberOfRocks())) {
                    sptHdlr.spawnRock(p);
                }
            }
        });
        
        timeline.setCycleCount(Timeline.INDEFINITE); 
        timeline.setAutoReverse(false); 
        timeline.getKeyFrames().add(frame); 
        timeline.play();
        
        primaryStage.setScene(gScene);
    }
}