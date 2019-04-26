package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 * Liikkuvaa kappaletta edustava luokka
 * @author tkoukkar
 */
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
    
    public double getPositionX() {
        return this.form.getTranslateX();
    }

    public double getPositionY() {
        return this.form.getTranslateY();
    }
    
    public Point2D getVelocity() {
        return this.velocity;
    }
    
    public void setVelocity(Point2D v) {
        this.velocity = v;
    }
    
    /**
     * Kääntää kappaletta ruudulla esittävää monikulmiota.
     * <p>
     * Metodi lisää kappaletta esittävän monikulmion nykyiseen rotaatioon parametrina saadun määrän asteita.
     * Parametrin positiivisilla arvoilla kappale kääntyy myötäpäivään ja negatiivisilla vastapäivään.
     * </p>
     * 
     * @param deg käännöksen suuruus asteina
     */
    public void rotate(double deg) {
        this.form.setRotate(this.form.getRotate() + deg);
    }
    
    /**
     * Siirtää kappaletta ruudulla esittävän monikulmion parametreina annettujen koordinaattien mukaiseen sijaintiin.
     * 
     * @param x uuden sijainnin x-koordinaatti
     * @param y uuden sijainnin y-koordinaatti
     */
    public void moveTo(double x, double y) {
        this.form.setTranslateX(x);
        this.form.setTranslateY(y);
    }
    
    /**
     * Kiihdyttää kappaleen liikettä parametrina annettuun suuntaan toisena parametrina annetun nopeusvektorin verran.
     * 
     * @param dir kiihdytyssuunta
     * @param v kiihdytyksen suuruus
     */
    public void accelerate(double dir, double v) {
        double xComp = Math.cos(Math.toRadians(this.form.getRotate() + dir)) * v;
        double yComp = Math.sin(Math.toRadians(this.form.getRotate() + dir)) * v;
        
        this.velocity = this.velocity.add(xComp, yComp);
    }
    
    /**
     * Luo uuden kappaleen (Sprite-olion) tämän kappaleen sijaintiin ja asettaa sille saatujen parametrien mukaisen lähtönopeuden. Palauttaa luodun kappaleen.
     * 
     * @param p uutta kappaletta vastaava monikulmio
     * @param dir suunta, johon uusi kappale lähtee
     * @param v nopeus, jolla uusi kappale lähtee liikkeelle
     * @return luotu kappale
     */
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
    
    /**
     * Merkitsee kappaleen tuhotuksi ja poistaa sitä esittävän monikulmion ruudulta.
     */
    public void destroy() {
        this.getForm().setVisible(false);
        this.destroyed = true;
    }
    
    public Boolean isDestroyed() {
        return this.destroyed;
    }
}