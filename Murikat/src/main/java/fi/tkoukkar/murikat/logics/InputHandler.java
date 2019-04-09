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

import javafx.scene.input.KeyCode;

public class InputHandler {
    HashSet<KeyCode> keys;
    private int triggerState;
    
    private double shipThrust;
    
    public InputHandler() {
        this.keys = new HashSet<>();
        this.triggerState = 0;
        this.shipThrust = 0.01;
    }
    
    public void input(KeyCode kc) {
        this.keys.add(kc);
    }
    
    public void remove(KeyCode kc) {
        this.keys.remove(kc);
        
        if (kc.equals(KeyCode.SPACE)) {
            this.triggerState = 0;
        }
    }
    
    public void processControls(Spaceship s) {
        this.keys.forEach(c -> {
            if (c.equals(KeyCode.LEFT)) {
                s.turnLeft();
            }
            
            if (c.equals(KeyCode.RIGHT)) {
                s.turnRight();
            }
            
            if (c.equals(KeyCode.UP)) {
                s.accelerate();
            }
            
            if (c.equals(KeyCode.SPACE)) {
                this.triggerState++;
            }
        });
    }
    
    public int getTriggerState() {
        return this.triggerState;
    }
}
