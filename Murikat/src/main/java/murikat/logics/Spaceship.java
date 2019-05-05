package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.dao.SpaceshipDao;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;

/**
 * Pelaajan avaruusalusta edustava luokka
 * @author tkoukkar
 */
public class Spaceship {
    private double thrust;
    private double wpnPower;
    private int maxProjectiles;
    private int shields;
    
    private Sprite sprite;
    
    private SpaceshipDao shipDao;
    
    private int invulnerability;
    
    public Spaceship(SpaceshipDao shipDao, int x, int y) {
        this.shipDao = shipDao;
        
        int l = this.shipDao.getLength();
        int s = this.shipDao.getSemispan();
        
        Polygon hull = new Polygon(-s, -s, l, 0, -s, s);
        hull.setFill(Color.WHITE);
        
        this.sprite = new Sprite(hull, x, y);
        
        this.sprite.rotate(-90);
        
        this.thrust = shipDao.getThrust();
        this.wpnPower = shipDao.getWpnPower();
        this.maxProjectiles = shipDao.getMaxProjectiles();
        this.shields = 3;
        
        this.sprite.setHitPts(this.shields);
        
        this.invulnerability = 0;
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    /**
     * Kääntää alusta vasemmalle.
     * <p>
     * Metodi kutsuu alusta vastaavan Sprite-olion metodia rotate(double), jonka parametriksi annetaan käännöksen suuruus asteina (nykyisessä toteutuksessa -3).
     * Vasemmalle kääntymistä vastaa alusta esittävän monikulmion kääntäminen vastapäivään, joten parametrin etumerkki on negatiivinen.
     * </p>
     * 
     * @see murikat.logics.Sprite#rotate(double)
     */
    public void turnLeft() {
        this.sprite.rotate(-3);
    }
    
     /**
     * Kääntää alusta vasemmalle.
     * <p>
     * Metodi kutsuu alusta vastaavan Sprite-olion metodia rotate(double), jonka parametriksi annetaan käännöksen suuruus asteina (nykyisessä toteutuksessa 3).
     * Oikealle kääntymistä vastaa alusta esittävän monikulmion kääntäminen myötäpäivään, joten parametrin etumerkki on positiivinen.
     * </p>
     * 
     * @see murikat.logics.Sprite#rotate(double)
     */
    public void turnRight() {
        this.sprite.rotate(3);
    }
    
    /**
     * Lisää aluksen kiihtyvyyttä sen keulan osoittamaan suuntaan.
     * <p>
     * Metodi hakee aluksen nykyisen nopeuden kutsumalla sitä vastaavan Sprite-olion metodia getVelocity().
     * Nykyiseen nopeuteen lisätään aluksen moottorien työntövoiman mukaiset x- ja y-komponentit sen keulan osoittamaan suuntaan,
     * ja näin saatu nopeus asetetaan aluksen nopeudeksi kutsumalla sitä vastaavan Sprite-olion metodia setVelocity(Point2D v).
     * </p>
     * 
     * @see murikat.logics.Sprite#getVelocity()
     * @see murikat.logics.Sprite#setVelocity(javafx.geometry.Point2D)
     */
    public void accelerate() {
        Point2D v = this.sprite.getVelocity();
        
        double xComp = Math.cos(Math.toRadians(this.sprite.getForm().getRotate())) * this.thrust;
        double yComp = Math.sin(Math.toRadians(this.sprite.getForm().getRotate())) * this.thrust;
        
        v = v.add(xComp, yComp);
        
        this.sprite.setVelocity(v);
    }
    
    /**
     * Luo ammuksen ja asettaa sille aluksen aseen tehoa vastaavan nopeuden aluksen keulan osoittamaan suuntaan.
     * 
     * @return ammus
     */
    public Sprite fire() {        
        Polygon p = new Polygon(-2, 0, 0, 2, 2, 0, 0, -2);
        p.setFill(Color.AZURE);
        
        Sprite projectile = this.sprite.emitProjectile(p, 0, this.wpnPower);
        
        return projectile;
    }
    
    /**
     * Käsittelee tilanteen, jossa alus törmää murikkaan.
     * 
     * Jos alus on tällä hetkellä haavoittumattomassa tilassa, mitään ei tapahdu.
     * Mikäli törmäyksen aiheuttama haavoittumattomuus uhkaa loppua ennen törmäyksen päättymistä (jäljellä oleva arvo 1), sitä jatketaan, jottei samaa törmäystä käsitellä kahdesti.
     * 
     */
    public void collide() {
        if (getInvulnerability() == 1) {
            setInvulnerability(2);
            return;
        } else if (getInvulnerability() > 0) {
            return;
        }
        
        this.getSprite().getForm().setFill(Color.RED);
        this.shields--;
        this.sprite.setHitPts(this.sprite.getHitPts() - 1);
        setInvulnerability(20);
    }

    public int getMaxProjectiles() {
        return maxProjectiles;
    }
    
    public int getShields() {
        return this.shields;
    }

    public int getInvulnerability() {
        return invulnerability;
    }

    public void setInvulnerability(int t) {
        if (t == 0) {
            this.getSprite().getForm().setFill(Color.WHITE);
        }
        
        this.invulnerability = t;
    }
}
