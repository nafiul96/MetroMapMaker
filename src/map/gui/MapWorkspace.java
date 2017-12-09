package map.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import static map.Property.*;
import map.data.MapData;
import static map.data.MapData.BLACK_HEX;
import static map.data.MapData.WHITE_HEX;
import map.data.MapState;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.awt.Font;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import static map.css.MapStyle.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import static map.Property.ABOUT_ICON;
import static map.Property.ABOUT_TOOLTIP;
import static map.Property.REDO_ICON;
import static map.Property.REDO_TOOLTIP;
import static map.Property.UNDO_ICON;
import static map.Property.UNDO_TOOLTIP;
import properties_manager.PropertiesManager;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Nafiul Azim
 * @author ?
 * @version 1.0
 */
public class MapWorkspace extends AppWorkspaceComponent {

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    //UndoRedo toolbar
    FlowPane undoRedo;
    Button undo, redo;

    //About Toolbar
    FlowPane aboutPane;
    Button about;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;

    //Line Toolbar
    VBox lineToolPane;
    ColorPicker lineColor;
    HBox linePane1;
    HBox linePane2;
    HBox linePane3;
    Label lineLabel;
    ComboBox lineList;
    Button addLine, removeLine, addStation, removeStation, listStation, editLine;
    Slider lineThickness;

    //StationPane
    VBox stationPane;
    ColorPicker stationColor;
    HBox stationPane1;
    HBox stationPane2;
    HBox stationPane3;
    Label stationPaneLabel;
    ComboBox stationList;
    Button addStop, removeStop, moveLabel, rotateLabel, snap;
    Slider stopThickness;
    ColorPicker stationFillColor;

    //Navigation Toolbar
    VBox navTool;
    HBox navContainer;
    ComboBox starting, ending;
    Button navigate;

    //Decor tool
    VBox decorTool;
    Label decorLabel;
    ColorPicker backgroundColor;
    HBox decorContent;
    HBox decorContainer;
    Button imageBack;
    Button addImage;
    Button addLabel;
    Button removeElement;

    //Font Tool
    VBox fontTool;
    HBox fontTool1;
    HBox fontTool2;
    ColorPicker fontColor;
    Label fontLabel;
    ToggleButton fontBold, fontItalic;
    ComboBox fontSize, fontName;

    //VIEWPORT TOOLBAR
    VBox viewPane;
    Label viewLabel;
    HBox viewPane1;
    HBox viewPane2;
    CheckBox grid;
    Button zoomIn;
    Button zoomOut;
    Button paneUp;
    Button paneDown;

    
    
    Button export;
    // THIS IS WHERE WE'LL RENDER OUR DRAWING, NOTE THAT WE
    // CALL THIS A CANVAS, BUT IT'S REALLY JUST A Pane
    Pane canvas;
    ScrollPane canvasScroll;

    Scene temp;

    
    
    //All Controllers
    LineController lineControl;
    canvasController canvasControl;
    StationController stationControl;
    DecorController decorControl;
    FontController fontControl;
    UndoController trans;
    MapWorkspaceController wsControl;
    // HERE ARE THE CONTROLLERS
    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    // FOR DISPLAYING DEBUG STUFF
    Text debugText;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public MapWorkspace(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // KEEP THE GUI FOR LATER
        gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();
        temp = gui.getWindow().getScene();

        //gui.getFileController().handleNewRequest();
        // HOOK UP THE CONTROLLERS
        initController();
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
        welcome();
        //ohWell();

    }

    public ComboBox getStationList() {
        return stationList;
    }

