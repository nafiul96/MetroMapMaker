/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import map.data.Station;

/**
 *
 * @author nafi
 */
public class StationRadiusChange_Transaction implements jTPS_Transaction{
    
    Station station;
    double old;
    double newCol;

    public StationRadiusChange_Transaction(Station station, double old, double newCol) {
        this.station = station;
        this.old = old;
        this.newCol = newCol;
    }

    
    
    
    
    
    
    @Override
    public void doTransaction() {
        station.setCenterX(newCol);
        station.setCenterY(newCol);
    }

    @Override
    public void undoTransaction() {
        
       station.setCenterX(old);
        station.setCenterY(old);
    }
}
