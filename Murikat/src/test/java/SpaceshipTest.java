/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.logics.Sprite;
import murikat.logics.Spaceship;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javafx.stage.Stage;
import javafx.scene.shape.Polygon;

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
        
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite shipSprite = new Sprite(p, w / 2, h / 2);
        ship = new Spaceship(shipSprite);
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
    public void shipTestSpriteExists() {
        assertNotNull(ship.getSprite());
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
