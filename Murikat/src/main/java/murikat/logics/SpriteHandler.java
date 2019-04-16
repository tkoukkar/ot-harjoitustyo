/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package murikat.logics;

/**
 *
 * @author tkoukkar
 */

import murikat.gui.MurikatUi;
import static murikat.gui.MurikatUi.h;
import static murikat.gui.MurikatUi.w;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javafx.event.EventType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

public class SpriteHandler {
    private int w;
    private int h;
    
    private Boolean collDetected;
    
    private Random r;
    
    private Pane pane;
    private Sprite shipSprite;
    private HashSet<Sprite> allSprites;
    private HashSet<Sprite> rocks;
    private HashSet<Sprite> fragments;
    private HashSet<Sprite> projectiles;
    
    public SpriteHandler(Pane pane) {
        this.w = MurikatUi.w;
        this.h = MurikatUi.h;
        
        this.pane = pane;
        
        this.r = new Random();
        
        this.allSprites = new HashSet<>();
        this.rocks = new HashSet<>();
        this.fragments = new HashSet<>();
        this.projectiles = new HashSet<>();
    }
    
    public Spaceship buildSpaceship() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        p.setFill(Color.WHITE);
        
        this.shipSprite = new Sprite(p, w / 2, h / 2);
        
        this.allSprites.add(shipSprite);
        this.pane.getChildren().add(shipSprite.getForm());
        
        Spaceship s = new Spaceship(this.shipSprite);
        
        return s;
    }
    
    public void initRocks() {
        for(int i = 0; i < 4; i++) { 
            spawnRock(i);
        }
    }
    
    public void spawnRock(int i) {
        Polygon p = new Polygon(-30 - r.nextInt(15), -30 - r.nextInt(15), -30 - r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), -30 - r.nextInt(15));
        p.setFill(Color.LIGHTGREY);
            
        Sprite rock = new Sprite(p, r.nextInt(w), 0);
            
        rock.accelerate(-45 + (90 * (i - 1)) + r.nextInt(30), 2);
            
        this.allSprites.add(rock);
        this.rocks.add(rock);
        this.pane.getChildren().add(rock.getForm());
    }
    
    public int getNumberOfRocks() {
        return this.rocks.size();
    }
    
    public void addSprite(Sprite s) {
        this.allSprites.add(s);
        this.pane.getChildren().add(s.getForm());
    }
    
    public void removeSprite(Sprite s) {
        s.destroy();
        
        this.allSprites.remove(s);
        
        if (this.rocks.contains(s)) {
            this.rocks.remove(s);
        }
        
        if (this.projectiles.contains(s)) {
            this.projectiles.remove(s);
        }
    }
    
    public void processFiring(Spaceship s) {
        if (this.projectiles.size() >= 3) {
            return;
        }
        
        Sprite projectile = s.fire();
        this.allSprites.add(projectile);
        this.projectiles.add(projectile);
        this.pane.getChildren().add(projectile.getForm());
    }
    
    public void processMovement() {
        this.allSprites.forEach(sprite -> {
            double targetX = sprite.getPositionX() + sprite.getVelocity().getX();
            double targetY = sprite.getPositionY() + sprite.getVelocity().getY();
            
            sprite.moveTo(targetX, targetY);
            
            if (sprite.getPositionX() < 0 || sprite.getPositionX() > w) {
                wrap(sprite, Math.abs(sprite.getPositionX() - w), sprite.getPositionY());
            }
            
            if (sprite.getPositionY() < 0 || sprite.getPositionY() > h) {
                wrap(sprite, sprite.getPositionX(), Math.abs(sprite.getPositionY() - h));
            }
        });
    }
    
    public Boolean processCollisions() {
        this.collDetected = false;
        
        this.allSprites.forEach(sprite -> {
            if (!this.rocks.contains(sprite)) {
                this.rocks.forEach(rock -> {
                    if (rock.getForm().getBoundsInParent().contains(sprite.getPositionX(), sprite.getPositionY())) {
                        this.collDetected = true;
                        sprite.setHitPts(sprite.getHitPts() - 1);
                        
                        if (!this.fragments.contains(rock) && rock.getHitPts() > 0) {
                            Polygon p = new Polygon(-15 - r.nextInt(8), -15 - r.nextInt(8), -15 - r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), -15 - r.nextInt(8));
                            p.setFill(Color.LIGHTGRAY);
                            Polygon q = new Polygon(-15 - r.nextInt(8), -15 - r.nextInt(8), -15 - r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), 15 + r.nextInt(8), -15 - r.nextInt(8));
                            q.setFill(Color.LIGHTGRAY);

                            Sprite oneHalf = rock.emitProjectile(p, 45 + r.nextInt(40), 4);
                            Sprite otherHalf = rock.emitProjectile(q, -135  + r.nextInt(40), 4);

                            this.rocks.add(oneHalf);
                            this.fragments.add(oneHalf);
                            this.rocks.add(otherHalf);
                            this.fragments.add(otherHalf);
                            addSprite(oneHalf);
                            addSprite(otherHalf);
                        }
                            
                        rock.setHitPts(rock.getHitPts() - 1);
                    }
                });
            }
        });
        
        return this.collDetected;
    }
        
    public void wrap(Sprite s, double x, double y) {
        if (this.projectiles.contains(s)) {
            s.destroy();
        } else {
            s.moveTo(x, y);
        }
    }
    
    public void processDestruction() {
        this.allSprites.forEach(sprite -> {
            if (sprite.getHitPts() < 1) {
                sprite.destroy();
            }
        });
        
        this.allSprites.removeIf(s -> s.isDestroyed());
        this.projectiles.removeIf(s -> s.isDestroyed());
        this.rocks.removeIf(s -> s.isDestroyed());
        
        if (this.rocks.isEmpty()) {
            spawnRock(r.nextInt(2));
        }
    }
}
