/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import static com.sun.javafx.geom.BaseBounds.BoundsType.RECTANGLE;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;

/**
 *
 * @author nafi
 */
public class LabelNote extends Text implements MapElement {
    double startX;
    double startY;
    
    public LabelNote(String initText) {
        super(initText);
	setX(0.0);
	setY(0.0);
	//setWidth(0.0);
	//setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
    }
    
    @Override
    public LabelNote makeClone() {
        return null;
    }
    
    @Override
    public MapState getStartingState() {
	return MapState.STARTING_RECTANGLE;
    }
    
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	setX(x);
	setY(y);
    }
    
    @Override
    public void setStart(int initStartX, int initStartY) {
        startX = initStartX;
        startY = initStartY;
    }
    
    @Override
    public void drag(int x, int y) {
	//double diffX = x - (getX() + (getWidth()/2));
	//double diffY = y - (getY() + (getHeight()/2));
        double diffX = x - startX;
        double diffY = y - startY;
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    
    @Override
    public void size(int x, int y) {
	// WE DON'T CARE ABOUT THIS FOR TEXT
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
        // WE DON'T CARE ABOUT HTE SIZE
    }
    
    @Override
    public String getNodeType() {
	return "LabelNote";
    }    

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
