/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import jtps.jTPS_Transaction;
import map.data.MapData;
import map.data.TrainLine;

/**
 *
 * @author nafi
 */
public class AddLine_Transaction implements jTPS_Transaction {

    MapData data;
    TrainLine line;

    public AddLine_Transaction(MapData data, TrainLine line) {
        this.data = data;
        this.line = line;
    }

    @Override
    public void doTransaction() {

        data.getShapes().addAll(line.getStartText(), line.getEndText(), line);
    }

    @Override
    public void undoTransaction() {

        data.removeLine(line.getName());
    }

}
