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

import java.util.HashSet;

import javafx.scene.input.KeyCode;

public class InputHandler {
    private HashSet<KeyCode> keys;
    private Spaceship ship;
    private int triggerState;
        
    public InputHandler(Spaceship s) {
        this.keys = new HashSet<>();
        this.ship = s;
        this.triggerState = 0;
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
    
    public void processControls() {
        this.keys.forEach(c -> {
            if (c.equals(KeyCode.LEFT)) {
                this.ship.turnLeft();
            }
            
            if (c.equals(KeyCode.RIGHT)) {
                this.ship.turnRight();
            }
            
            if (c.equals(KeyCode.UP)) {
                this.ship.accelerate();
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
