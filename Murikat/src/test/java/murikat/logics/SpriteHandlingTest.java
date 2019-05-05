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
import murikat.gui.MurikatUi;
import murikat.dao.SpaceshipDao;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkoukkar
 */
public class SpriteHandlingTest {
    SpriteHandler sph;
    Spaceship ship;
    
    int w;
    int h;    
    
    static String tShipDat;
    
    public SpriteHandlingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        tShipDat = properties.getProperty("testShip");
    }
    
    @Before
    public void setUp() {
        w = MurikatUi.w;
        h = MurikatUi.h;
        
        Pane testPane = new Pane();
        testPane.setPrefSize(w, h);
        
        ship = new Spaceship(new SpaceshipDao(tShipDat), w / 2, h / 2);
        
        sph = new SpriteHandler(testPane, ship);
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
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
    public void collisionProcessingTestHitScoredEqualsGenerationOfHitRock() {
        int q = 0;
        ship.getSprite().getForm().setRotate(0);
        
        sph.spawnRock(1);
        
        for (int i = 0; i <= w; i++) {
            ship.getSprite().moveTo(i, 1);
            sph.processFiring();
            sph.processCollisions();
            
            q = sph.getHitScored();
            
            if (q != 0) {
                break;
            }
        }
        
        assertEquals(1, q);
    }
    
    @Test
    public void collisionProcessingTestHitScoredIsZeroWhenNoCollisionOccurs() {
        int q = 0;
        ship.getSprite().getForm().setRotate(0);
        
        sph.spawnRock(1);
    
        for (int i = 0; i <= w; i++) {
            ship.getSprite().moveTo(i, h / 2);
            sph.processFiring();
            sph.processCollisions();
            
            q = sph.getHitScored();
            
            if (q != 0) {
                break;
            }
        }
        
        assertEquals(0, q);
    }
    
    @Test
    public void collisionProcessingTestRockBreaksInTwoWhenHit() {
        int q = 0;
        ship.getSprite().getForm().setRotate(0);
        
        sph.spawnRock(1);
        
        for (int i = 0; i <= w; i++) {
            ship.getSprite().moveTo(i, 1);
            sph.processFiring();
            sph.processCollisions();
            
            q = sph.getHitScored();
            
            if (q != 0) {
                break;
            }
        }
        
        assertEquals(2, sph.getNumberOfRocks());
    }
    
    @Test
    public void collisionProcessingTestShipLosesShieldsWhenHit() {
        ship.getSprite().getForm().setRotate(0);
        
        sph.spawnRock(1);
        
        for (int i = 0; i <= w; i++) {
            ship.getSprite().moveTo(i, 1);
            sph.processCollisions();
        }
        
        assertEquals(2, ship.getShields());
    }
    
    @Test
    public void destructionProcessingTestReturnsTrueIfShipIntact() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.addSprite(s);
        
        s.setHitPts(0);
        
        assertTrue(sph.processDestruction());
    }
    
    @Test
    public void destructionProcessingTestReturnsFalseeIfShipDestroyed() {
        ship.getSprite().setHitPts(0);
        
        assertFalse(sph.processDestruction());
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
    public void destructionProcessingTestSpriteWithNonZeroHpNotDestroyed() {
        Polygon p = new Polygon(-8, -8, 24, 0, -8, 8);
        Sprite s = new Sprite(p, w / 2, h / 2);
        
        sph.addSprite(s);
        
        s.setHitPts(2);
        
        sph.processDestruction();
                
        assertFalse(s.isDestroyed());
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
    
    @Test
    public void randomSpawningTestWithFullProbabilityNumberOfRocksIncreased() {
        sph.processRandomSpawn(15999);
        sph.processRandomSpawn(15999);
        sph.processRandomSpawn(15999);
        
        assertEquals(3, sph.getNumberOfRocks());        
    }
}

