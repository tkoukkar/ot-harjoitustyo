/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murikat.dao;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkoukkar
 */
public class HighScoresTest {
    HighScoreDao dao;
    
    public HighScoresTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        dao = new HighScoreDao("jdbc:sqlite:data/test/scorestest.db");
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
    public void constructorTestNameListCreated() {
        assertNotNull(dao.getNames());
    }
    
    @Test
    public void constructorScoreListCreated() {
        assertNotNull(dao.getScores());
    }
    
    @Test
    public void dataLoadingTestNameListIsLoaded() throws SQLException {
        dao.loadData();
        
        assertEquals(3, dao.getNames().size());
    }
    
    @Test
    public void dataLoadingTestScoreListIsLoaded() throws SQLException {
        dao.loadData();
        
        assertEquals(3, dao.getScores().size());
    }
    
    @Test
    public void dataLoadingTestNameListSorted() throws SQLException {
        dao.loadData();
        
        assertEquals("test", dao.getNames().get(0));
    }
    
    @Test
    public void dataLoadingTestScoreListIsSorted() throws SQLException {
        dao.loadData();
        
        int score = dao.getScores().get(0);
        
        assertEquals(score, 2000);
    }
    
    @Test
    public void getLastNumberOnListReturnsZeroIfListNotFull() throws SQLException {
        dao.loadData();
        
        assertEquals(dao.getLast(), 0);
    }
    
    @Test
    public void getLastNumberOnListReturnsTwentiethIfListIsFull() throws SQLException {
        for (int i = 1; i < 42; i++) {
            dao.getScores().add(i);
        }
        
        assertEquals(dao.getLast(), 20);
    }
}
