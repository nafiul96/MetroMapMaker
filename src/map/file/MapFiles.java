package map.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javafx.scene.text.Text;
import map.data.MapData;
import map.data.Station;
import map.data.TrainLine;


/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class MapFiles implements AppFileComponent {

    private static final String JSON_NAME = "name";
    private int c;
    
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        
        MapData manager = (MapData)data;
        
        JsonArrayBuilder linesArray = Json.createArrayBuilder();
        JsonArrayBuilder stations = Json.createArrayBuilder();
        
        ObservableList<Node> shapes = manager.getShapes();
        
        for(int i=0; i<manager.getShapes().size(); i++){
        
            Node shape = shapes.get(i);
            if(shape instanceof TrainLine){
            
                TrainLine tline = (TrainLine)shape;
                String name = tline.getName();
                Color col = (Color)tline.getStroke();
                double red = col.getRed();
                double green = col.getGreen();
                double blue  = col.getBlue();
                double alpha = col.getOpacity();
                JsonObject color = Json.createObjectBuilder()
                        .add("red", red)
                        .add("green", green)
                        .add("blue", blue)
                        .add("alpha", alpha)
                        .build();
                JsonArrayBuilder lineStation = Json.createArrayBuilder();
                
                Set<String> k = tline.getStops().keySet();
                Iterator it = k.iterator();
                
                while(it.hasNext()){
                
                    lineStation.add(tline.getStops().get(it.next()).getName().getText());
                }
                
                JsonArray lineStations = lineStation.build();
                
                JsonObject lineJas = Json.createObjectBuilder()
                        .add("name", name)
                        .add("circular", tline.isCircular())
                        .add("color", color)
                        .add("station_names", lineStations)
                        .build();
                
                
                //lineStation.addAll(tline.getStation());
                
                
                
                
                linesArray.add(lineJas);
                
            }else if(shape instanceof Station){
            
                Station tStation = (Station)shape;
                String name = tStation.getName().getText();
                double cX = tStation.getCenterX();
                double cY = tStation.getCenterY();
                JsonObject ts = Json.createObjectBuilder()
                        .add("name", name)
                        .add("x", cX)
                        .add("y", cY)
                        .build();
                stations.add(ts);
            }
        }
        JsonArray JsonStateArray = stations.build();
        JsonObject obj = Json.createObjectBuilder()
                .add("name", "WikiCity")
                .add("lines", linesArray)
                .add("stations", JsonStateArray)
                .build();
        
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(obj);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(obj);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        MapData dataManager = (MapData)data;
       // dataManager.resetData();
        System.out.println("couted to " + (c++));
        JsonObject json = loadJSONFile(filePath);
        String metroName = json.getString("name");
        System.out.println(metroName);
        JsonArray lineArray = json.getJsonArray("lines");
        System.out.println("couted to " + (c++));
        JsonArray stationArray = json.getJsonArray("stations");
        
        for(int i=0; i< stationArray.size(); i++){
        
            JsonObject obj = stationArray.getJsonObject(i);
            //Station temp = new Station(new Text("name"));
            double x = this.addAsDouble(obj, "x");
            double y = this.addAsDouble(obj, "y");
            dataManager.startNewStation((int)x, (int)y,new Text(obj.getString("name")));
            
            //dataManager.addNode(temp);
        }
        
        for(int k=0; k<lineArray.size(); k++){
        
            JsonObject obj = lineArray.getJsonObject(k);
            //Text txt = new Text(obj.getString("name"));
            
           
            TrainLine temp = dataManager.loadNewLine(obj.getString("name"));
            
            JsonArray js = obj.getJsonArray("station_names");
            for(int p=0; p<js.size(); p++){
            
                Station st = dataManager.getStation().get(js.get(p).toString());
                if(st != null){
                    dataManager.processAddStationToLine((int)st.getCenterX(), (int)st.getCenterY());
                    //temp.addStop(st.getName().getText(), st);
                }
            }
        }
          
        
        
    }
    
    
    
    
    

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JsonObject loadJSONFile(String filePath) throws IOException {
       InputStream is = new FileInputStream(filePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json; 
    }

    

    private Shape loadShape(JsonObject lineObj, MapData data, JsonArray stations) {
        
        String name = lineObj.getString("name");
        boolean isCircular = lineObj.getBoolean("circular");
        JsonObject col = lineObj.getJsonObject("color");
        Color lineColor = loadLineColor(col);
        JsonArray stops = lineObj.getJsonArray("station_names");
        TrainLine myLine = new TrainLine(name,isCircular);
        myLine.setStroke(lineColor);
        myLine.setFill(lineColor);
        myLine.setStrokeWidth(10);
       
        for(int i=0; i<stops.size(); i++){
        
            for(int j=0; j<stations.size(); j++){
            
                JsonObject temp = stations.getJsonObject(j);
                if(stops.getString(i) == temp.getString("name")){
                    
                    //Station state  = new Station(new Text(stations.getString(j,name)));
                    String Ename = temp.getString("name");
                    double x = addAsDouble(temp,"x");
                    double y = addAsDouble(temp,"y");
                    data.startNewStation((int)x, (int)y, new Text(Ename));
                    //Station state = new Station();
                    //myLine.addStop(Ename,x,y);
                }
            }
        }
        
        
       
        return myLine;
        
        
    }

    private Color loadLineColor(JsonObject col) {
        
        double red = addAsDouble(col, "red");
        double green = addAsDouble(col, "green");
        double blue = addAsDouble(col, "blue");
        double alpha = addAsDouble(col, "alpha");
        return new Color(red, green, blue, alpha);
    }

    private double addAsDouble(JsonObject col, String rep) {
        JsonValue value = col.get(rep);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();
        
    }
   
}
