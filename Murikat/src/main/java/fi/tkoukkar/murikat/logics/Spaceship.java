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

import javafx.scene.shape.Polygon;

public class Spaceship {
    private double thrust;
    private double wpnPower;
    private Sprite sprite;
    
    public Spaceship(Sprite s) {
        this.sprite = s;
        
        this.sprite.rotate(-90);
        
        this.thrust = 0.01;
        this.wpnPower = 2.5;
    }

    public Sprite getSprite() {
        return sprite;
    }
    
    public void turnLeft() {
        this.sprite.rotate(-1);
    }
    
    public void turnRight() {
        this.sprite.rotate(1);
    }
    
    public void accelerate() {
        this.sprite.accelerate(0, this.thrust);
    }
    
    public Sprite fire() {        
        Polygon p = new Polygon(-2, 0, 0, 2, 2, 0, 0, -2);
        
        Sprite projectile = this.sprite.emitProjectile(p, 0, this.wpnPower);
        
        return projectile;
    }
}
