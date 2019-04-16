/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.tkoukkar.murikat.logics;

/**
 *
 * @author tkoukkar
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;

public class Spaceship {
    private double thrust;
    private double wpnPower;
    private Sprite sprite;
    
    public Spaceship(Sprite s) {
        this.sprite = s;
        
        this.sprite.rotate(-90);
        
        this.thrust = 0.1;
        this.wpnPower = 12;
    }

    public Sprite getSprite() {
        return sprite;
    }
    
    public void turnLeft() {
        this.sprite.rotate(-3);
    }
    
    public void turnRight() {
        this.sprite.rotate(3);
    }
    
    public void accelerate() {
        Point2D v = this.sprite.getVelocity();
        
        double xComp = Math.cos(Math.toRadians(this.sprite.getForm().getRotate())) * this.thrust;
        double yComp = Math.sin(Math.toRadians(this.sprite.getForm().getRotate())) * this.thrust;
        
        v = v.add(xComp, yComp);
        
        this.sprite.setVelocity(v);
    }
    
    public Sprite fire() {        
        Polygon p = new Polygon(-2, 0, 0, 2, 2, 0, 0, -2);
        p.setFill(Color.AZURE);
        
        Sprite projectile = this.sprite.emitProjectile(p, 0, this.wpnPower);
        
        return projectile;
    }
}
