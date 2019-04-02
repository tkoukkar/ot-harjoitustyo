/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tkoukkar
 */

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Spaceship {
    private Polygon sprite;
    private Point2D velocity;
    
    private double thrust;
    
    public Spaceship(int x, int y) {
        this.sprite = new Polygon(-8, -8, 24, 0, -8, 8);
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
        this.sprite.setFill(Color.WHITE);
        
        this.velocity = new Point2D(0, 0);
        
        this.thrust = 0.05;
    }
    
    public Polygon getSprite() {
        return this.sprite;
    }
    
    public void turnLeft() {
        this.sprite.setRotate(this.sprite.getRotate() - 5);
    }
    
    public void turnRight() {
        this.sprite.setRotate(this.sprite.getRotate() + 5);
    }
    
    public void accelerate() {
        double xComp = Math.cos(Math.toRadians(this.sprite.getRotate())) * this.thrust;
        double yComp = Math.sin(Math.toRadians(this.sprite.getRotate())) * this.thrust;
        
        this.velocity = this.velocity.add(xComp, yComp);
    }
    
    public void move() {
        this.sprite.setTranslateX(this.sprite.getTranslateX() + this.velocity.getX());
        this.sprite.setTranslateY(this.sprite.getTranslateY() + this.velocity.getY());
        
        if (this.sprite.getTranslateX() < 0) {
            this.sprite.setTranslateX(1280);
        }
        
        if (this.sprite.getTranslateY() < 0) {
            this.sprite.setTranslateY(960);
        }
        
        if (this.sprite.getTranslateX() > 1280) {
            this.sprite.setTranslateX(0);
        }
        
        if (this.sprite.getTranslateY() > 960) {
            this.sprite.setTranslateY(0);
        }
    }
}