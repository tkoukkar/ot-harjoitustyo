package murikat.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tkoukkar
 */
public class SpaceshipDao {
    private double thrust;
    private double wpnPower;
    private int length;
    private int semispan;
    
    private File file;
    
    public SpaceshipDao(String filename) {
        this.file = new File(filename);
        try {
            Scanner reader = new Scanner(this.file);
            while (reader.hasNextLine()) {
                String[] shipData = reader.nextLine().split(" ");
                this.thrust = Double.parseDouble(shipData[1]);
                this.wpnPower = Double.parseDouble(shipData[2]);
                this.length = Integer.parseInt(shipData[3]);
                this.semispan = Integer.parseInt(shipData[4]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpaceshipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getThrust() {
        return thrust;
    }

    public double getWpnPower() {
        return wpnPower;
    }

    public int getLength() {
        return length;
    }

    public int getSemispan() {
        return semispan;
    }
}
