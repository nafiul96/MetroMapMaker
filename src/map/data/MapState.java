package map.data;

/**
 * This enum has the various possible states of the logo maker app
 * during the editing process which helps us determine which controls
 * are usable or not and what specific user actions should affect.
 * 
 * @author Richard McKenna
 * @author NAFIUL AZIM
 * @version 1.0
 */
public enum MapState {
    
    
    
    starting_line,
    sizing_line,
    dragging_line,
    deleting_line,
    selecting_line,
    
    
    
    starting_station,
    deleting_station,
    selecting_station,
    //sizing_line,
    
    
    
    
    selecting_shape,
    dragging_shape,
    sizing_shape,
    
    add_station_mode,
    remove_station_mode,
    
    
    
    
    SELECTING_SHAPE,
    DRAGGING_SHAPE,
    STARTING_RECTANGLE,
    STARTING_ELLIPSE,
    SIZING_SHAPE,
    DRAGGING_NOTHING,
    SIZING_NOTHING, 
    remove_shape, 
    
    remove_station
}
