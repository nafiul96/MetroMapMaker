/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import map.data.MapData;

/**
 *
 * @author nafi
 */
public class MapElementController {
    
    AppTemplate app;
    MapData data;
    
    public MapElementController(AppTemplate initApp){
    
        app = initApp;
        
        data = (MapData)app.getDataComponent();
        
    }
    
    
    public void addLine(){
    
        
    }
    
    
}
