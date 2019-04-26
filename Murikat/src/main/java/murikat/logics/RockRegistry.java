package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.HashSet;

/**
 * Murikoista kirjaa pitävä luokka
 * @author tkoukkar
 */
public class RockRegistry {
    private HashMap<Sprite, Integer> rocks;
    private Sprite hitRock;
    
    public RockRegistry() {
        this.rocks = new HashMap<>();
    }
    
    /**
     * Lisää kirjanpitoon murikan sekä sen sukupolven.
     * <p>
     * Uudet isot murikat ovat 1. sukupolvea; myöhemmät sukupolvet ovat halkeamalla syntyneitä pienempiä murikoita.
     * </p>
     * @param rock lisättävä murikka
     * @param gen lisättävä murikan sukupolvi
     */
    public void add(Sprite rock, int gen) {
        this.rocks.put(rock, gen);
    }
    
    /**
     * Poistaa murikan kirjanpidosta.
     * @param rock poistettava murikka
     */
    public void remove(Sprite rock) {
        this.rocks.remove(rock);
    }
    
    /**
     * Tutkii, onko parametrina saatu Sprite-olio merkitty murikkana kirjanpitoon.
     * @param sprite tutkittava kappale
     * @return true, jos tutkittava kappale on murikka ja merkitty kirjanpitoon, muuten false
     */
    public Boolean contains(Sprite sprite) {
        return this.rocks.containsKey(sprite);
    }
    
    /**
     * Palauttaa murikan sukupolven.
     * @param rock tutkittava murikka
     * @return sukupolvi
     */
    public int getGeneration(Sprite rock) {
        return this.rocks.get(rock);
    }
    
    public HashSet<Sprite> getRocks() {
        HashSet<Sprite> rockSet = new HashSet(this.rocks.keySet());
        
        return rockSet;
    }

    public void setHitRock(Sprite rock) {
        this.hitRock = rock;
    }
    
    public Sprite getHitRock() {
        return hitRock;
    }
}
