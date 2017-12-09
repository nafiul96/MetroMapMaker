/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import jtps.jTPS_Transaction;
import map.data.MapData;
import map.data.Station;
import map.data.TrainLine;

/**
 *
 * @author nafi
 */
public class AddStationToLine_Transaction implements jTPS_Transaction{

    Station toAdd;
    TrainLine givenLine;
    MapData data;
    AppTemplate app;
     public AddStationToLine_Transaction(Station toAdd, TrainLine givenLine, AppTemplate app) {
        this.toAdd = toAdd;
        this.givenLine = givenLine;
        this.data = (MapData)app.getDataComponent();
    }
    
    
    
    @Override
    public void doTransaction() {
        
        givenLine.addStop(toAdd.getName().toString(), toAdd);
        toAdd.addLine(givenLine.getName(), givenLine);
    }

   

    @Override
    public void undoTransaction() {
       
        givenLine.removeStationFromLine((int)toAdd.getCenterX(), (int)toAdd.getCenterY());
    }
    
}
