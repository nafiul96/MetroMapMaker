/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import map.data.MapData;
import static map.data.MapState.add_station_mode;
import static map.data.MapState.deleting_line;
import static map.data.MapState.dragging_line;
import static map.data.MapState.remove_shape;
import static map.data.MapState.remove_station_mode;
import static map.data.MapState.selecting_shape;
import static map.data.MapState.sizing_line;
import static map.data.MapState.sizing_shape;
import static map.data.MapState.starting_line;
import static map.data.MapState.starting_station;
import map.data.TrainLine;

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
        
            data.startNewLine(x, y);
           // data.setState(sizing_line);
           
        }else if(data.getState() == starting_station){
        
            data.startNewStation(x, y, new Text(textGetter()));
            data.setState(selecting_shape);
        }else if(data.getState() == selecting_shape){
        
            Node node = data.selectTopNode(x, y);
        }else if(data.getState() == remove_shape){
            Node node = data.selectTopNode(x, y);
            data.removeSelection();
            data.setState(selecting_shape);
        }else if(data.getState() == deleting_line){
        
            data.deleteLine(x,y);
        }else if(data.getState() == add_station_mode){
        
            data.processAddStationToLine(x,y);
        }else if(data.getState() == remove_station_mode){
        
            data.processRemoveStationFromLine(x,y);
        }
        
        app.getGUI().updateToolbarControls(false);
    }
    
    public void mouseDrag(int x, int y){
    
        MapData  data = (MapData)app.getDataComponent();
        
        if(data.getState() == sizing_line){
        
            TrainLine line = (TrainLine)data.getNewShape();
            line.size(x, y);
        }else if(data.getState() == dragging_line){
            
            TrainLine line = (TrainLine)data.getNewShape();
            //line.drag(x, y);
            
        }
        
        app.getGUI().updateToolbarControls(false);
    }
    
    public void mouseRelease(int x, int y){
    
        MapData data = (MapData)app.getDataComponent();
        
        if(data.getState() == sizing_line){
        
            data.setState(dragging_line);
        }
        
        app.getGUI().updateToolbarControls(false);
    }
    
    
    
    public String textGetter(){
    
        TextInputDialog dial = new TextInputDialog("");
        dial.setTitle("New Text Window");
        dial.setHeaderText("Please Enter Your Text Below: ");
        Optional<String> input = dial.showAndWait();
        return input.get();
    }
    
    
}