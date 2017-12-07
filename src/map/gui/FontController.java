/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import map.data.MapData;

/**
 *
 * @author nafi
 */
public class FontController {
    
    
    AppTemplate app;
    MapData data;

    public FontController(AppTemplate app) {
        this.app = app;
        
        data = (MapData)app.getDataComponent();
    }
    
    
    void changeTextColor(){
    
        if (data.isTextSelected()) {
            Text selectedText = (Text)data.getSelectedShape();
            MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
            selectedText.setFill(space.getFontColor().getValue());
        }
        
        
    }
    
    void changeTextFont(){
    
        if (data.isTextSelected()) {
            Text selectedText = (Text)data.getSelectedShape();
            MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
            Font currentFont = workspace.fontSettings();
            selectedText.setFont(currentFont);
        }
    }
    
    
    
    
    
}