    public void welcome() {

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        BorderPane mainPane = new BorderPane();

        //LeftPane
        VBox leftPane = new VBox();
        VBox rightPane = new VBox();
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty("WELCOME_ICON");
        Image img = new Image(imagePath, 600, 250, false, false);

        ImageView view = new ImageView(img);

        //view.resize(150, 75);
        rightPane.getChildren().add(view);
        rightPane.setAlignment(Pos.TOP_CENTER);

        //LeftPane Component
        Label section = new Label("Recent Work");
        Text txt1 = new Text("Seoul");
        txt1.setUnderline(true);
        Text txt2 = new Text("NYC");
        txt2.setUnderline(true);
        leftPane.getChildren().addAll(section, txt1, txt2);
        txt1.setOnMouseClicked(e -> {

            if (e.getClickCount() > 0) {

                gui.getWindow().setTitle(gui.getWindow().getTitle() + "-" + txt1.getText());
                gui.getWindow().setScene(gui.getPrimaryScene());
                gui.getFileController().handleNewRequest();

            }
        });
        txt2.setOnMouseClicked(e -> {

            if (e.getClickCount() > 0) {

                gui.getWindow().setTitle(gui.getWindow().getTitle() + "-" + txt2.getText());
                gui.getWindow().setScene(gui.getPrimaryScene());
                gui.getFileController().handleNewRequest();

            }
        });

        Text txt3 = new Text("New Work");
        txt3.setUnderline(true);

        rightPane.getChildren().add(txt3);

        txt3.setOnMouseClicked(e -> {

            if (e.getClickCount() > 0) {

                gui.getWindow().setTitle("Metro Map Maker -" + txt3.getText());
                gui.getWindow().setScene(gui.getPrimaryScene());
                gui.getFileController().handleNewRequest();

            }
        });

        mainPane.setLeft(leftPane);
        mainPane.setCenter(rightPane);
        leftPane.setStyle("-fx-background-color:#f2c58e;\n"
                + "    -fx-padding: 90 10 0 30;\n"
                + "    -fx-font: bold 24px \"serif\";"
                + "-fx-min-width:300;"
                + "-fx-spacing:60;"
                + "-fx-border-width:3px;-fx-border-color:#ab353d;");

        rightPane.setStyle("-fx-background-color:#f2c58e;\n"
                + "    -fx-padding: 60 10 0 30;\n"
                + "    -fx-font: bold 24px \"serif\";"
                + "-fx-min-width:300;"
                + "-fx-spacing:60;"
                + "-fx-border-width:3px;-fx-border-color:#ab353d;");

        Scene scene = new Scene(mainPane);

        gui.getWindow().setScene(scene);

    }

    public Slider getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(Slider lineThickness) {
        this.lineThickness = lineThickness;
    }

    public ColorPicker getLineColor() {
        return lineColor;
    }

    public void setLineColor(ColorPicker lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
        debugText.setText(text);
    }

    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    public Pane getCanvas() {
        return canvas;
    }

    // HELPER SETUP METHOD
    private void initLayout() {

        //gui.getTopToolbarPane().setAlignment(Pos.CENTER);
        //Undo Teoolbar
        undoRedo = new FlowPane();
         export = new Button("exp");
         export.setTooltip(new Tooltip("Export"));
         gui.getFileToolbar().getChildren().add(export);
         
         
         
        undo = gui.initChildButton(undoRedo, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), false);
        redo = gui.initChildButton(undoRedo, REDO_ICON.toString(), REDO_TOOLTIP.toString(), false);
        //undoRedo.getChildren().add(export);
        undoRedo.resize(10, 20);
        gui.getTopToolbarPane().getChildren().add(undoRedo);

        //About Toolbar
        aboutPane = new FlowPane();
        about = gui.initChildButton(aboutPane, ABOUT_ICON.toString(), ABOUT_TOOLTIP.toString(), false);
        gui.getTopToolbarPane().getChildren().add(aboutPane);

        // THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
        editToolbar = new VBox();

       
        
        
        //LinePane
        lineToolPane = new VBox();
        lineLabel = new Label("Metro Lines");
        lineList = new ComboBox();
        linePane1 = new HBox();
        linePane2 = new HBox();
        linePane3 = new HBox();

        lineColor = new ColorPicker(Color.web(BLACK_HEX));
        lineColor.setTooltip(new Tooltip("Choose Line Color"));

        addLine = gui.initChildButton(linePane2, ADD_LINE_ICON.toString(), ADD_LINE_TOOLTIP.toString(), false);
        removeLine = gui.initChildButton(linePane2, REMOVE_LINE_ICON.toString(), REMOVE_LINE_TOOLTIP.toString(), false);
        addStation = gui.initChildButton(linePane2, ADD_STATION_ICON.toString(), ADD_STATION_TOOLTIP.toString(), false);
        removeStation = gui.initChildButton(linePane2, REMOVE_STATION_ICON.toString(), REMOVE_STATION_TOOLTIP.toString(), false);
        editLine = new Button("Edit\nLine");
        listStation = gui.initChildButton(linePane2, LIST_STATION_ICON.toString(), LIST_STATION_TOOLTIP.toString(), false);
        lineThickness = new Slider(0, 10, 1);

