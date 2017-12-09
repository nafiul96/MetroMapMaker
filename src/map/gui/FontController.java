/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import jtps.jTPS;
import map.data.MapData;
import map.trans.FontChange_Transaction;
import map.trans.FontColor_Transaction;
import map.trans.History;

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
            
            jTPS tps = History.getTps();
//MapData data = (MapData)app.getDataComponent();
FontColor_Transaction newTransaction = new FontColor_Transaction(selectedText,(Color)selectedText.getFill(), space.getFontColor().getValue());
tps.addTransaction(newTransaction);
            
            
           // selectedText.setFill(space.getFontColor().getValue());
        }
        
        
    }
    
    void changeTextFont(){
    
        if (data.isTextSelected()) {
            Text selectedText = (Text)data.getSelectedShape();
            MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
            Font currentFont = workspace.fontSettings();
            
            jTPS tps = History.getTps();
//MapData data = (MapData)app.getDataComponent();
FontChange_Transaction newTransaction = new FontChange_Transaction(selectedText, selectedText.getFont(), currentFont);
tps.addTransaction(newTransaction);
            
            
          //  selectedText.setFont(currentFont);
        }
    }
    
    
    
    
    
}
