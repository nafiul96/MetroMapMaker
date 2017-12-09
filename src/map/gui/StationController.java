/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import jtps.jTPS;
import map.data.MapData;
import map.data.MapState;
import map.data.Station;
import map.trans.History;
import map.trans.PositioningLabel_Transaction;
import map.trans.RotateLabel_Transaction;

/**
 *
 * @author nafi
 */
public class StationController {
    
    AppTemplate app;
    
    MapData data;

    public StationController(AppTemplate app) {
        this.app = app;
        data = (MapData)app.getDataComponent();
    }
    
    void processAddStation(){
    
        data.setState(MapState.starting_station);
    }
    
    
    
    
    
    
    void processRemoveStation(){
    
        data.setState(MapState.remove_station);
    }
    
    void processMoveLabel(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        String name = (String)space.getStationList().getValue();
        Station temp = data.getStation().get(name);
        if(temp != null){
        
            jTPS tps = History.getTps();
            //MapData data = (MapData)app.getDataComponent();
            PositioningLabel_Transaction newTransaction = new PositioningLabel_Transaction(temp);
            tps.addTransaction(newTransaction);
            
           // temp.moveLabel();
        }
    }
    
    void processRotateLabel(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        String name = (String)space.getStationList().getValue();
        Station temp = data.getStation().get(name);
        
        if(temp != null){
        
            
            jTPS tps = History.getTps();
            //MapData data = (MapData)app.getDataComponent();
            RotateLabel_Transaction newTransaction = new RotateLabel_Transaction(temp);
            tps.addTransaction(newTransaction);
            
           // temp.rotateLabel();
        }
        
     
        
    }
    
    void processSelectStation(){
    
        
    }
    
    
    void processChangeFillColor(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        String name = (String)space.getStationList().getValue();
        Station temp = data.getStation().get(name);
        if(temp != null){
        
            temp.setFill(space.getStationFillColor().getValue());
        }
    }

    void processChangeRadius() {
        
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        if(!space.getStationList().getItems().isEmpty()){
        String name = (String)space.getStationList().getValue();
        Station temp = data.getStation().get(name);
        if(temp != null){
        
            temp.setRadiusX(space.getStopThickness().getValue());
            temp.setRadiusY(space.getStopThickness().getValue());
        }
    }
    }
    
    
    
}
