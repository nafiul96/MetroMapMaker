/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;

/**
 *
 * @author nafi
 */
public class BackgroundImage_Transaction implements jTPS_Transaction{

    Background old;
    Background newBack;
    AppTemplate app;
    Pane pane;
    public BackgroundImage_Transaction(Background newBack, Pane pane) {
        this.old = new Background(pane.getBackground().getFills().get(0));
        this.newBack = newBack;
        this.app = app;
        this.pane = pane;
    }
    
    
    
    @Override
    public void doTransaction() {
        
        pane.setBackground(newBack);
    }

    @Override
    public void undoTransaction() {
        pane.setBackground(old);
    }
    
}
