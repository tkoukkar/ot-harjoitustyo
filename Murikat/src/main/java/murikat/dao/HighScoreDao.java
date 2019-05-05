package murikat.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tkoukkar
 */
public class HighScoreDao {
    private Database db;
    
    private ArrayList<String> names;
    private ArrayList<Integer> scores;

    public HighScoreDao(String path) throws ClassNotFoundException {
        this.db = new Database(System.getenv("JDBC_DATABASE_URL") != null 
                                ? System.getenv("JDBC_DATABASE_URL") 
                                : path);
        
        this.names = new ArrayList<>();
        this.scores = new ArrayList<>();
    }
    
    public void loadData() throws SQLException {
        try (Connection conn = this.db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scores ORDER BY score DESC"); // {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                if (this.scores.size() < 19) {
                    this.names.add(rs.getString("name"));
                    this.scores.add(rs.getInt("score"));
                }
            }
            
            stmt.close();
        }
    }
    
    public void addNewScore(String name, int score) throws SQLException {
        try (Connection conn = this.db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Scores (name, score) VALUES (?, ?)");
            
            stmt.setString(1, name);
            stmt.setInt(2, score);
            
            stmt.executeUpdate();
            stmt.close();
        }
        
        this.names.add(name);
        this.scores.add(score);
    }
    
    public void refresh() throws SQLException {
        this.names.clear();
        this.scores.clear();
        
        loadData();
    }
    
    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }
    
    public int getLast() {
        if (this.scores.size() < 19) {
            return 0;
        }
        
        return this.scores.get(18);
    }
}
