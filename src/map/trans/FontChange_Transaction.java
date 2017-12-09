/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author nafi
 */
public class FontChange_Transaction implements jTPS_Transaction{

    Text txt;
    Font oldFont;
    Font newFont;

    public FontChange_Transaction(Text txt, Font oldFont, Font newFont) {
        this.txt = txt;
        this.oldFont = oldFont;
        this.newFont = newFont;
    }
    
    
    
    
    @Override
    public void doTransaction() {
        
        txt.setFont(newFont);
    }

    @Override
    public void undoTransaction() {
        
        txt.setFont(oldFont);
    }
    
}
