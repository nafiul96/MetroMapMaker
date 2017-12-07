/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import map.data.MapData;
import map.data.MapState;
import map.data.Station;

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
        
            temp.moveLabel();
        }
    }
    
    void processRotateLabel(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        String name = (String)space.getStationList().getValue();
        Station temp = data.getStation().get(name);
        
        if(temp != null){
        
            temp.rotateLabel();
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
