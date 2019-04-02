/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javafx.stage.Stage;

/**
 *
 * @author tkoukkar
 */
public class MurikatTest {
    Spaceship s;
    int w;
    int h;
    
    public MurikatTest() {
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
        s = new Spaceship(w / 2, h / 2);
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
        assertNotNull(s);
    }
    
    @Test
    public void shipConstructorTestPositionX() {
        double x = s.getSprite().getTranslateX();
        
        assertEquals(100.0, x, 1.0);
    }
    
    @Test
    public void shipConstructorTestPositionY() {
        double y = s.getSprite().getTranslateX();
        
        assertEquals(100.0, y, 1.0);
    }
}
