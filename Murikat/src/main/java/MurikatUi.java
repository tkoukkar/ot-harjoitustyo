/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tkoukkar
 */

import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

public class MurikatUi extends Application {
    public static int w = 1280;
    public static int h = 960;
        
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
        
        startBtn.setOnAction(event -> {
            run(primaryStage);
        });
        
        exitBtn.setOnAction(event -> {
            Platform.exit();
        });
        
        VBox menuBox = new VBox(24);
        menuBox.setPrefSize(w, h);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(startBtn, scoreBtn, exitBtn);
        
        Scene mScene = new Scene(menuBox);
        return mScene;
    }
    
    public void run(Stage primaryStage) {
        Pane gamePane = new Pane();
        gamePane.setStyle("-fx-background-color: black;");
        gamePane.setPrefSize(w, h);
        
        Spaceship ship = new Spaceship(w / 2, h / 2);
        
        gamePane.getChildren().add(ship.getSprite());
        
        Scene gScene = new Scene(gamePane);
        
        gScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                ship.turnLeft();
            }
        
            if (event.getCode() == KeyCode.RIGHT) {
                ship.turnRight();
            }
            
            if (event.getCode() == KeyCode.UP) {
                ship.accelerate();
            }
        });
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ship.move();
            }
        }.start();
        
        primaryStage.setScene(gScene);
    }
}
