/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import static map.data.MapState.starting_line;

/**
 *
 * @author nafi
 */
public class TrainLine extends Line implements MapElement {
 
    double startX, startY;
    double endX, endY;
    
    public TrainLine(){
    
       startX = 0;
       startY = 0;
       endX = 0;
       endY = 0;
       
        
    }
    
    public boolean isCircular(){
    
        return (startX == startY) && (endX == endY);
    }
    
    
    @Override
    public MapElement makeClone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapState getStartingState() {
        
        return starting_line;
    }

    @Override
    public void start(int x, int y) {
        
        startX = x;
        startY = y;
        endX = startX;
        endY = startY + 100;
        
        //
        this.setStartX(startX);
        this.setStartY(startY);
        this.setEndX(endX);
        this.setEndY(endY);
    }

    @Override
    public void drag(int x, int y) {
        
        
    }

    @Override
    public void size(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX() {
      return 0;
   }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNodeType() {
        return LINE;
    }

    @Override
    public void setStart(int initX, int initY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
