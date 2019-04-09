package fi.tkoukkar.murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tkoukkar
 */

import java.util.HashSet;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Sprite {
    private Polygon form;
    private Point2D velocity;
    private Boolean destroyed;
    private int type;
    
    public Sprite(Polygon p, double x, double y, int t) {
        this.form = p;
        
        this.form.setFill(Color.WHITE);
        this.form.setTranslateX(x);
        this.form.setTranslateY(y);
        
        this.velocity = new Point2D(0, 0);
        
        this.destroyed = false;
        this.type = t;
    }
    
    public Polygon getForm() {
        return this.form;
    }
    
    public void rotate(double deg) {
        this.form.setRotate(this.form.getRotate() + deg);
    }

    public double getPositionX() {
        return this.form.getTranslateX();
    }

    public double getPositionY() {
        return this.form.getTranslateY();
    }

    public void moveTo(double x, double y) {
        this.form.setTranslateX(x);
        this.form.setTranslateY(y);
    }
    
    public Point2D getVelocity() {
        return this.velocity;
    }
    
    public void accelerate(double dir, double v) {
        double xComp = Math.cos(Math.toRadians(this.form.getRotate() + dir)) * v;
        double yComp = Math.sin(Math.toRadians(this.form.getRotate() + dir)) * v;
        
        this.velocity = this.velocity.add(xComp, yComp);
    }
    
    public Sprite emitProjectile(Polygon p, double dir, double v) {
        Sprite projectile = new Sprite(p, this.form.getTranslateX() + 8, this.form.getTranslateY(), 1);
        
        projectile.accelerate(this.form.getRotate() + dir, v);
        
        return projectile;
    }
    
    public int getType() {
        return this.type;
    }
    
    public Boolean isDestroyed() {
        return this.destroyed;
    }
}