/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import javafx.scene.Node;
import jtps.jTPS_Transaction;
import map.data.MapData;

/**
 *
 * @author nafi
 */
public class AddNode_Transaction implements jTPS_Transaction {
    private MapData data;
    private Node node;
    
    public AddNode_Transaction(MapData initData, Node initNode) {
        data = initData;
        node = initNode;
    }

    @Override
    public void doTransaction() {
        data.getShapes().add(node);
    }

    @Override
    public void undoTransaction() {
        data.getShapes().remove(node);
    }
}
