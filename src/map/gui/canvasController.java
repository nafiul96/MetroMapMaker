/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.scene.text.Text;
import map.data.MapData;
import static map.data.MapState.remove_shape;
import static map.data.MapState.selecting_shape;
import static map.data.MapState.sizing_shape;
import static map.data.MapState.starting_line;
import static map.data.MapState.starting_station;

/**
 *
 * @author nafi
 */
public class canvasController {
    
    
    AppTemplate app;

    public canvasController(AppTemplate app) {
        this.app = app;
    }
    
    void processMousePress(int x, int y){
    
    
        MapData  data = (MapData)app.getDataComponent();
        
        if(data.getState() == starting_line){
        
            data.startNewLine(x, y, new Text("Test"));
            data.setState(selecting_shape);
        }else if(data.getState() == starting_station){
        
            data.startNewStation(x, y, new Text("test"));
            data.setState(selecting_shape);
        }else if(data.getState() == selecting_shape){
        
            data.selectShape(x, y);
        }else if(data.getState() == remove_shape){
        
            data.selectShape(x, y);
            data.removeSelection();
            data.setState(selecting_shape);
        }
    }
    
}
