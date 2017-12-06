package map.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import map.gui.MapWorkspace;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.text.Text;
import static map.data.MapState.deleting_line;
import static map.data.MapState.deleting_station;
import static map.data.MapState.selecting_shape;
import static map.data.MapState.sizing_line;
import static map.data.MapState.sizing_shape;

/**
 * This class serves as the data management component for this application.
 *
 * @author NAFIUL AZIM
 * @author ?
 * @version 1.0
 */
public class MapData implements AppDataComponent {
    
    //Shapes in the map
    ObservableList<Node> shapes;
    //ObservableList<TrainLine> trainlines;
    //ObservableList<Station> stations;
    
    
    HashMap<String,TrainLine> lines;
    HashMap<String,Station> station;
    
    
    //Background color
    Color backgroundColor;
    
    Node newShape;
    
    Node selectedShape;
    
    Color currentFill, currentOutline; 
    double currentBorderWidth;
    
    MapState state;
    
    AppTemplate app;
    
    Effect highlighted;
    

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 1;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public MapData(AppTemplate initApp) {
	// KEEP THE APP FOR LATER
	app = initApp;

	newShape = null;
        selectedShape = null;
        currentFill = Color.web(WHITE_HEX);
        currentBorderWidth = 1;
        this.lines = new HashMap();
        this.station = new HashMap();
        DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlighted = dropShadowEffect;
	
    }

    public ObservableList<Node> getShapes() {
        return shapes;
    }

    public void setShapes(ObservableList<Node> shapes) {
        this.shapes = shapes;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        
        this.backgroundColor = backgroundColor;
        MapWorkspace workspace  = (MapWorkspace)app.getWorkspaceComponent();
        BackgroundFill fill = new BackgroundFill(backgroundColor,null,null);
        Background background = new Background(fill);
        workspace.getCanvas().setBackground(background);
    }

    public Node getNewShape() {
        return newShape;
    }

    public void setNewShape(Node newShape) {
        this.newShape = newShape;
    }

