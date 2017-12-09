/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javax.imageio.ImageIO;
import jtps.jTPS;
import map.data.MapData;
import map.file.MapFiles;
import map.trans.BlowMap_Transaction;
import map.trans.History;
import map.trans.ShrinkMap_Transaction;

/**
 *
 * @author nafi
 */
public class MapWorkspaceController {
    
    AppTemplate app;
    MapData data;
    MapWorkspace space;
    public MapWorkspaceController(AppTemplate initApp){
    
        app = initApp;
        
        data = (MapData)app.getDataComponent();
        
        space = (MapWorkspace)app.getWorkspaceComponent();
        
    }

    void shrink() {
        System.out.println("shrink");
        MapWorkspace spa = (MapWorkspace)app.getWorkspaceComponent();
        
        double height = spa.getCanvas().getHeight()/1.1;
        double width = spa.getCanvas().getWidth()/1.1;
        
        spa.getCanvas().setMinHeight(height);
        spa.getCanvas().setMinWidth(width);
        spa.canvasScroll.autosize();
        
        jTPS tps = History.getTps();
        MapData data = (MapData)app.getDataComponent();
        ShrinkMap_Transaction newTransaction = new ShrinkMap_Transaction(height,width,height*1.1,width*1.1, spa.getCanvas());
        tps.addTransaction(newTransaction);
    
        
    }

    void blow() {
         System.out.println("shrink");
        MapWorkspace spa = (MapWorkspace)app.getWorkspaceComponent();
        
        double height = spa.getCanvas().getHeight()*1.1;
        double width = spa.getCanvas().getWidth()*1.1;
      
        spa.getCanvas().setMinHeight(height);
        spa.getCanvas().setMinWidth(width);
        
    
    }
    
    void zoomIn(){
        
        MapWorkspace spa = (MapWorkspace)app.getWorkspaceComponent();
        Scale newScale = new Scale();
        newScale.setX(spa.getCanvas().getScaleX() * 1.5);
        newScale.setY(spa.getCanvas().getScaleY() * 1.5);
        spa.getCanvas().getTransforms().add(newScale);
        for(int i=0; i<data.getShapes().size(); i++){
        
            Node temp = data.getShapes().get(i);
            newScale = new Scale();
            newScale.setX(temp.getScaleX() * 1.5);
            newScale.setY(temp.getScaleY() * 1.5);
            temp.getTransforms().add(newScale);
            temp.getParent().layout();
        }
        
        
        
        System.out.println("shrink");
        
    }
    
    void zoomOut(){
    
        System.out.println("shrink");
        
        MapWorkspace spa = (MapWorkspace)app.getWorkspaceComponent();
        Scale newScale = new Scale();
        newScale.setX(spa.getCanvas().getScaleX() * 0.5);
        newScale.setY(spa.getCanvas().getScaleY() * 0.5);
        spa.getCanvas().getTransforms().add(newScale);
        for(int i=0; i<data.getShapes().size(); i++){
        
            Node temp = data.getShapes().get(i);
            newScale = new Scale();
            newScale.setX(temp.getScaleX() * 0.5);
            newScale.setY(temp.getScaleY() * 0.5);
            temp.getTransforms().add(newScale);
            temp.getParent().layout();
        }
        
    }
    
    
    
    
    
    
    
    ///Export processor
    
    String chooseSuitableName(){
    
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Export your Map");
        dialog.setHeaderText("Now give it a Name");
        dialog.setContentText("Please Enter map name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        String s = "";
        if (result.isPresent()){
            
            s = result.get();
        }

        return s;
    }
    
    private void makeDir(String string) {
        
        File file = new File(string);
        file.mkdir();
                
    }
    public void processSnapshot() throws IOException{
	MapWorkspace workspace = (MapWorkspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        //app.getFileComponent().loadData(app.getDataComponent(), );
        
        String mapname = chooseSuitableName();
        makeDir(PATH_WORK+"/"+mapname);
	File file = new File(PATH_WORK+"/"+mapname+"/"+mapname+".png");
        MapFiles comp = (MapFiles)app.getFileComponent();
        comp.saveData(app.getDataComponent(),PATH_WORK+"/"+mapname+"/"+mapname);
	try {
            
            
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }

    
    
    
    
    
    
    
    
}
