package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.gui.MurikatUi;

import java.util.HashSet;
import java.util.Random;

import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Sprite-olioiden käsittelystä vastaava luokka
 * @author tkoukkar
 */
public class SpriteHandler {
    private int w;
    private int h;
    
    private int hitScored;
    
    private Random r;
    
    private Pane pane;
    private Spaceship ship;
    private Sprite shipSprite;
    private RockRegistry rockReg;
    private HashSet<Sprite> allSprites;
    private HashSet<Sprite> projectiles;
    
    public SpriteHandler(Pane pane, Spaceship plrVessel) {
        this.w = MurikatUi.w;
        this.h = MurikatUi.h;
        
        this.pane = pane;
        this.ship = plrVessel;
        this.shipSprite = plrVessel.getSprite();
        
        this.r = new Random();
        
        this.rockReg = new RockRegistry();
        
        this.allSprites = new HashSet<>();
        this.projectiles = new HashSet<>();
        
        this.allSprites.add(shipSprite);
        this.pane.getChildren().add(shipSprite.getForm());
    }
       
    /**
    * Luo neljä isoa murikkaa kutsumalla neljä kertaa metodia {@link #spawnRock(int)}.
    */
    public void initRocks() {
        for (int i = 0; i < 4; i++) {
            spawnRock(i);
        }
    }
    
    /**
    * Metodi luo yhden uuden ison murikan peliin ja arpoo sille liikesuunnan.
    * 
    * @param i Liikesuunnan arpomisessa käytetty kerroin, joka varmistaa murikoiden lähtevän eri suuntiin erityisesti pelin alussa
    */
    public void spawnRock(int i) {
        Polygon p = new Polygon(-30 - r.nextInt(15), -30 - r.nextInt(15), -30 - r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), 30 + r.nextInt(15), -30 - r.nextInt(15));
        p.setFill(Color.LIGHTGREY);
            
        Sprite rock = new Sprite(p, r.nextInt(w), 0);
            
        rock.accelerate(-45 + (90 * (i - 1)) + r.nextInt(30), 2);
            
