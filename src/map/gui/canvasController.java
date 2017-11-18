/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import map.data.MapData;
import static map.data.MapState.starting_line;

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
        }
    }
    
}
