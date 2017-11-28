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
import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import static map.data.MapState.selecting_shape;
import static map.data.MapState.sizing_shape;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class MapData implements AppDataComponent {
    
    //Shapes in the map
    ObservableList<Node> shapes;
    ObservableList<TrainLine> trainlines;
    ObservableList<Station> stations;
    
    //Background color
    Color backgroundColor;
    
    Shape newShape;
    
    Shape selectedShape;
    
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
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

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

    public Shape getNewShape() {
        return newShape;
    }

    public void setNewShape(Shape newShape) {
        this.newShape = newShape;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public Color getCurrentFill() {
        return currentFill;
    }

    public void setCurrentFill(Color currentFill) {
        this.currentFill = currentFill;
        if(selectedShape !=null){
        
            selectedShape.setFill(currentFill);
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
    
    public void startNewLine(int x, int y, Text name){
    
        Text txt = name;
        
        TrainLine newLine = new TrainLine(txt,false);
        newLine.start(x, y);
        newShape = newLine;
        initShape();
        shapes.addAll(newLine.startText, newLine.endText);
        newLine.startText.setOnMouseDragged(e->{
        
            newLine.dragStart(e.getX(), e.getY());
        });
        
        newLine.endText.setOnMouseDragged(e->{
        
            newLine.dragEnd(e.getX(), e.getY());
        });
        
    }

    
    public void startNewStation(int x, int y, Text name){
    
        Station newStation  = new Station(name);
        newShape = newStation;
        newStation.start(x, y);
        
        
        MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
        newShape.setFill(workspace.getStationFillColor().getValue());
        newShape.setStroke(workspace.getStationFillColor().getValue());
       // newStation.setRadius(workspace.getStopThickness().getValue());
        newStation.setRadiusX(workspace.getStopThickness().getValue());
        newStation.setRadiusY(workspace.getStopThickness().getValue());
        
        newStation.setOnMouseDragged(e->{
        
            newStation.drag((int)e.getX(), (int)e.getY());
        });
        
        shapes.add(newShape);
        shapes.add(newStation.name);
        workspace.getStationList().getItems().add("Map statetion " + shapes.size());
        workspace.getStationList().getSelectionModel().selectLast();
    }
    
    
    
    private void initShape() {
        if(selectedShape != null){
        
            unhighlightShape(selectedShape);
            selectedShape = null;
        }
        MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
        newShape.setFill(workspace.getLineColor().getValue());
        newShape.setStroke(workspace.getLineColor().getValue());
        newShape.setStrokeWidth(workspace.getLineThickness().getValue());
        
        shapes.add(newShape);
       // workspace.getStationList().getItems().add();
        workspace.getLineList().getItems().add("Map statetion " + shapes.size());
        workspace.getLineList().getSelectionModel().selectLast();
       //state = sizing_shape;
        
    }

    private void unhighlightShape(Shape shape) {
        shape.setEffect(null);
    }
    
    public void selectShape(int x, int y){
    
        if(selectedShape != null){
                
                    selectedShape.setEffect(null);
                    selectedShape = null;
                }
                
        
        
        for(int i=0; i<shapes.size(); i++){
        
            if(((Shape)shapes.get(i)).contains(x,y)){
            
                
                this.selectedShape = (Shape)shapes.get(i);
                selectedShape.setEffect(this.highlighted);
            }
        }
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
    
    
}
