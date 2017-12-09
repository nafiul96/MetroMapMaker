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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
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

    String name;
    Text startText, endText;
    Group labeledLine;
    boolean isCircular;
    ObservableList<Double> point;
    double startX, startY, endX, endY;
    HashMap<String, Station> stops;

    public HashMap<String, Station> getStops() {
        return stops;
    }
    ArrayList<String> station;

    public TrainLine(String myName, boolean isCircular) {

        //LabelelingInformation
        name = myName;
        startText = new Text(name);
        endText = new Text(name);
        labeledLine = new Group();
        labeledLine.getChildren().addAll(startText, this, endText);
        this.isCircular = isCircular;

        //Point Information- the DNA of a Line
        point = this.getPoints();
        startX = startY = endX = endY = 0.0;
        stops = new HashMap<>();
        station = new ArrayList<>();
        initTextControl();

    }

    public Group getLabeledLine() {

        return labeledLine;
    }

    public void addStop(String stationName, Station station) {

        double x = station.getCenterX();
        double y = station.getCenterY();

        int minX = 0;

        double mindist = dist(point.get(0), point.get(1), x, y);

        for (int i = 0; i < point.size(); i = i + 2) {

            double temp = dist(point.get(i), point.get(i + 1), x, y);
            if (temp < mindist) {

                minX = i;
                mindist = temp;
            }

        }

        int minY = minX + 1;

        point.add(minY + 1, y);
        point.add(minY + 1, x);
        stops.put(stationName, station);

    }
    
    public void removeStationFromLine(int x, int y) {
        
        Set k = this.stops.keySet();
        Iterator it = k.iterator();
        Station temp = null;
        while(it.hasNext()){
        
            temp = this.stops.get((String)it.next());
            if(temp != null && temp.contains(x, y)){
            
                temp.lines.replace(this.name, this, null);
                this.stops.replace(temp.name.getText(), temp, null);
                
                break;
            }
        }
        if(temp !=null){
        
            for(int i=0; i< point.size(); i = i+2){
            
                if(point.get(i) == temp.getCenterX() && point.get(i+1) == temp.getCenterY()){
                
                    point.remove(i);point.remove(i);
                    return;
                }
            }
        }
        
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
        this.startText.setText(name);
        this.endText.setText(name);
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

    public boolean isCircular() {

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
        startX = x;
        startY = y;
        
        point.add(startX);
        point.add(startY);

        //startText.relocate(point.get(0), point.get(1));
       /*startText.setX(startX);
       startText.setY(startY);
       endText.setX(endX);
       endText.setY(endX);
*/  
       startText.xProperty().set(startX);
       startText.yProperty().set(startY);
       endText.xProperty().set(endX);
       endText.xProperty().set(endY);

    }

    void addStation(Station e) {

    }

    @Override
    public void drag(int x, int y) {

        
        if(startText.contains(x, y)){
        
            this.dragStart(x, y);
        }else if(endText.contains(x, y)){
        
            this.dragEnd(x, y);
        }
    }

    @Override
    public void size(int x, int y) {
        
        endX = x;
        endY = y;
        
        if(point.size() == 4){
        
            point.remove(point.size()-1);
            point.remove(point.size()-1);
        }
       //point.remove(point.size()-1);
      // point.remove(point.size()-1);
       point.add(endX);
       point.add(endY);
       
       //endText.setX(endX);
       //endText.setY(endY);
       endText.xProperty().set(endX);
        endText.yProperty().set(endY);       
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

        //startText.setX(x);
        //startText.setY(y);
        point.remove(0);
        point.remove(0);

        startX = x;
        startY = y;
        point.add(0, startY);
        point.add(0, startX);
        startText.xProperty().set(point.get(0)-10);
        startText.yProperty().set(point.get(1));

    }

    void dragEnd(double x, double y) {

        //endText.setX(x);
        //endText.setY(y);
        
        point.remove(point.size() - 1);
        point.remove(point.size() - 1);
        endX = x;
        endY = y;

       point.add(endX);
       point.add(endY);
        // startText.relocate(endX, endY);
        endText.xProperty().set(point.get(point.size()-2)+10);
        endText.yProperty().set(point.get(point.size()-1));
       
    }

    private double dist(Double x1, Double y1, double x, double y) {

        double temp = Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2);

        return Math.sqrt(temp);

    }

    private void initTextControl() {

        startText.setOnMouseDragged(e -> {

            dragStart(e.getX(), e.getY());
        });

        endText.setOnMouseDragged(e -> {

            dragEnd(e.getX(), e.getY());
        });
        
        
        
       // endText.setOnMouseReleased(e->{
        
         //   point.add(e.getX());
         //   point.add(e.getY());
        //});
        
       // startText.setOnMouseReleased(e->{
        
          //  point.add(0,e.getY());
         //   point.add(0,e.getX());
       // });
         
        
        
    }

}