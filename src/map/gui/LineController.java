/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import map.data.MapData;
import map.data.MapState;

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
    
    
    
    
    
    
    
    void processRemoveLine(){
    
        data.setState(MapState.deleting_line);
       MapWorkspace space = (MapWorkspace)app.getWorkspaceComponent();
        data.removeElement((String)space.getLineList().getValue());
        
    }
    
    
    void processAddStationToLine(){
    
        data.setState(MapState.add_station_mode);
    }
    
    
    void processRemoveStationFromLine(){
    
        
    }
    
    
    void processListAllStation(){
    
        
    }
    
    
    
    
}
