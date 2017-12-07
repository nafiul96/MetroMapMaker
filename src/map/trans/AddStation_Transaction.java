/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import jtps.jTPS_Transaction;
import map.data.MapData;
import map.data.Station;

/**
 *
 * @author nafi
 */
public class AddStation_Transaction implements jTPS_Transaction{

    MapData data;
    Station node;

    public AddStation_Transaction(MapData data, Station node) {
        this.data = data;
        this.node = node;
    }
    
    
    
    
    
    
    @Override
    public void doTransaction() {
        data.addStation(node);
    }

    @Override
    public void undoTransaction() {
        
        data.removeStation(node);
    }
    
}
