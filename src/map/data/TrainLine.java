/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import static map.data.MapState.starting_line;

/**
 *
 * @author nafi
 */
public class TrainLine extends Polyline implements MapElement {
 
    double startX, startY;
    double endX, endY;
    Text name;
    boolean isCircular;
    ObservableList<Double> point;
    Text startText,endText;
    HashMap<String, Station> stops;
    ArrayList<String> station;
    
    public TrainLine(Text myName, boolean isCircular){
    
       super();
       stops = new HashMap<>();
       station = new ArrayList<>();
       this.setStroke(Color.BLUE);
       name = myName;
       point = this.getPoints();
       startX = 300;
       startY = 300;
       this.isCircular = isCircular;
       point.add(startX);
       point.add(startY);
       endX = startX;
       endY = 100 + startY;
       startText = new Text(name.getText());
       this.setAccessibleText("Good");
       
       endText = new Text(name.getText());
       
        
    }
    
    
    public void addStop(String name, double x, double y){
    
        Station temp = new Station(new Text(name));
        temp.setCenterX(x);
        temp.setCenterY(y);
        stops.put(name, temp);
        point.add(x);
        point.add(y);
        
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }
    
    
    

    public Text getStartText() {
        return startText;
    }

    public void setStartText(Text startText) {
        this.startText = startText;
    }

    public Text getEndText() {
        return endText;
    }

    public ArrayList<String> getStation() {
        return station;
    }

    public void setStation(ArrayList<String> station) {
        this.station = station;
    }

    public void setEndText(Text endText) {
        this.endText = endText;
    }
    
    public boolean isCircular(){
    
        return isCircular;
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
        
        
       // this.getPoints().add((double)x);
        //this.getPoints().add((double)y);
        
        point.add((double)x);
        point.add((double)y);
       
        //startText.relocate(point.get(0), point.get(1));
        
        startText.xProperty().set(startX);
        startText.yProperty().set(startY);
        
        endText.xProperty().set(endX);
        endText.yProperty().set(endY);
        
        
        
        
    }
    
    void addStation(Station e){
    
        
        
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

    void dragStart(double x, double y) {
        
        double diffX = x - (startX);
	double diffY = y - (startY);
	double newX = startX + diffX;
	double newY = startY + diffY;
        
        
        startText.xProperty().set(x);
        startText.yProperty().set(y);
        point.remove(0);
        point.remove(0);
        
        startX = newX;
        startY = newY;
        point.add(0, startY);
        point.add(0, startX);
        startText.xProperty().set(point.get(0));
        startText.yProperty().set(point.get(1));
        
    }

    void dragEnd(double x, double y) {
        
        double diffX = x - endX;
	double diffY = y - endY;
	double newX = endX + diffX;
	double newY = endY + diffY;
        
        
        
        point.remove(point.size()-1);
        point.remove(point.size()-1);
        endX = newX;
        endY = newY;
        
        point.add(endX);
        point.add(endY);
       // startText.relocate(endX, endY);
        endText.xProperty().set(endX);
        endText.yProperty().set(endY);
        
        
    }

    
    
}
