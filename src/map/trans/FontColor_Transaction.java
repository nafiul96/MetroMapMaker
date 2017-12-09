/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author nafi
 */
public class FontColor_Transaction implements jTPS_Transaction{
    
    Text txt;
    Color oldColor;
    Color newColor;

    public FontColor_Transaction(Text txt, Color oldColor, Color newColor) {
        this.txt = txt;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void doTransaction() {
        txt.setFill(newColor);
    }

    @Override
    public void undoTransaction() {
        txt.setFill(oldColor);
               
    }
    
    
}
