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
public class StationFillChange_Transaction implements jTPS_Transaction{

    Station station;
    Color old;
    Color newCol;

    public StationFillChange_Transaction(Station station, Color old, Color newCol) {
        this.station = station;
        this.old = old;
        this.newCol = newCol;
    }
    
    
    
    
    
    @Override
    public void doTransaction() {
        station.setFill(newCol);
    }

    @Override
    public void undoTransaction() {
        
        station.setFill(old);
    }
    
}
