/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

/**
 *
 * @author nafi
 */
public class BackgroundGradient_Transaction implements jTPS_Transaction{

   private Pane pane;
    private Color color;
    private Color oldColor;
    
    public BackgroundGradient_Transaction(Pane initPane, Color initColor) {
        pane = initPane;
        color = initColor;
        oldColor = (Color)pane.getBackground().getFills().get(0).getFill();
    }

    @Override
    public void doTransaction() {
        BackgroundFill fill = new BackgroundFill(color, null, null);
	Background background = new Background(fill);
        pane.setBackground(background);
    }

    @Override
    public void undoTransaction() {
        BackgroundFill fill = new BackgroundFill(oldColor, null, null);
	Background background = new Background(fill);
        pane.setBackground(background);    
    }    
    
}
