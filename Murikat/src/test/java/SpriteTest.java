/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.tkoukkar.murikat.logics.Sprite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;

/**
 *
 * @author tkoukkar
 */
public class SpriteTest {
    Polygon p;
    Sprite s;
    int w;
    int h;
    
    public SpriteTest() {
    }
    
    @Before
    public void setUp() {
        w = 200;
        h = 200;
        
        p = new Polygon(-8, -8, 24, 0, -8, 8);
        s = new Sprite(p, w / 2, h / 2, 0);
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
    public void spriteConstructorTestSpriteExists() {
        assertNotNull(s);
    }
    
    @Test
    public void spriteConstructorTestPositionX() {
        double x = s.getPositionX();
        
        assertEquals(100.0, x, 1.0);
    }
    
    @Test
    public void spriteConstructorTestPositionY() {
        double y = s.getPositionY();
        
        assertEquals(100.0, y, 1.0);
    }
    
    @Test
    public void spriteConstructorTestZeroVelocity() {
        Point2D v = new Point2D(0, 0);
        
        assertEquals(v, s.getVelocity());
    }
    
    @Test
    public void spriteFormTest() {
        assertEquals(p, s.getForm());
    }
    
    @Test
    public void spriteRotationTestClockwise() {
        s.rotate(45);
        
        assertEquals(s.getForm().getRotate(), 45, 1.0);
    }
    
    @Test
    public void spriteRotationTestCounterClockwise() {
        s.rotate(-45);
        
        assertEquals(s.getForm().getRotate(), -45, 1.0);
    }
    
    @Test
    public void spriteMovementTestPositionX() {
        s.moveTo(100, 100);
        
        assertEquals(s.getPositionX(), 100, 1.0);
    }
    
    @Test
    public void spriteMovementTestPositionY() {
        s.moveTo(100, 100);
        
        assertEquals(s.getPositionY(), 100, 1.0);
    }
    
    @Test
    public void spriteAccelerationTest() {
        s.accelerate(0, 1);
        
        Point2D v = new Point2D(1, 0);
        
        assertEquals(v, s.getVelocity());
    }
    

    
}