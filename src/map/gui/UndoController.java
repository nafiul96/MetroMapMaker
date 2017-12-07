/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import jtps.jTPS;
import map.trans.History;

/**
 *
 * @author nafi
 */
public class UndoController {
    private AppTemplate app;
    
    public UndoController(AppTemplate initApp) {
        app = initApp;
    }
    
    public void processUndoRequest() {
        jTPS tps = History.getTps();
        tps.undoTransaction();
        app.getGUI().updateToolbarControls(false);
    }
    
    public void processRedoRequest() {
        jTPS tps = History.getTps();
        tps.doTransaction();
        app.getGUI().updateToolbarControls(false);
    }    
}
