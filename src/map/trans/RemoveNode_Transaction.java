/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import djf.AppTemplate;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import map.data.MapData;

/**
 *
 * @author nafi
 */
public class RemoveNode_Transaction implements jTPS_Transaction{
    
    MapData data;
    Node node;

    public RemoveNode_Transaction(Node node, AppTemplate app) {
        this.node = node;
        data = (MapData)app.getDataComponent();
    }

    
    
    
    @Override
    public void doTransaction() {
        
        data.removeNodeTransact(node);
    }

    @Override
    public void undoTransaction() {
        
        data.addNodeTransact(node);
    }
    
    
}
