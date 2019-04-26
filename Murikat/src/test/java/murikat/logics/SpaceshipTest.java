package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.dao.SpaceshipDao;

import org.junit.After;
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
    
    public SpaceshipTest() {
    }
    
    /* @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    } */
    
    @Before
    public void setUp() {
        w = 200;
        h = 200;
        
        ship = new Spaceship(new SpaceshipDao("data/spaceship.dat"), w / 2, h / 2);
    }
    
    @After
    public void tearDown() {
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
}
