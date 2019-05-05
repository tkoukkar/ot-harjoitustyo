/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murikat.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkoukkar
 */
public class HighScoresTest {
    HighScoreDao dao;
    
    static String testDb;
    
    public HighScoresTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        testDb = properties.getProperty("scoreTestDb");
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        dao = new HighScoreDao(testDb);
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
        
        assertEquals(2000, score);
    }
    
    @Test
    public void refreshTestNoDoubletsCreated() throws SQLException {
        dao.loadData();

        int s = dao.getNames().size();
        
        dao.refresh();
        
        assertEquals(s, dao.getNames().size());
    }
    
    @Test
    public void getLastNumberOnListReturnsZeroIfListNotFull() throws SQLException {
        dao.loadData();
        
        assertEquals(dao.getLast(), 0);
    }
    
    @Test
    public void getLastNumberOnListReturnsNineteenthIfListIsFull() throws SQLException {
        for (int i = 1; i < 42; i++) {
            dao.getScores().add(i);
        }
        
        assertEquals(dao.getLast(), 19);
    }
}
