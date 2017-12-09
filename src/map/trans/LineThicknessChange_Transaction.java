/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import jtps.jTPS_Transaction;
import map.data.TrainLine;
import map.gui.MapWorkspace;

/**
 *
 * @author nafi
 */
public class LineThicknessChange_Transaction implements jTPS_Transaction{

    TrainLine line;
    double oldStroke;
    double newStroke;
    AppTemplate app;

    public LineThicknessChange_Transaction(AppTemplate app,TrainLine line, double oldStroke, double newStroke) {
        this.line = line;
        this.oldStroke = oldStroke;
        this.newStroke = newStroke;
        this.app = app;
    }
    
    
    
    
    
    
    @Override
    public void doTransaction() {
        
        line.setStrokeWidth(newStroke);
    }

    @Override
    public void undoTransaction() {
        
        MapWorkspace sp = (MapWorkspace)app.getWorkspaceComponent();
        sp.getLineThickness().setValue(oldStroke);
        line.setStrokeWidth(oldStroke);
        
    }
    
}
