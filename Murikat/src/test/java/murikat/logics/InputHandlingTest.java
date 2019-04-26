package murikat.logics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import murikat.logics.Sprite;
import murikat.logics.Spaceship;
import murikat.logics.InputHandler;
import murikat.dao.SpaceshipDao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;

/**
 *
 * @author tkoukkar
 */
public class InputHandlingTest {
    InputHandler iph;
    Spaceship ship;
    int w;
    int h;
    
    public InputHandlingTest() {
    }
    
    @Before
    public void setUp() {
        w = 200;
        h = 200;
        
        ship = new Spaceship(new SpaceshipDao("data/spaceship.dat"), w / 2, h / 2);
        
        iph = new InputHandler(ship);
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
    public void controlsTestTurnLeft() {
        iph.input(KeyCode.LEFT);
        
        iph.processControls();
        iph.processControls();
        iph.processControls();
        iph.processControls();
        iph.processControls();
        
        assertEquals(-105, ship.getSprite().getForm().getRotate(), 0.5);
    }
    
    @Test
    public void controlsTestTurnRight() {
        iph.input(KeyCode.RIGHT);
        
        iph.processControls();
        iph.processControls();
        iph.processControls();
        iph.processControls();
        iph.processControls();
        
        assertEquals(-75, ship.getSprite().getForm().getRotate(), 0.5);
    }
    
    @Test
    public void keyReleaseTestInputRemoved() {
        iph.input(KeyCode.LEFT);
        
        iph.processControls();  // Rotation: -93
        iph.processControls();  // Rotation: -96
        iph.processControls();  // Rotation: -99
        
        iph.remove(KeyCode.LEFT);
        
        iph.processControls();
        iph.processControls();
        
        iph.input(KeyCode.RIGHT);
        
        iph.processControls();  // Rotation: -96
        
        iph.remove(KeyCode.RIGHT);
        
        iph.processControls();
        
        assertEquals(-96, ship.getSprite().getForm().getRotate(), 0.5);
    }
    
    @Test
    public void triggerStateTestIncreaseWhileHeld() {
        iph.input(KeyCode.SPACE);
        
        iph.processControls();
        iph.processControls();
        iph.processControls();
        
        assertEquals(3, iph.getTriggerState());
    }
    
    @Test
    public void triggerStateTestResetOnRelease() {
        iph.input(KeyCode.SPACE);
        
        iph.processControls();
        iph.processControls();
        iph.processControls();
        
        iph.remove(KeyCode.SPACE);
        
        assertEquals(0, iph.getTriggerState());
    }
}
