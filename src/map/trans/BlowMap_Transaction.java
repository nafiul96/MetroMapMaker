/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import map.data.MapData;

/**
 *
 * @author nafi
 */
public class BlowMap_Transaction implements jTPS_Transaction{

    double oldH;
    double oldW;
    double newH;
    double newW;
    Pane paneGroup;

    public BlowMap_Transaction(double oldH, double oldW, double newH, double newW, Pane paneGroup) {
        this.oldH = oldH;
        this.oldW = oldW;
        this.newH = newH;
        this.newW = newW;
        this.paneGroup = paneGroup;
    }
    
    
    
    
    
    
    @Override
    public void doTransaction() {
        
        paneGroup.minHeight(newH);
        paneGroup.minWidth(newW);
    }

    @Override
    public void undoTransaction() {
        
        paneGroup.minHeight(oldH);
        paneGroup.minWidth(newH);
    }
}
