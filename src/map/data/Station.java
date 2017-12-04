/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import static map.data.MapState.starting_station;

/**
 *
 * @author nafi
 */
public class Station extends Ellipse implements MapElement {

    double startCenterX;
    double startCenterY;
    Text name;

    HashMap<String, TrainLine> lines;

    public Station(Text name) {
        setCenterX(0.0);
        setCenterY(0.0);
        setRadiusX(20.0);
        setRadiusY(20.0);
        setOpacity(1.0);
        startCenterX = 0.0;
        startCenterY = 0.0;
        this.name = name;

        name.xProperty().bind(this.centerXProperty());
        name.yProperty().bind(this.centerYProperty());
        lines = new HashMap<>();

    }

    public void addLine(String lineName, TrainLine trainline) {

        lines.put(lineName, trainline);

    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    @Override
    public MapState getStartingState() {
        return starting_station;
    }

    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;
        setCenterX(startCenterX);
        setCenterY(startCenterY);
    }

    @Override
    public void drag(int x, int y) {
        double diffX = x - startCenterX;
        double diffY = y - startCenterY;
        double newX = getCenterX() + diffX;
        double newY = getCenterY() + diffY;
        setCenterX(newX);
        setCenterY(newY);
        
        
        Iterator it = lines.keySet().iterator();
        
        while(it.hasNext()){
        
            TrainLine temp = (TrainLine)lines.get(it.next());
            temp.drag(x, y);
            ObservableList<Double> pt = temp.point;
            for(int i=0; i< pt.size(); i= i+2){
            
                if(pt.get(i) == startCenterX && pt.get(i+1) == startCenterY){
                
                    pt.remove(i);
                    pt.remove(i);
                    pt.add(i, newY);
                    pt.add(i, newX);
                    System.out.println();
                    break;
                }
            }
        }
        
        startCenterX = x;
        startCenterY = y;
    }

    @Override
    public void size(int x, int y) {
        double width = x - startCenterX;
        double height = y - startCenterY;
        double centerX = startCenterX + (width / 2);
        double centerY = startCenterY + (height / 2);
        setCenterX(centerX);
        setCenterY(centerY);
        setRadiusX(width / 2);
        setRadiusY(height / 2);

    }

    @Override
    public double getX() {
        return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
        return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
        return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
        return getRadiusY() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        setCenterX(initX + (initWidth / 2));
        setCenterY(initY + (initHeight / 2));
        setRadiusX(initWidth / 2);
        setRadiusY(initHeight / 2);
    }

    @Override
    public String getNodeType() {
        return ELLIPSE;
    }

    @Override
    public MapElement makeClone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStart(int initX, int initY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
