package murikat.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.logics.Spaceship;
import murikat.logics.InputHandler;
import murikat.logics.SpriteHandler;

import murikat.dao.HighScoreDao;

import java.sql.SQLException;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.animation.*;
import javafx.event.Event;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import murikat.dao.SpaceshipDao;

/**
 *
 * @author tkoukkar
 */

public class MurikatUi extends Application {
    public static int w = 1280;
    public static int h = 960;
    
    static HighScoreDao scoreDao;
    
    private int pts;
        
    @Override
    public void start(Stage primaryStage) throws Exception {
        scoreDao = new HighScoreDao("jdbc:sqlite:data/scores.db");
        scoreDao.loadData();
        
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
            try {
                primaryStage.setScene(hiScores(primaryStage, false));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    public Scene hiScores(Stage primaryStage, Boolean newHighScore) throws ClassNotFoundException, SQLException {
        // Build main high score view
        Label scoresTitle = new Label("PISTE-ENNÄTYKSET");
        scoresTitle.setTextFill(Color.GREENYELLOW);
        scoresTitle.setFont(new Font("Courier", 24));
        
        ListView nameList = new ListView(FXCollections.observableArrayList(scoreDao.getNames()));
        ListView scoreList = new ListView(FXCollections.observableArrayList(scoreDao.getScores()));
        
        nameList.setMouseTransparent(true);
        nameList.setFocusTraversable(false);
        
        scoreList.setMouseTransparent(true);
        scoreList.setFocusTraversable(false);
        
        scoreList.setPrefWidth(64);
        
        // Build controls for exiting high score view
        Button closeBtn = new Button("Päävalikkoon");
        closeBtn.setPrefSize(200, 36);
        
        closeBtn.setOnAction(event -> {
            primaryStage.setScene(mainMenu(primaryStage));
        });
        
        // Build controls for entering high score new
        Label instrc = new Label("Syötä nimesi:");
        instrc.setTextFill(Color.GREENYELLOW);
        instrc.setFont(new Font("Courier", 18));
        
        TextField nameField = new TextField("nimi");
        nameField.setMaxWidth(160);
        
        Button okBtn = new Button("OK");
        
        HBox newBox = new HBox(12);
        newBox.setAlignment(Pos.CENTER);
        
        okBtn.setOnAction(event -> {
            try {
                scoreDao.addNewScore(nameField.getText(), pts);
                scoreDao.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            nameList.setItems(FXCollections.observableArrayList(scoreDao.getNames()));
            scoreList.setItems(FXCollections.observableArrayList(scoreDao.getScores()));
            
            newBox.setVisible(false);
            
            scoresTitle.setTextFill(Color.GREENYELLOW);
            
            nameList.setDisable(false);
            scoreList.setDisable(false);
            closeBtn.setDisable(false);
        });

        newBox.getChildren().addAll(instrc, nameField, okBtn);
        
        if (newHighScore) {
            scoresTitle.setTextFill(Color.GREEN);
            nameList.setDisable(true);
            scoreList.setDisable(true);
            closeBtn.setDisable(true);
        } else {
            newBox.setVisible(false);
        }
        
        // Assemble & display scene
        HBox listBox = new HBox(24);
        listBox.setPrefSize(w, h / 2);
        listBox.setAlignment(Pos.CENTER);
        
        listBox.getChildren().addAll(nameList, scoreList);
        
        VBox boxBox = new VBox(24);
        boxBox.setPrefSize(w, h);
        boxBox.setAlignment(Pos.CENTER);
        boxBox.setStyle("-fx-background-color: black;");
        
        boxBox.getChildren().addAll(scoresTitle, newBox, listBox, closeBtn);
        
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
        
        Spaceship ship = new Spaceship(new SpaceshipDao("data/spaceship.dat"), w / 2, h / 2);
        
        SpriteHandler sptHdlr = new SpriteHandler(gamePane, ship.getSprite());
        
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
                            gameOver(primaryStage);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {                                            // add points
                        pts += 100;
                        ptsDisplay.setText(Integer.toString(pts));
                    }
                }
                
                int p = r.nextInt(10000 - pts);
                
                while (p < 0) {
                    p += r.nextInt(100);
                }
                
                if (p < 20 - (sptHdlr.getNumberOfRocks())) {
                    sptHdlr.spawnRock(p);
                }
                
                if (sptHdlr.getNumberOfRocks() <= 0) {
                    sptHdlr.spawnRock(r.nextInt(2));
                }
            }
        });
        
        timeline.setCycleCount(Timeline.INDEFINITE); 
        timeline.setAutoReverse(false); 
        timeline.getKeyFrames().add(frame); 
        timeline.play();
        
        primaryStage.setScene(gScene);
    }
    
    public void gameOver(Stage primaryStage) throws ClassNotFoundException, SQLException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MurikatUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (pts > scoreDao.getLast()) {
            primaryStage.setScene(hiScores(primaryStage, true));
        } else {
            primaryStage.setScene(mainMenu(primaryStage));
        }
    }
}