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

import java.util.HashSet;

import javafx.scene.shape.Polygon;

public class SpriteHandler {
    private int w;
    private int h;
    
    private HashSet<Sprite> sprites;
    
    public SpriteHandler(int w, int h) {
        this.w = w;
        this.h = h;
        
        this.sprites = new HashSet<>();
    }
    
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    
    public void processMovement() {
        this.sprites.forEach(sprite -> {
            double targetX = sprite.getPositionX() + sprite.getVelocity().getX();
            double targetY = sprite.getPositionY() + sprite.getVelocity().getY();
            
            sprite.moveTo(targetX, targetY);
        
            if (sprite.getType() == 1) {
                return;
            }
            
            if (sprite.getPositionX() < 0) {
                sprite.moveTo(w, sprite.getPositionY());
            }
            
            if (sprite.getPositionY() < 0) {
                sprite.moveTo(sprite.getPositionX(), h);
            }

            if (sprite.getPositionX() > w) {
                sprite.moveTo(0, sprite.getPositionY());
            }

            if (sprite.getPositionY() > h) {
                sprite.moveTo(sprite.getPositionX(), 0);
            }
        });
    }
}
