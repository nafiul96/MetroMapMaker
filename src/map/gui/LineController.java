/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jtps.jTPS;
import map.data.MapData;
import map.data.MapState;
import map.data.TrainLine;
import map.trans.History;
import map.trans.LineEdit_Transaction;

/**
 *
 * @author nafi
 */
public class LineController {
    
    //MapState state;
    
    AppTemplate app;
    MapData data;
    
    public LineController(AppTemplate initApp){
    
        app = initApp;
        
    }
    
    
    void processAddLine(){
    
        
        
            
            data = (MapData)app.getDataComponent();
            data.setState(MapState.starting_line);
            initLineDialog();
            
        
        
        
    }
    
    
    public void initLineDialog(){
    
        AppMessageDialogSingleton dial = AppMessageDialogSingleton.getSingleton();
        dial.setTitle("Add Line");
        VBox box = new VBox();
        //HBox pane = new HBox();
        GridPane pane = new GridPane();
        
        Label label = new Label("Line Name: ");
        TextField txt = new TextField();
        
        MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
        
        
        Label label2 = new Label("Color: ");
        ColorPicker picker = new ColorPicker();
        
        Button btn = new Button("Add");
        
        btn.setOnAction(e->{
        
            if(!txt.getText().isEmpty()){
            
                workspace.getLineList().getItems().add(txt.getText());
                workspace.getLineList().getSelectionModel().selectLast();
                workspace.getLineColor().setValue(picker.getValue());
                //workspace.setLineThickness(new Slider(0,10,1));
                //Scene appScene = app.getGUI().getPrimaryScene();
               // appScene.setCursor(Cursor.CROSSHAIR);
                dial.close();
            }
        });
        
        pane.addRow(0, label,txt);
        pane.addRow(3, label2, picker);
        
        pane.addRow(5, btn);
        
        Scene scene = new Scene(pane,300,300);
        
        dial.setScene(scene);
        
        dial.showAndWait();
        
    
        
    }
    
    
    
    public void processEditLine(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        lineEditDialog((String)space.getLineList().getValue());
    }
    
    
    void lineEditDialog(String name){
    
    
        TrainLine line = data.getLines().get(name);
        AppMessageDialogSingleton dial = AppMessageDialogSingleton.getSingleton();
        dial.setTitle("Edit Line");
        VBox box = new VBox();
        //HBox pane = new HBox();
        GridPane pane = new GridPane();
        
        Label label = new Label("Line Name: ");
        TextField txt = new TextField();
        txt.setText(name);
        
        
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        
        
        Label label2 = new Label("Color: ");
        ColorPicker picker = new ColorPicker();
        picker.setValue((Color)line.getStroke());
        Button btn = new Button("Add");
        
        btn.setOnAction(e->{
        
            if(!txt.getText().isEmpty()){
            
                jTPS tps = History.getTps();
MapData data = (MapData)app.getDataComponent();
LineEdit_Transaction newTransaction = new LineEdit_Transaction(app,line,line.getName(), (Color) line.getStroke(),txt.getText(),picker.getValue());
tps.addTransaction(newTransaction);
                dial.close();
            }
        });
        
        pane.addRow(0, label,txt);
        pane.addRow(3, label2, picker);
        
        pane.addRow(5, btn);
        
        Scene scene = new Scene(pane,300,300);
        
        dial.setScene(scene);
        
        dial.showAndWait();
    
    }
    
    
    
    void processRemoveLine(){
    
        data.setState(MapState.deleting_line);
       MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        data.removeElement((String)space.getLineList().getValue());
        
    }
    
    
    void processAddStationToLine(){
    
        data.setState(MapState.add_station_mode);
    }
    
    
    void processRemoveStationFromLine(){
    
        data.setState(MapState.remove_station_mode);
    }
    
    
    void processListAllStation(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        TrainLine line = data.getLines().get(space.getLineList().getValue());
        
        
        AppMessageDialogSingleton dial = AppMessageDialogSingleton.getSingleton();
        dial.setTitle("View All Stations");
        
        ListView v = new ListView();
        Set k = line.getStops().keySet();
        
        Iterator  it = k.iterator();
        LinkedList<String> s = new LinkedList();
        while(it.hasNext()){
        /*
            String t = (String)it.next();
            if(line.getStops().get(t) != null){
            
                s.addLast(t);
            }
*/          
            s.add(line.getStops().get(it.next()).getName().getText());
        }
        v.getItems().setAll(s);
        ScrollPane box = new ScrollPane();
        box.setContent(v);
        Scene scene = new Scene(box);
        dial.setScene(scene);
        dial.showAndWait();
        
    }
    
    void processLineSelection(){
    
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        if(!space.getLineList().getItems().isEmpty()){
        data.selectLine((String)space.getLineList().getValue());
        }
    }
    
    void processStrokeChangeRequest(){
    
        
        MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        if(!space.getLineList().getItems().isEmpty()){
        String name = (String)space.getLineList().getValue();
        
        TrainLine line = data.getLines().get(name);
        if(line != null){
        
            line.setStrokeWidth(space.getLineThickness().getValue());
        }
        
    }
    }
    
}