    public Node getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Node selectedShape) {
        this.selectedShape = selectedShape;
    }

    public Color getCurrentFill() {
        return currentFill;
    }

    public void setCurrentFill(Color currentFill) {
        this.currentFill = currentFill;
        if(selectedShape !=null){
        
          //  selectedShape.setFill(currentFill);
        }
    }

    public Color getCurrentOutline() {
        return currentOutline;
    }

    public void setCurrentOutline(Color currentOutline) {
        this.currentOutline = currentOutline;
    }

    public double getCurrentBorderWidth() {
        return currentBorderWidth;
    }

    public void setCurrentBorderWidth(double currentBorderWidth) {
        this.currentBorderWidth = currentBorderWidth;
    }

    public MapState getState() {
        return state;
    }

    public void setState(MapState state) {
        this.state = state;
    }

    public AppTemplate getApp() {
        return app;
    }

    public void setApp(AppTemplate app) {
        this.app = app;
    }

    public Effect getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Effect highlighted) {
        this.highlighted = highlighted;
    }
    
    
    
    
    
    
    
    

    @Override
    public void resetData() {
        
        state = selecting_shape;
        newShape = null;
        selectedShape = null;
        
        this.currentFill = Color.web(WHITE_HEX);
        this.currentOutline = Color.web(BLACK_HEX);
        
        shapes.clear();
        ((MapWorkspace)app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }
    
    public void startNewLine(int x, int y){
    
        MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
        
        String lineName = (String)workspace.getLineList().getSelectionModel().getSelectedItem();
        TrainLine newLine = new TrainLine(lineName, false);
        this.lines.put(lineName, newLine);
       // newLine.setThicknessslider(workspace.getLineThickness());
        newLine.start(x, y);
        newShape = newLine;
        
        shapes.addAll(newLine.startText,newLine,newLine.endText);
        
         if(selectedShape != null){
        
            unhighlightShape(selectedShape);
            selectedShape = null;
        }
         
         selectedShape = newShape;
        
       // newShape.setFill(workspace.getLineColor().getValue());
        newLine.setStroke(workspace.getLineColor().getValue());
        newLine.setStrokeWidth(workspace.getLineThickness().getValue());
        
        state = sizing_line;
    }

    
    public void startNewStation(int x, int y, Text name){
    
        Station newStation  = new Station(name);
        newShape = newStation;
        newStation.start(x, y);
        
        
        MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
        newStation.setFill(workspace.getStationFillColor().getValue());
        newStation.setStroke(workspace.getStationFillColor().getValue());
       // newStation.setRadius(workspace.getStopThickness().getValue());
        newStation.setRadiusX(workspace.getStopThickness().getValue());
        newStation.setRadiusY(workspace.getStopThickness().getValue());
        
        newStation.setOnMouseDragged(e->{
        
            newStation.drag((int)e.getX(), (int)e.getY());
        });
        
        station.put(newStation.name.getText(), newStation);
        shapes.add(newShape);
        shapes.add(newStation.name);
        workspace.getStationList().getItems().add(newStation.name.getText());
        workspace.getStationList().getSelectionModel().selectLast();
    }
    
    
    
    private void unhighlightShape(Node shape) {
        
        
        
        shape.setEffect(null);
    }
    
    public void selectShape(int x, int y){
    
        if(selectedShape != null){
                
                    selectedShape.setEffect(null);
                    selectedShape = null;
                    
                }
                
        
        /*
        for(int i= shapes.size()-1; i>=0; i--){
        
            if((shapes.get(i)).contains(x,y)){
            
                
                this.selectedShape = shapes.get(i);
                selectedShape.setEffect(this.highlighted);
                if(selectedShape instanceof TrainLine){
                
                   // lineSetting();
                }
            }
        }
        */
    }
    
    
    public Node selectTopNode(int x, int y){
    
        Node node = getTopNode(x,y);
        
        if(node == selectedShape){
        
            return node;
        }
        
        if(selectedShape != null){
        
            unhighlight(node);
        }
        
        
        if(node instanceof TrainLine){
        
            TrainLine temp = (TrainLine)node;
            
            lineSettings(temp);
        }else if(node instanceof Station){
        
            Station temp = (Station)node;
            stationSettings(temp);
        }else{
        
            
        }
        
        return node;
    }
    
    public Node getTopNode(int x, int y){
    
        for(int i= shapes.size()-1; i>=0; i--){
        
            if(((Shape)shapes.get(i)).contains(x,y)){
            
                
                return shapes.get(i);
                
            }
        }
        
        return null;
    }
    
    
    
    boolean isCurrentState(MapState state){
    
        return this.state == state;
    }

    public void removeSelection() {
        if(selectedShape != null){
        
            if(selectedShape instanceof TrainLine){
            
                TrainLine temp = (TrainLine)selectedShape; 
                shapes.remove(temp.startText);
                shapes.remove(temp.endText);
            }else if(selectedShape instanceof Station){
            
                Station temp = (Station)selectedShape; 
                shapes.remove(temp.name);
               
                
            }
            
            
            shapes.remove(selectedShape);
            selectedShape = null;
        }
    }
    
    
    
    public void removeElement(String element){
    
        if(state == deleting_line){
        
            TrainLine line = (TrainLine)lines.get(element);
            shapes.removeAll(line,line.startText,line.endText);
            
            Set<String> k = line.stops.keySet();
            Iterator it = k.iterator();
            while(it.hasNext()){
                line.stops.get(it.next()).lines.remove(element);
            }
            
            
            MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
            workspace.getLineList().getItems().remove(element);
            System.out.println("Line Removed Successfully");
            
        }else if(state == deleting_station){
        
         Station temp = (Station)this.station.get(element);
         Set<String> k = temp.lines.keySet();
         Iterator it = k.iterator();
            while(it.hasNext()){
                temp.lines.get(it.next()).stops.remove(element);
            }
            
        }
    }
    
    
    

    public void deleteLine(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void nodeSettings(TrainLine temp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void lineSettings(TrainLine temp) {
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        space.getLineList().getSelectionModel().select(temp.getName());
        space.getLineThickness().setValue(temp.getStrokeWidth());
        //space.setLineThickness(temp.getThicknessSlider());
        temp.getLabeledLine().setEffect(highlighted);
        
    }

    private void stationSettings(Station temp) {
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        space.getStationList().getSelectionModel().select(temp.getName());
        space.getStopThickness().setValue(temp.getStrokeWidth());
        //temp.setEffect(highlighted);
    }

    public HashMap<String, TrainLine> getLines() {
        return lines;
    }

    public HashMap<String, Station> getStation() {
        return station;
    }

    private void unhighlight(Node node) {
        
        node.setEffect(null);
    }

    public void selectLine(String lineName) {
        
        Node node = lines.get(lineName);
        
        if(shapes.contains(node)){
        
            unhighlight(selectedShape);
            selectedShape = node;
            selectedShape.setEffect(highlighted);
            MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
            this.lineSettings(lines.get(lineName));
            System.out.println("entered");
        }
    }

    public void processAddStationToLine(int x, int y) {
        
        Node node = this.getTopNode(x, y);
        
        if(node instanceof Station){
        
            MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
            Station state = (Station)node;
            TrainLine line = (TrainLine)lines.get((String)space.getLineList().getValue());
            line.addStop(state.name.getText(), state);
            state.addLine(line.getName(), line);
        }
    }

    public void processRemoveStationFromLine(int x, int y) {
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        TrainLine line = this.lines.get(space.getLineList().getValue());
        line.removeStationFromLine(x,y);
    }
    
    
}
