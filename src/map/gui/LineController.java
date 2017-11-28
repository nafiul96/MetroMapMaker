/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import map.data.MapData;
import map.data.MapState;

/**
 *
 * @author nafi
 */
public class LineController {
    
    //MapState state;
    
    AppTemplate app;
    MapData data;
    
    public LineController(AppTemplate initApp){
    
        app = initApp;
        
    }
    
    
    void processAddLine(){
    
        
            data = (MapData)app.getDataComponent();
            data.setState(MapState.starting_line);
            
            
        
        
        
    }
    
    
    void processRemoveLine(){
    
        data.setState(MapState.remove_shape);
    }
    
    
    void processAddStationToLine(){
    
        
    }
    
    
    void processRemoveStationFromLine(){
    
        
    }
    
    
    void processListAllStation(){
    
        
    }
    
    
}
