/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import map.data.TrainLine;
import map.gui.MapWorkspace;

/**
 *
 * @author nafi
 */
public class LineEdit_Transaction implements jTPS_Transaction{
    
    TrainLine line;
    String oldName;
    Color oldColor;
    
    String newName;
    Color newColor;
    AppTemplate app;
    
    public LineEdit_Transaction(AppTemplate app,TrainLine line, String oldName, Color oldColor, String newName, Color newColor) {
        this.line = line;
        this.oldName = oldName;
        this.oldColor = oldColor;
        this.newName = newName;
        this.newColor = newColor;
        this.app = app;
    }
    
    

    @Override
    public void doTransaction() {
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        
        
        line.setName(newName);
        line.setStroke(newColor);
        int k = space.getLineList().getSelectionModel().getSelectedIndex();
                space.getLineList().getItems().add(k,newName);
                space.getLineList().getItems().remove(k+1);
        space.getLineList().getSelectionModel().select(newName);
    }

    @Override
    public void undoTransaction() {
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();    
        line.setName(oldName);
        line.setStroke(oldColor);
        int k = space.getLineList().getSelectionModel().getSelectedIndex();
                space.getLineList().getItems().add(k,oldName);
                space.getLineList().getItems().remove(k+1);
        space.getLineList().getSelectionModel().select(oldName);
    }
    
    
    
    
}
