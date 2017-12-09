/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import jtps.jTPS_Transaction;
import map.data.Station;

/**
 *
 * @author nafi
 */
public class RotateLabel_Transaction implements jTPS_Transaction{

    Station station;
   
    public RotateLabel_Transaction(Station station) {
        this.station = station;
        
    }
    
    
    
    
    @Override
    public void doTransaction() {
        
        station.rotateLabel();
    }

    @Override
    public void undoTransaction() {
        
        station.rotateLabel();
    }
    
}