        linePane1.getChildren().addAll(lineLabel, lineList, editLine);
        // linePane2.getChildren().addAll(addLine, removeLine, addStation, removeStation, listStation);
        linePane3.getChildren().add(lineThickness);
        lineToolPane.getChildren().addAll(linePane1, linePane2, linePane3);
        //lineToolPane.getChildren().addAll();
        editToolbar.getChildren().add(lineToolPane);

        //Station Tools
        stationPane = new VBox();
        stationPane1 = new HBox();
        stationPane2 = new HBox();
        stationPane3 = new HBox();

        stationPaneLabel = new Label("Metro Stations");
        stationFillColor = new ColorPicker(Color.valueOf(BLACK_HEX));
        stationFillColor.setTooltip(new Tooltip("Fill Station Circle"));

        stationList = new ComboBox();
        addStop = gui.initChildButton(stationPane2, ADD_STOP_ICON.toString(), ADD_STOP_TOOLTIP.toString(), false);
        removeStop = gui.initChildButton(stationPane2, REMOVE_STOP_ICON.toString(), REMOVE_LINE_TOOLTIP.toString(), false);
        snap = gui.initChildButton(stationPane2, SNAP_ICON.toString(), SNAP_TOOLTIP.toString(), false);
        moveLabel = gui.initChildButton(stationPane2, MOVE_LABEL_ICON.toString(), MOVE_LABEL_TOOLTIP.toString(), false);
        rotateLabel = gui.initChildButton(stationPane2, ROTATE_ICON.toString(), ROTATE_TOOLTIP.toString(), false);
        stopThickness = new Slider(0, 10, 1);

        stationPane1.getChildren().addAll(stationPaneLabel, stationList, stationFillColor);
        //stationPane2.getChildren().addAll(addStop, removeStop, snap, moveLabel, rotateLabel);
        stationPane3.getChildren().addAll(stopThickness);

        stationPane.getChildren().addAll(stationPane1, stationPane2, stationPane3);
        editToolbar.getChildren().add(stationPane);

        //Navigation Tool
        navTool = new VBox();
        navContainer = new HBox();
        starting = new ComboBox();
        ending = new ComboBox();

        navContainer.getChildren().addAll(starting, ending);

        navigate = gui.initChildButton(navContainer, FIND_ROUTE_ICON.toString(), FIND_ROUTE_TOOLTIP.toString(), false);
        navTool.getChildren().addAll(navContainer);
        editToolbar.getChildren().add(navTool);

        //Decor Toolbar
        decorTool = new VBox();
        decorContainer = new HBox();
        decorContent = new HBox();
        decorLabel = new Label("Decor");
        backgroundColor = new ColorPicker(Color.valueOf(BLACK_HEX));
        backgroundColor.setTooltip(new Tooltip("Choose Background Color"));
        decorContent.getChildren().addAll(decorLabel, backgroundColor);

        imageBack = gui.initChildButton(decorContainer, IMAGE_BACK_ICON.toString(), IMAGE_BACK_TOOLTIP.toString(), false);
        addImage = gui.initChildButton(decorContainer, ADD_IMAGE_ICON.toString(), ADD_IMAGE_TOOLTIP.toString(), false);
        addLabel = gui.initChildButton(decorContainer, ADD_LABEL_ICON.toString(), ADD_LABEL_TOOLTIP.toString(), false);
        removeElement = gui.initChildButton(decorContainer, REMOVE_ELEMENT_ICON.toString(), REMOVE_ELEMENT_TOOLTIP.toString(), false);
        //decorContainer.getChildren().addAll(addLabel, imageBack, addImage, removeElement);
        decorTool.getChildren().addAll(decorContent, decorContainer);
        editToolbar.getChildren().add(decorTool);

        //Font Tool
        fontTool = new VBox();
        fontTool1 = new HBox();
        fontTool2 = new HBox();

        fontLabel = new Label("Font");
        fontColor = new ColorPicker(Color.valueOf(BLACK_HEX));
        fontColor.setTooltip(new Tooltip("Choose Font color"));
        fontBold = toggleChildButton(fontTool2, BOLD_ICON.toString(), BOLD_TOOLTIP.toString(), false);
        fontItalic = toggleChildButton(fontTool2, ITALIC_ICON.toString(), ITALIC_TOOLTIP.toString(), false);
        fontSize = initComboBox("FONT_SIZE_COMBO_BOX_OPTIONS");
        fontName = initComboBox("FONT_FAMILY_COMBO_BOX_OPTIONS");

