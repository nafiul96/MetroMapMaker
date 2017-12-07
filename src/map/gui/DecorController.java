/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import map.data.ImageLay;
import map.data.LabelNote;
import map.data.MapData;
import map.data.MapState;
import static map.data.MapState.selecting_shape;

/**
 *
 * @author nafi
 */
public class DecorController {

    AppTemplate app;

    MapData data;

    public DecorController(AppTemplate app) {
        this.app = app;
        data = (MapData) app.getDataComponent();

    }

    void processChangeBackgroundFill() {

        MapWorkspace space = (MapWorkspace) app.getWorkspaceComponent();

        BackgroundFill fill = new BackgroundFill(space.backgroundColor.getValue(), null, null);
        Background background = new Background(fill);
        space.getCanvas().setBackground(background);
    }

    void processImageBackground() {

        Image img = promptForImage();

        if (img != null) {

            BackgroundImage myBI = new BackgroundImage(img,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
            MapWorkspace space = (MapWorkspace) app.getWorkspaceComponent();
            Background back = new Background(myBI);
            space.getCanvas().setBackground(back);

        }

    }

    private Image promptForImage() {
        // SETUP THE FILE CHOOSER FOR PICKING IMAGES
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images/"));
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
        FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterBMP, extFilterGIF, extFilterJPG, extFilterPNG);
        fileChooser.setSelectedExtensionFilter(extFilterPNG);

        // OPEN THE DIALOG
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            return image;
        } catch (IOException ex) {
            //AppDialogs.showMessageDialog(app.getGUI().getWindow(), "ERROR LOADING IMAGE TITLE", "ERROR LOADING IMAGE CONTENT");
            return null;
        }
    }

    void processAddImageOverLay() {

        Image imageToAdd = promptForImage();
        if (imageToAdd != null) {
            ImageLay imageViewToAdd = new ImageLay();
            imageViewToAdd.setImage(imageToAdd);
            imageViewToAdd.xProperty().set(400);
            imageViewToAdd.yProperty().set(400);
            data.getShapes().add(imageViewToAdd);
            imageViewToAdd.setOnMouseDragged(e->{
        
            imageViewToAdd.xProperty().set(e.getX());
            imageViewToAdd.yProperty().set(e.getY());
        });
        }
    }

    void processAddLabelNote() {
        
        AppMessageDialogSingleton dial = AppMessageDialogSingleton.getSingleton();
        
        GridPane container = new GridPane();
        Label content = new Label("Enter your Note: ");
        TextField field = new TextField();
        Button ok = new Button("ok");
        ok.setOnAction(e->{
        
            if(!field.getText().isEmpty()){
            
                LabelNote note = new LabelNote(field.getText());
                note.xProperty().set(300);
                note.yProperty().set(400);
                note.setOnMouseDragged(ek->{
                
                    note.xProperty().set(ek.getX());
                    note.yProperty().set(ek.getY());
                    
                });
                data.getShapes().add(note);
                dial.close();
            }
        });
        
        container.addRow(0, content, field);
        container.addRow(2, ok);
        
        Scene scene = new Scene(container);
        dial.setTitle("Add Label Note");
        dial.setScene(scene);
        dial.showAndWait();
        
    }

    void processRemoveElement() {
        data.removeSelection();
        data.setState(selecting_shape);
    }

}
