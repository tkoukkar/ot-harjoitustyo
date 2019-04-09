package fi.tkoukkar.murikat.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tkoukkar
 */

// import fi.tkoukkar.murikat.sprites.Projectile;
import fi.tkoukkar.murikat.logics.Sprite;
import fi.tkoukkar.murikat.logics.Spaceship;
import fi.tkoukkar.murikat.logics.InputHandler;
import fi.tkoukkar.murikat.logics.SpriteHandler;

import java.util.Random;

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
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

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
        // Set stage
        Random r = new Random();
        
        Pane gamePane = new Pane();
        gamePane.setStyle("-fx-background-color: black;");
        gamePane.setPrefSize(w, h);
        
        Polygon s = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite shipSprite = new Sprite(s, w / 2, h / 2, 0);
        Spaceship ship = new Spaceship(shipSprite);
        
        Polygon p = new Polygon(-10 - r.nextInt(10), -10 - r.nextInt(10), -10 - r.nextInt(10), 10 + r.nextInt(10), 10 + r.nextInt(10), 10 + r.nextInt(10), 10 + r.nextInt(10), -10 - r.nextInt(10));
        Sprite rock = new Sprite(p, 10, 10, 0);
        rock.accelerate(45, 0.2);
        
        SpriteHandler sptHdlr = new SpriteHandler(w, h);
        
        sptHdlr.addSprite(shipSprite);
        sptHdlr.addSprite(rock);
        gamePane.getChildren().addAll(shipSprite.getForm(), rock.getForm());
        
        Scene gScene = new Scene(gamePane);
        
        // Init. controls
        InputHandler inpHdlr = new InputHandler();
        
        gScene.setOnKeyPressed(event -> {
            inpHdlr.input(event.getCode());
        });
        
        gScene.setOnKeyReleased(event -> {
            inpHdlr.remove(event.getCode());
        });
        
        // Main loop
        new AnimationTimer() {
            @Override
            public void handle (long now) {
                inpHdlr.processControls(ship);
                
                if (inpHdlr.getTriggerState() == 1) {
                    Sprite projectile = ship.fire();
                    sptHdlr.addSprite(projectile);
                    gamePane.getChildren().add(projectile.getForm());
                }
                
                sptHdlr.processMovement();
            }
        }.start();
        
        primaryStage.setScene(gScene);
    }
}

