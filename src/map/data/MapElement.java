/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

/**
 *
 * @author nafi
 */
public interface MapElement {
    
    public static final String LINE = "LINE";
    public static final String ELLIPSE = "ELLIPSE";
    public static final String TEXT = "TEXT";
    public static final String IMAGE = "IMAGE";
    public MapElement makeClone();
    public MapState getStartingState();
    public void start(int x, int y);
    public void drag(int x, int y);
    public void size(int x, int y);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);
    public String getNodeType();
    public void setStart(int initX, int initY);
    
}
