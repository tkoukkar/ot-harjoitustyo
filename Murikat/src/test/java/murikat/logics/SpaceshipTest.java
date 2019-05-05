package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import murikat.dao.SpaceshipDao;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author tkoukkar
 */
public class SpaceshipTest {
    Spaceship ship;
    int w;
    int h;
    
    static String shipDat;
    
    public SpaceshipTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        shipDat = properties.getProperty("shipData");
    }

    @Before
    public void setUp() {
        w = 200;
        h = 200;
        
        ship = new Spaceship(new SpaceshipDao(shipDat), w / 2, h / 2);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void shipConstructorTestShipExists() {
        assertNotNull(ship);
    }
    
    @Test
    public void shipConstructorTestSpriteExists() {
        assertNotNull(ship.getSprite());
    }
    
    @Test
    public void shipConstructorTestPositionX() {
        assertEquals(w / 2, ship.getSprite().getPositionX(), 1);
    }
    
    @Test
    public void shipConstructorTestPositionY() {
        assertEquals(h / 2, ship.getSprite().getPositionY(), 1);
    }
    
    @Test
    public void shipConstructorTestShieldsSetToThree() {
        assertEquals(3, ship.getShields());
    }
    
    @Test
    public void shipConstructorTestInvulnerabilityOff() {
        assertEquals(0, ship.getInvulnerability());
    }
    
    @Test
    public void shipTestTurnLeft() {
        ship.turnLeft();
        ship.turnLeft();
        ship.turnLeft();
        ship.turnLeft();
        ship.turnLeft();
        
        assertEquals(-105, ship.getSprite().getForm().getRotate(), 0.5);
    }
    
    @Test
    public void shipTestTurnRight() {
        ship.turnRight();
        ship.turnRight();
        ship.turnRight();
        ship.turnRight();
        ship.turnRight();
        
        assertEquals(-75, ship.getSprite().getForm().getRotate(), 0.5);
    }
    
    @Test
    public void weaponsTestProjectileExists() {
        Sprite p = ship.fire();
        
        assertNotNull(p);
    }
    
    @Test
    public void weaponsTestProjectileVelocity() {
        Sprite p = ship.fire();
        
        double y = Math.sin(Math.toRadians(-90)) * 12;
        
        assertEquals(y, p.getVelocity().getY(), 0.5);
    }
    
    @Test
    public void collisionTestInvulnerabilitySetOn() {
        ship.collide();
        assertEquals(20, ship.getInvulnerability());
    }
    
    @Test
    public void collisionTestShieldsReduced() {
        ship.collide();
        assertEquals(2, ship.getShields());
    }
    
    @Test
    public void collisionTestShieldsNotReducedWhileInvulnerable() {
        ship.collide();
        ship.collide();
        ship.collide();
        
        assertEquals(2, ship.getShields());
    }
    
    @Test
    public void collisionTestInvulnerabilityExtendedIfAtOneDuringOngoingCollision() {
        ship.setInvulnerability(1);
        ship.collide();
        
        assertEquals(2, ship.getInvulnerability());
    }
}
