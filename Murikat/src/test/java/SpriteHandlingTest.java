/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.tkoukkar.murikat.gui.MurikatUi;
import static fi.tkoukkar.murikat.gui.MurikatUi.h;
import static fi.tkoukkar.murikat.gui.MurikatUi.w;
import fi.tkoukkar.murikat.logics.Spaceship;
import fi.tkoukkar.murikat.logics.Sprite;
import fi.tkoukkar.murikat.logics.SpriteHandler;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkoukkar
 */
public class SpriteHandlingTest {
    SpriteHandler sph;
    
    int w;
    int h;    
    
    public SpriteHandlingTest() {
    }
    
    @Before
    public void setUp() {
        w = MurikatUi.w;
        h = MurikatUi.h;
        
        Pane testPane = new Pane();
        testPane.setPrefSize(w, h);
        
        sph = new SpriteHandler(testPane);
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
    public void shipBuildingTestShipExists() {
        Spaceship ship = sph.buildSpaceship();
        
        assertNotNull(ship);
    }
    
    @Test
    public void shipBuildingTestShipSpriteExists() {
        Spaceship ship = sph.buildSpaceship();
        
        assertNotNull(ship.getSprite());
    }
    
    @Test
    public void shipBuildingTestShipPositionX() {
        Spaceship ship = sph.buildSpaceship();
        
        assertEquals(w / 2, ship.getSprite().getPositionX(), 1);
    }
    
    @Test
    public void shipBuildingTestShipPositionY() {
        Spaceship ship = sph.buildSpaceship();
        
        assertEquals(h / 2, ship.getSprite().getPositionY(), 1);
    }
    
    @Test
    public void movementProcessingTestPositionX() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.addSprite(s);
        
        s.accelerate(0, 1);
        
        sph.processMovement();
        
        assertEquals(w / 2 + 1, s.getPositionX(), 0.5);
    }
    
    @Test
    public void movementProcessingTestPositionY() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        s.rotate(90);
        
        sph.addSprite(s);
        
        s.accelerate(0, 1);
        
        sph.processMovement();
        
        assertEquals(h / 2 + 1, s.getPositionY(), 0.5);
    }
    
    @Test
    public void wrappingTestHorizontal() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.addSprite(s);
        
        s.moveTo(w - 1, h / 2);
        s.accelerate(0, 2);
        
        sph.processMovement();
        assertEquals(1, s.getPositionX(), 0.5);
    }
    
    @Test
    public void wrappingTestVertical() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        s.rotate(90);
        
        sph.addSprite(s);
        
        s.moveTo(w / 2, h - 1);
        s.accelerate(0, 2);
        
        sph.processMovement();
        assertEquals(1, s.getPositionY(), 0.5);
    }
    
    @Test
    public void spriteRemovalTestSpriteDestroyed() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.removeSprite(s);
        
        assertTrue(s.isDestroyed());
    }
    
    @Test
    public void destructionProcessingTestSpriteWithZeroHpDestroyed() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.addSprite(s);
        
        s.setHitPts(0);
        
        sph.processDestruction();
        
        assertTrue(s.isDestroyed());
    }
    
    @Test
    public void rockSpawningTestNumberOfRocksIncreased() {
        sph.spawnRock(1);
        sph.spawnRock(1);
        sph.spawnRock(1);
        
        assertEquals(3, sph.getNumberOfRocks());
    }
    
    @Test
    public void rockInitializationTestFourRocksCreated() {
        sph.initRocks();
        
        assertEquals(4, sph.getNumberOfRocks());
    }
}