        fontTool1.getChildren().addAll(fontLabel, fontColor);
        fontTool2.getChildren().addAll(fontSize, fontName);

        fontTool.getChildren().addAll(fontTool1, fontTool2);

        editToolbar.getChildren().add(fontTool);

        //ViewPane and its elements
        viewPane = new VBox();
        viewPane1 = new HBox();
        viewPane2 = new HBox();
        viewLabel = new Label("Navigation");
        grid = new CheckBox();
        zoomIn = gui.initChildButton(viewPane2, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), false);
        zoomOut = gui.initChildButton(viewPane2, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        paneUp = gui.initChildButton(viewPane2, SHRINK_ICON.toString(), SHRINK_TOOLTIP.toString(), false);
        paneDown = gui.initChildButton(viewPane2, DILATE_ICON.toString(), DILATE_TOOLTIP.toString(), false);
        viewPane1.getChildren().addAll(viewLabel, grid);
        // viewPane2.getChildren().addAll(zoomIn, zoomOut, paneUp, paneDown);
        viewPane.getChildren().addAll(viewPane1, viewPane2);
        editToolbar.getChildren().add(viewPane);

        // WE'LL RENDER OUR STUFF HERE IN THE CANVAS
        canvas = new Pane();
        debugText = new Text();
        canvas.getChildren().add(debugText);
        debugText.setX(100);
        debugText.setY(100);

        canvasScroll = new ScrollPane();
        MapData data = (MapData) app.getDataComponent();
        data.setShapes(canvas.getChildren());
        canvasScroll.setContent(canvas);
        
        // AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
        // AND NOW SETUP THE WORKSPACE
        workspace = new BorderPane();
        ((BorderPane) workspace).setLeft(editToolbar);
        ((BorderPane) workspace).setCenter(canvasScroll);
        
        Group grp = new Group();
        grp.getChildren().addAll(canvas);
        grp.getChildren().addAll(canvas.getChildren());
        
        canvasScroll.fitToWidthProperty().set(true);
        canvasScroll.fitToHeightProperty().set(true);
        canvasScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //gui.getFileController().handleNewRequest();
    }

    private void initController() {

        canvasControl = new canvasController(app);
        lineControl = new LineController(app);
        stationControl = new StationController(app);
        decorControl = new DecorController(app);
        fontControl = new FontController(app);
        trans = new UndoController(app);
        wsControl = new MapWorkspaceController(app);
        //Listeners for Line toolbar
        addLine.setOnAction(e -> {

            lineControl.processAddLine();
        });
        removeLine.setOnAction(e -> {

            lineControl.processRemoveLine();
        });

        this.addStation.setOnAction(e -> {

            lineControl.processAddStationToLine();
        });

        this.addStation.setOnAction(e -> {

            lineControl.processAddStationToLine();
        });

        this.editLine.setOnAction(e -> {

            lineControl.processEditLine();
        });
        this.removeStation.setOnAction(e -> {

            lineControl.processRemoveStationFromLine();
        });
        this.listStation.setOnAction(e -> {

            lineControl.processListAllStation();
        });
        this.lineList.setOnAction(e -> {

            lineControl.processLineSelection();
        });
        this.lineThickness.setOnMouseReleased(e->{
        
            lineControl.processStrokeChangeRequest();
        });
        
        

        //Listeners for Station Toolbar
        addStop.setOnAction(e -> {

            stationControl.processAddStation();
        });

        removeStop.setOnAction(e -> {

            stationControl.processRemoveStation();
        });

        this.moveLabel.setOnAction(e -> {

            stationControl.processMoveLabel();
        });
        this.rotateLabel.setOnAction(e -> {

            stationControl.processRotateLabel();
        });
        this.stationFillColor.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                stationControl.processChangeFillColor();
            }

        });

        this.stopThickness.setOnMouseReleased(e-> {
            
            stationControl.processChangeRadius();
            });

        canvas.setOnMouseClicked(e -> {

            canvasControl.processMousePress((int) e.getX(), (int) e.getY());
        });

        canvas.setOnMouseDragged(e -> {

            canvasControl.mouseDrag((int) e.getX(), (int) e.getY());
        });

        canvas.setOnMouseReleased(e -> {

            canvasControl.mouseRelease((int) e.getX(), (int) e.getY());
        });
        
        canvasScroll.setOnKeyPressed(e->{
        
            if(e.getCode() == KeyCode.A){
            
                canvasScroll.setHvalue(canvasScroll.getHvalue()-0.1);
            }else if(e.getCode() == KeyCode.D){
                canvasScroll.setHvalue(canvasScroll.getHvalue()+0.1);
            }else if(e.getCode() == KeyCode.W){
                canvasScroll.setVvalue(canvasScroll.getVvalue()+0.1);
            }else if(e.getCode() == KeyCode.S){
                canvasScroll.setVvalue(canvasScroll.getVvalue()-0.1);
            }
        });

        //Decor toolbar listeners
        this.backgroundColor.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                decorControl.processChangeBackgroundFill();
            }
        });
        this.imageBack.setOnAction(e -> {

            decorControl.processImageBackground();
        });
        this.addImage.setOnAction(e -> {

            decorControl.processAddImageOverLay();
        });
        this.addLabel.setOnAction(e -> {

            decorControl.processAddLabelNote();
        });
        this.removeElement.setOnAction(e -> {

            decorControl.processRemoveElement();
            System.out.println("processed");
        });
        
        
        
        //Font toolbar listeners
        this.fontColor.setOnAction(e->{
        
            fontControl.changeTextColor();
        });
        this.fontBold.setOnAction(e->{
        
            fontControl.changeTextFont();
        });
        this.fontItalic.setOnAction(e->{
        
            fontControl.changeTextFont();
        });
        
        this.fontSize.setOnAction(e->{
        
            fontControl.changeTextFont();
        });
        this.fontName.setOnAction(e->{
        
            fontControl.changeTextFont();
        });
        

        //Workspace listeners
        this.paneUp.setOnAction(e->{
        
            wsControl.shrink();
        });
        
        this.paneDown.setOnAction(e->{
        
            wsControl.blow();
        });
       this.zoomIn.setOnAction(e->{
       
           wsControl.zoomIn();
           
       });
       
       this.zoomOut.setOnAction(e->{
       
           wsControl.zoomOut();
           
       });
       
       
       export.setOnAction(e->{
       
           wsControl.processSnapshot();
       });
        
        
        //Grid toggle
        this.grid.setOnAction(e -> {

            if (grid.isSelected()) {

                workspace.getStyleClass().add(GRID_LINE);
            } else {

                workspace.getStyleClass().remove(GRID_LINE);
            }
        });
        
        
        //Undo-Redo listener
        this.undo.setOnAction(e->{
        
            trans.processUndoRequest();
        });
        
        this.redo.setOnAction(e->{
        
            trans.processRedoRequest();
        });

    }
    
    
    public javafx.scene.text.Font fontSettings(){
    
        String fontFamily = fontName.getSelectionModel().getSelectedItem().toString();
        int size = Integer.valueOf(fontSize.getSelectionModel().getSelectedItem().toString());
        FontWeight weight = FontWeight.NORMAL;
        if (fontBold.isPressed()) weight = FontWeight.BOLD;
        FontPosture posture = FontPosture.REGULAR;
        if (fontItalic.isPressed()) posture = FontPosture.ITALIC;
        javafx.scene.text.Font newFont = javafx.scene.text.Font.font(fontFamily, size);
        return newFont;
    }
    
    private ComboBox initComboBox(String comboPropertyList) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> comboOptions = props.getPropertyOptionsList(comboPropertyList);
        ObservableList oList = FXCollections.observableList(comboOptions);
        ComboBox cBox = new ComboBox(oList);
        cBox.getSelectionModel().selectFirst();
        return cBox;
    }
    

    public ColorPicker getFontColor() {
        return fontColor;
    }
    

    public ColorPicker getStationColor() {
        return stationColor;
    }

    public void setStationColor(ColorPicker stationColor) {
        this.stationColor = stationColor;
    }

    public Slider getStopThickness() {
        return stopThickness;
    }

    public void setStopThickness(Slider stopThickness) {
        this.stopThickness = stopThickness;
    }

    public ColorPicker getStationFillColor() {
        return stationFillColor;
    }

    public void setStationFillColor(ColorPicker stationFillColor) {
        this.stationFillColor = stationFillColor;
    }

    public ComboBox getLineList() {
        return lineList;
    }

    public void setLineList(ComboBox lineList) {
        this.lineList = lineList;
    }

    public ToggleButton toggleChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        ToggleButton button = new ToggleButton();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);

        // PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);

        // AND RETURN THE COMPLETED BUTTON
        return button;
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {

        //File toolbar style
        gui.getFileToolbar().getStyleClass().add(TOP_TOOLBAR);

        //UndoRedo toolbar Style
        undoRedo.getStyleClass().add(TOP_TOOLBAR);
        undo.getStyleClass().add(CLASS_FILE_BUTTON);
        redo.getStyleClass().add(CLASS_FILE_BUTTON);

        //About Toolbar Style
        aboutPane.getStyleClass().add(TOP_TOOLBAR);
        about.getStyleClass().add(CLASS_FILE_BUTTON);

        //Workspace style
        workspace.getStyleClass().add(CLASS_MAX_PANE);

        //editToolbar Style
        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);

        // canvas style
        canvas.getStyleClass().add(CLASS_MAX_PANE);
        
        
        //Line tool style
        lineToolPane.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        linePane1.getStyleClass().add(ROW_ITEM);
        linePane2.getStyleClass().add(ROW_ITEM);
        linePane3.getStyleClass().add(ROW_ITEM);
        addLine.getStyleClass().add(SMALL_BUTTON);
        removeLine.getStyleClass().add(SMALL_BUTTON);
        listStation.getStyleClass().add(SMALL_BUTTON);
        lineColor.getStyleClass().add(CLASS_BUTTON);
        addStation.getStyleClass().add(BIG_BUTTON);
        removeStation.getStyleClass().add(BIG_BUTTON);
        lineLabel.getStyleClass().add(SEC_LABEL);
        lineColor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        lineList.getStyleClass().add(COMBO_STYLE);

        //Station tool Style
        stationPane.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        stationPane1.getStyleClass().add(ROW_ITEM);
        stationPane2.getStyleClass().add(ROW_ITEM);
        stationPane3.getStyleClass().add(ROW_ITEM);
        addStop.getStyleClass().add(SMALL_BUTTON);
        removeStop.getStyleClass().add(SMALL_BUTTON);
        snap.getStyleClass().add(SMALL_BUTTON);
        moveLabel.getStyleClass().add(SMALL_BUTTON);
        rotateLabel.getStyleClass().add(SMALL_BUTTON);
        stationPaneLabel.getStyleClass().add(SEC_LABEL);
        stationList.getStyleClass().add(COMBO_STYLE);
        stationFillColor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

        //Navigation too style
        navTool.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        navContainer.getStyleClass().add(ROW_ITEM);
        navigate.getStyleClass().add(SMALL_BUTTON);
        starting.getStyleClass().add(COMBO_STYLE);
        ending.getStyleClass().add(COMBO_STYLE);

        //Decor tool Style
        decorTool.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        decorContainer.getStyleClass().add(ROW_ITEM);
        decorContent.getStyleClass().add(ROW_ITEM);
        addImage.getStyleClass().add(BIG_BUTTON);
        imageBack.getStyleClass().add(BIG_BUTTON);
        addLabel.getStyleClass().add(BIG_BUTTON);
        export.getStyleClass().add(BIG_BUTTON);
        removeElement.getStyleClass().add(BIG_BUTTON);
        decorLabel.getStyleClass().add(SEC_LABEL);
        backgroundColor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

        //Font tool style
        fontTool.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fontTool1.getStyleClass().add(ROW_ITEM);
        fontTool2.getStyleClass().add(ROW_ITEM);
        fontBold.getStyleClass().add(SMALL_BUTTON);
        fontItalic.getStyleClass().add(SMALL_BUTTON);
        fontLabel.getStyleClass().add(SEC_LABEL);
        fontName.getStyleClass().add(COMBO_STYLE);
        fontColor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

        //ViewPane Style
        viewPane.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        viewPane.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        viewPane1.getStyleClass().add(ROW_ITEM);
        viewPane2.getStyleClass().add(ROW_ITEM);
        zoomIn.getStyleClass().add(SMALL_BUTTON);
        zoomOut.getStyleClass().add(SMALL_BUTTON);
        paneUp.getStyleClass().add(SMALL_BUTTON);
        paneDown.getStyleClass().add(SMALL_BUTTON);
        viewLabel.getStyleClass().add(SEC_LABEL);

    }

    /**
     * This function reloads all the controls for editing logos the workspace.
     */
    @Override
    public void reloadWorkspace(AppDataComponent data) {
        MapData dataManager = (MapData) data;

    }

    @Override
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }
}
