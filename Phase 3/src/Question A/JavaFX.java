import java.io.IOException;
import javafx.application.Application;
import javafx.scene.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class JavaFX extends Application {
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    static Scene scene;
    public static String type;
    static String algorithm;
    public static String value1;
    public static String value2;
    public static String value3;
    public static String quantity1;
    public static String quantity2;
    public static String quantity3;
    static int[] values = new int[3];
    public static int[] quantities = new int[3];
    TextField[] textFields;
    public static boolean unlimited;
    static boolean stop;
    Thread thread;

    final Group group=new Group();
     // Move this line to the top of the class
    private PerspectiveCamera camera;
    private AmbientLight ambientLight;

    private double lastX;
    private double lastY;

    private final int BLOCK_SIZE = 30;
    private final int OFFSET = 0;
    //private final int width = 75; // Move these declarations above their usage
    //private final int height = 120;
    //private final int depth = 415;
    //private final int xCoord = (width * BLOCK_SIZE + (width * OFFSET)) / 2;
    //private final int yCoord = (height * BLOCK_SIZE + (height * OFFSET)) / 2;
    //private final int zCoord = (depth * BLOCK_SIZE + (depth * OFFSET)) / 2;

    int[][][] exampleArray = {
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
        {{1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1},
        {1,1,2,2,2,2,2,1,1,1,1,1,2,2,1,1,1,1,1,2,2,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1},},
};

 
@Override
public void start(Stage stage) throws Exception {
    // Creating a scene object colored in black
    this.camera = new PerspectiveCamera();
    this.ambientLight = new AmbientLight(Color.WHITE);

    // Load the FXML file
    Parent loader = FXMLLoader.load(getClass().getResource("mygui.fxml"));
    VBox pane = (VBox) loader;

    // Set up the scene
    Scene scene = new Scene(pane, 800, 1000);
    scene.setFill(Color.WHITE);
    stage.setTitle("Truck Visualizer");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();

    // Access the panes using their IDs
    Pane leftpane = (Pane) scene.lookup("#leftpane");
   
    Pane rightPane=(Pane)scene.lookup("#rightpane");
   
    // Continue with the rest of your code
    SubScene subScene = new SubScene(group, 800, 500, true, SceneAntialiasing.BALANCED);
    subScene.setCamera(this.camera);
    leftpane.getChildren().add(subScene);
    this.enableMouseInteraction(scene, subScene, group);
    subScene.setPickOnBounds(true);
    leftpane.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent arg0) {
            subScene.requestFocus();
        }
    });
    pane.getChildren().addAll(leftpane,rightPane);
    this.group.getChildren().add(this.ambientLight);

    this.group.translateXProperty().set(WIDTH / 5);
    this.group.translateYProperty().set(HEIGHT / 2);

    this.drawParcel(this.exampleArray);
    this.createContainerOutlines();
}

    @Override
    public void stop(){
        stop=true;
    }
    public static void setScore(int solutionScore) {
        Label score = (Label) scene.lookup("#score");
        score.setText(solutionScore + "");
    }

    private void enableMouseInteraction(Scene scene,SubScene subScene,final Node group ) {
        subScene.setOnMousePressed(event -> {
            this.lastX = event.getSceneX();
            this.lastY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - this.lastX;
            double deltaY = event.getSceneY() - this.lastY;

            double deltaXAngle = deltaX / scene.getWidth() * 360;
            double deltaYAngle = deltaY / scene.getHeight() * 360;

            Rotate rotateX = new Rotate(deltaYAngle, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(deltaXAngle, Rotate.Y_AXIS);

            // Apply rotation transformations to the group (truck, parcels, and outlines)
            this.group.getTransforms().addAll(rotateX, rotateY);

            this.lastX = event.getSceneX();
            this.lastY = event.getSceneY();

        });
        Button subButton=(Button) scene.lookup("#sub");
Button stopButton=(Button) scene.lookup("#stop");
CheckBox unlimitedObj = (CheckBox) scene.lookup("#unlimited");

        @SuppressWarnings("unchecked")
        ComboBox<String> typeObj = (ComboBox<String>) scene.lookup("#type");

        @SuppressWarnings("unchecked")
        ComboBox<String> algoObj = (ComboBox<String>) scene.lookup("#algo");
        TextField qAObj = (TextField) scene.lookup("#qa");
        TextField qBObj = (TextField) scene.lookup("#qb");
        TextField qCObj = (TextField) scene.lookup("#qc");
        TextField vAObj = (TextField) scene.lookup("#va");
        TextField vBObj = (TextField) scene.lookup("#vb");
        TextField vCObj = (TextField) scene.lookup("#vc");
        textFields = new TextField[] { qAObj, qBObj, qCObj, vAObj, vBObj, vCObj };

        EventHandler<ActionEvent> submitHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (parseInput()) {
                    type = typeObj.getValue();
                    algorithm = algoObj.getValue();
                    value1 = vAObj.getText();
                    value2 = vBObj.getText();
                    value3 = vCObj.getText();

                    values = new int[] { Integer.parseInt(value1), Integer.parseInt(value2), Integer.parseInt(value3) };

                    unlimited = unlimitedObj.isSelected();
                    if(!unlimited) {
                    quantity1 = qAObj.getText();
                    quantity2 = qBObj.getText();
                    quantity3 = qCObj.getText();
                    quantities = new int[] { Integer.parseInt(quantity1),
                    Integer.parseInt(quantity2), Integer.parseInt(quantity3) };
                    }
                    
                    stopButton.setDisable(false);
                    subButton.setDisable(true);
                    runAlgorithm();
                }
            }
        };
        EventHandler<ActionEvent> stopHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stop = true;
                stopButton.setDisable(true);
                subButton.setDisable(false);
            }
        };
        EventHandler<ActionEvent> unlimitedHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                unlimited = unlimitedObj.isSelected();
                
                if (unlimited) {
                    qAObj.setDisable(true);
                    qBObj.setDisable(true);
                    qCObj.setDisable(true);
                } else {
                    qAObj.setDisable(false);
                    qBObj.setDisable(false);
                    qCObj.setDisable(false);
                }
            }
        };
        
        algoObj.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            @SuppressWarnings("rawtypes")
            public void changed(ObservableValue ov, String t, String t1) {
                if(t1.equals("Dancing Links")) {
                    unlimitedObj.setSelected(true);
                    unlimitedObj.setDisable(true);
                    qAObj.setDisable(true);
                    qBObj.setDisable(true);
                    qCObj.setDisable(true);
                    unlimited = true;
                } else {
                    unlimitedObj.setDisable(false);
                }
            }    
         });
         typeObj.valueProperty().addListener(new ChangeListener<String>() {
            String[] textPromptParcel= new String[] { "Quantity A", "Quantity B", "Quantity B", "Value A", "Value B", "Value C" };
            String[] textPromptPent = new String[] { "Quantity L", "Quantity P", "Quantity T", "Value L", "Value P", "Value T" };
             @Override
             @SuppressWarnings("rawtypes")
             public void changed(ObservableValue ov, String t, String t1) {
                 if(t1.equals("Pentominoes")) {
                     for(int i = 0; i < textFields.length; i++) {
                         textFields[i].setPromptText(textPromptPent[i]);
                     }
                 } else {
                     for(int i = 0; i < textFields.length; i++) {
                         textFields[i].setPromptText(textPromptParcel[i]);
                     }
                 }
             }    
          });
    }
    private void runAlgorithm() {
        //switch (algorithm) {
           // case "Genetic Algorithm":
                //GA.setVisualizer(this);
             //   thread = new Thread(new GA());
              //  break;
           // case "Dancing Links":
               // DancingLinks.setVisualizer(this);
               // thread = new Thread(new DancingLinksRunnable());
               // break;
           // case "Greedy Algorithm":
              //  GreedyAlgorithm.setVisualizer(this);
                //thread = new Thread(new GreedyAlgorithm());
              //  break;
       // }
        stop = false;
        thread.start();
    }
    public static boolean getStop() {
        return stop;
    }
    public boolean parseInput() {
        int index = 0;
        if(unlimited) index = 3;

        for (int i = index; i < textFields.length; i++) {
            if (textFields[i].getText().equals(""))
                textFields[i].setText("0");

            if (!textFields[i].getText().matches("^[0-9]+$"))
                return false;
        }
        int sum = 0;

        if(unlimited) return true;

        for(int i = 0; i < 3; i++) {
            sum += Integer.parseInt(textFields[i].getText());
        }

        if(sum == 0) textFields[0].setText("1");

        return true;
    }
    private PhongMaterial getColor(int value) {
        final PhongMaterial material = new PhongMaterial();

        switch(value) {
            case 1:
                material.setDiffuseColor(Color.RED);
                break;
            case 2:
                material.setDiffuseColor(Color.BLUE);
                break;
            case 3:
                material.setDiffuseColor(Color.PURPLE);
                break;
            default:
                return null;
        }
        return material;
    }

    private void drawParcel(int[][][] array) {

        this.group.getChildren().clear();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int k = 0; k < array[i][j].length; k++) {
                    if (array[i][j][k] == 0) {
                        continue;
                    }

                    PhongMaterial color = this.getColor(array[i][j][k]);
                    Box box = new Box(30, 30, 30);
                    box.setMaterial(color);
                    box.setTranslateX((k * this.BLOCK_SIZE) + (k * this.OFFSET));
                    box.setTranslateY((j * this.BLOCK_SIZE) + (j * this.OFFSET));
                    box.setTranslateZ((i * this.BLOCK_SIZE) + (i * this.OFFSET));
                    this.group.getChildren().add(box);
                }
            }
        }
    }

    public void createContainerOutlines() {
        int boxWidth = 495 * 2;
        int boxHeight = 75 * 2;
        int boxDepth = 120 * 2;
        int offset = -(this.BLOCK_SIZE/2);
        Point3D p1 = new Point3D(offset, offset, offset);
        Point3D p2 = new Point3D(boxWidth + offset, offset, offset);
        Point3D p3 = new Point3D(offset, boxHeight + offset, offset);
        Point3D p4 = new Point3D(boxWidth + offset, boxHeight + offset, offset);
        Point3D p5 = new Point3D(offset, offset, boxDepth + offset);
        Point3D p6 = new Point3D(boxWidth + offset, offset, boxDepth + offset);
        Point3D p7 = new Point3D(offset, boxHeight + offset, boxDepth + offset);
        Point3D p8 = new Point3D(boxWidth + offset, boxHeight + offset, boxDepth + offset);

        this.createLine(p1, p2);
        this.createLine(p1, p3);
        this.createLine(p1, p5);
        this.createLine(p2, p6);
        this.createLine(p2, p4);
        this.createLine(p3, p4);
        this.createLine(p3, p7);
        this.createLine(p4, p8);
        this.createLine(p5, p6);
        this.createLine(p5, p7);
        this.createLine(p6, p8);
        this.createLine(p7, p8);
    }

    public void createLine(Point3D origin, Point3D target) { // Math taken from math stackexchange question #42124225

        double width = 2.0;

        Point3D yPoint = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToOriginCenter = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D rAxis = diff.crossProduct(yPoint);
        double angle = Math.acos(diff.normalize().dotProduct(yPoint));
        Rotate rotate = new Rotate(-Math.toDegrees(angle), rAxis);

        Cylinder line = new Cylinder(width, height);

        line.getTransforms().addAll(moveToOriginCenter, rotate);
        this.group.getChildren().add(line);
    }

    public static void main(String args[]) {
        launch(args);
    }
   
}