        this.allSprites.add(rock);
        this.rockReg.add(rock, 1);
        this.pane.getChildren().add(rock.getForm());
    }
    
    /**
     * Kutsuu satunnaisesti metodia {@link #spawnRock(int)}; todennäköisyys riippuu pelaajan pisteistä ja pelissä olevian murikoiden määrästä.
     * 
     * @param pts pelaajan pisteet
     */
    public void processRandomSpawn(int pts) {
        int p = r.nextInt(16000 - pts);

        while (p < 0) {
            p += r.nextInt(100);
        }

        if (p < 20 - (getNumberOfRocks())) {
            spawnRock(p);
        }
    }
    
    /**
     * Kertoo, montako murikkaa on pelissä.
     * 
     * @return  murikoiden määrä
     */
    public int getNumberOfRocks() {
        return this.rockReg.getRocks().size();
    }
    
    /**
     * Lisää käsiteltäväksi uuden Sprite-olion.
     * 
     * @param s lisättävä Sprite
     */
    public void addSprite(Sprite s) {
        this.allSprites.add(s);
        this.pane.getChildren().add(s.getForm());
    }
    
    /**
     * Poistaa Sprite-olion pelistä.
     * 
     * @param s poistettava Sprite
     */
    public void removeSprite(Sprite s) {
        s.destroy();
        
        this.allSprites.remove(s);
        
        if (this.rockReg.contains(s)) {
            this.rockReg.remove(s);
        }
        
        if (this.projectiles.contains(s)) {
            this.projectiles.remove(s);
        }
    }
    
    /**
     * Kutsuu avaruusaluksen metodia fire() ja lisää sen tuottaman ammuksen käsiteltävien Sprite-olioiden joukkoon.
     * 
     * @see murikat.logics.Spaceship#fire()
     */
    public void processFiring() {
        if (this.projectiles.size() >= ship.getMaxProjectiles()) {
            return;
        }
        
        Sprite projectile = this.ship.fire();
        
        this.allSprites.add(projectile);
        this.projectiles.add(projectile);
        
        this.pane.getChildren().add(projectile.getForm());
    }
    
    /**
     * Käsittelee pelissä olevien Sprite-olioiden liikkeen.
     * <p>
     * Metodi käy läpi kaikki pelissä olevat Spritet ja selvittää jokaisen sijainnin sekä nopeuden.
     * Lisäämällä nopeusvektorin x-komponentin sijainnin x-koordinaattiin ja y-komponentin vastaavasti y-koordinaattiin metodi määrittää kullekin Spritelle uuden sijainnin,
     * jonka koordinaatit annetaan parametreiksi Sprite-olion metodille moveTo(x, y).
     * </p>
     * Mikäli jonkin Sprite-olion sijainti siirtyy liikkeen seurauksena pelialueen ulkopuolelle, käsitellään tilanne kutsumalla metodia {@link #wrap(murikat.logics.Sprite, double, double)},
     * jonka parametreiksi annetaan kyseinen Sprite sekä koordinaatit pelialueen vastakkaiselta laidalta.
     * 
     * @see murikat.logics.Sprite#moveTo(double, double)
     */
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
    
    /**
     * Tutkii, onko alus tai ammus osunut murikkaan, ja jälkimmäisessä tapauksessa kutsuu murikan halkaisevaa metodia {@link #sunder(murikat.logics.Sprite)}.
     * 
     */
    public void processCollisions() {
        this.hitScored = 0;
        HashSet<Sprite> allRocks = this.rockReg.getRocks();
        
        allRocks.forEach(rock -> {
            if (rock.getForm().getBoundsInParent().contains(this.shipSprite.getPositionX(), this.shipSprite.getPositionY())) {
                ship.collide();
            }
            
            this.projectiles.forEach(projectile -> {
                if (rock.getForm().getBoundsInParent().contains(projectile.getPositionX(), projectile.getPositionY())) {
                    this.hitScored = this.rockReg.getGeneration(rock);
                    this.rockReg.setHitRock(rock);
                        
                    projectile.setHitPts(projectile.getHitPts() - 1);
                    rock.setHitPts(rock.getHitPts() - 1);            
                    
                    if (this.rockReg.getGeneration(rock) < 3) {
                        sunder(rock);
                    }
                }
            });
        });
    }
    
    public int getHitScored() {
        return this.hitScored;
    }
    
    /**
     * Tuhoaa parametrina annetun murikan ja synnyttää kaksi pienempää murikkaa sen tilalle.
     * 
     * @param rock tuhottava murikka
     */
    public void sunder(Sprite rock) {
        int g = rockReg.getGeneration(rock) + 1;
        
        Polygon p = new Polygon(-30 / g - r.nextInt(18 / g), -30 / g - r.nextInt(18 / g), -30 / g - r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), -30 / g - r.nextInt(18 / g));
        p.setFill(Color.LIGHTGRAY);
        
        Polygon q = new Polygon(-30 / g - r.nextInt(18 / g), -30 / g - r.nextInt(18 / g), -30 / g - r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), 30 / g + r.nextInt(18 / g), -30 / g - r.nextInt(18 / g));
        q.setFill(Color.LIGHTGRAY);

        Sprite oneHalf = rock.emitProjectile(p, 15 + r.nextInt(60), 2 * g);
        Sprite otherHalf = rock.emitProjectile(q, -165  + r.nextInt(60), 2 * g);

        this.rockReg.add(oneHalf, g);
        this.rockReg.add(otherHalf, g);
        addSprite(oneHalf);
        addSprite(otherHalf);

        this.rockReg.remove(rock);
    }
    
    /**
     * Tuhoaa pelialueen ulkopuolelle joutuneen Spriten (ammukset) tai siirtää sen parametreina saatuun uuteen sijaintiin (muut).
     * 
     * @param s käsiteltävä Sprite
     * @param x mahdollisen uuden sijainnin x-koordinaatti
     * @param y mahdollisen uuden sijainnin y-koordinaatti
     */
    public void wrap(Sprite s, double x, double y) {
        if (this.projectiles.contains(s)) {
            s.destroy();
        } else {
            s.moveTo(x, y);
        }
    }
    
    /**
     * Poistaa tuhoutuneet Sprite-oliot pelistä ja kertoo, onko pelaajan alus yhä olemassa.
     * 
     * @see murikat.logics.Sprite#destroy()
     * 
     * @return false, jos pelaajan alus on tuhoutunut, muuten true
     */
    public Boolean processDestruction() {
        this.allSprites.forEach(sprite -> {
            if (sprite.getHitPts() < 1) {
                sprite.destroy();
            }
        });
        
        if (this.shipSprite.isDestroyed()) {
            return false;
        }
        
        this.allSprites.removeIf(s -> s.isDestroyed());
        this.projectiles.removeIf(s -> s.isDestroyed());
        this.rockReg.remove(this.rockReg.getHitRock());
        
        return true;
    }
}