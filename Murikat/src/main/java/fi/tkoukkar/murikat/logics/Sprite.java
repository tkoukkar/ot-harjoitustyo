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

    private int hitPts;
    private Boolean destroyed;
    
    public Sprite(Polygon p, double x, double y) {
        this.form = p;
        
        this.form.setTranslateX(x);
        this.form.setTranslateY(y);
        
        this.velocity = new Point2D(0, 0);
        
        this.hitPts = 1;
        this.destroyed = false;
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
    
    public void setVelocity(Point2D v) {
        this.velocity = v;
    }
    
    public void accelerate(double dir, double v) {
        double xComp = Math.cos(Math.toRadians(this.form.getRotate() + dir)) * v;
        double yComp = Math.sin(Math.toRadians(this.form.getRotate() + dir)) * v;
        
        this.velocity = this.velocity.add(xComp, yComp);
    }
    
    public Sprite emitProjectile(Polygon p, double dir, double v) {
        Sprite projectile = new Sprite(p, this.form.getTranslateX() + 8, this.form.getTranslateY());
        
        projectile.accelerate(this.form.getRotate() + dir, v);
        
        return projectile;
    }
    
    public void setHitPts(int value) {
        this.hitPts = value;
    }
    
    public int getHitPts() {
        return this.hitPts;
    }
    
    public void destroy() {
        this.getForm().setVisible(false);
        this.destroyed = true;
    }
    
    public Boolean isDestroyed() {
        return this.destroyed;
    }
}