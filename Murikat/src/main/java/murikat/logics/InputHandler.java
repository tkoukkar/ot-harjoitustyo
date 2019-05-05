package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashSet;

import javafx.scene.input.KeyCode;

/**
 * Näppäinkomentojen käsittelystä vastaava luokka
 * @author tkoukkar
 */
public class InputHandler {
    private HashSet<KeyCode> keys;
    private Spaceship ship;
    private int triggerState;
        
    public InputHandler(Spaceship s) {
        this.keys = new HashSet<>();
        this.ship = s;
        this.triggerState = 0;
    }
    
    /**
     * Lisää käyttäjän painaman näppäimen koodin käsiteltäväksi.
     * 
     * @param kc painetun näppäimen näppäinkoodi
     */
    public void input(KeyCode kc) {
        this.keys.add(kc);
    }
    
    /**
     * Poistaa näppäinkomennon käsiteltävien joukosta.
     * <p>
     * Välilyönnin tapauksessa nollataan liipaisimen tilaa kuvastavan muuttujan arvo.
     * </p>
     * 
     * @param kc poistettavan näppäimen koodi
     */
    public void remove(KeyCode kc) {
        this.keys.remove(kc);
        
        if (kc.equals(KeyCode.SPACE)) {
            this.triggerState = 0;
        }
    }
    
    /**
     * Käsittelee näppäinkomennot ja säätelee niiden perusteella avaruusaluksen liikettä ja/tai liipaisimen tilaa.
     * <p>
     * Metodi käy läpi listalla olevat näppäinkomennot ja kutsuu kutakin niistä vastaavaa avaruusaluksen kääntö- tai kiihdytysmetodia, 
     * tai liipaisinnäppäimen (välilyönti) tapauksessa kasvattaa liipaisimen tilaa kuvastavan muuttujan arvoa.
     * </p>
     */
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
    
    /**
     * Palauttaa liipaisimen tilan; arvo on sitä suurempi, mitä pidempään liipaisin on ollut painettuna.
     * 
     * @return liipaisimen tila
     */
    public int getTriggerState() {
        return this.triggerState;
    }
}
