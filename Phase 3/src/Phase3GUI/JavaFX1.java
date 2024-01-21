
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class JavaFX1 extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    private Group group; // Move this line to the top of the class
    private PerspectiveCamera camera;
    private AmbientLight ambientLight;

    private double lastX;
    private double lastY;

    private final int BLOCK_SIZE = 30;
    private final int OFFSET = 0;
    private Group result = new Group();
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
        stage.setTitle("Phase 3 Visualization");

        int[][][] arr1 = this.QuestionA();
        int[][][] arr2 = this.QuestionB();
        int[][][] arr3 = this.QuestionC();
        int[][][] arr4 = this.QuestionD();

 LinearGradient gradient = new LinearGradient(
            0, 0,               // start coordinates
            0, 1,               // end coordinates
            true,               // proportional
            CycleMethod.NO_CYCLE, // cycle method
            new Stop(0, Color.LIGHTBLUE), // start color
            new Stop(1, Color.BLUE) // end color
    );

        //this.result.translateXProperty().set(WIDTH / 5);
        this.result.translateYProperty().set(HEIGHT / 5);

        //Scene 1
        ChoiceBox<Integer> layers = new ChoiceBox<>();
        layers.getItems().addAll(1,2,3,4,5,6,7,8);
        layers.setValue(8);
        layers.setStyle( " -fx-background-color: linear-gradient(to bottom, white, #4682B4);" +
        "-fx-border-color: white;" +          
        "-fx-border-width: 2px;" +            
        "-fx-border-radius: 0;" +          
        "-fx-padding: 5px;"  +
        " -fx-font-family: 'Times New Roman';" 
    
        
        );



Button button1 = new Button("Question A");
button1.setStyle(
    "-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +          
        "-fx-border-width: 2px;" +            
        "-fx-border-radius: 0;" +          
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"       +
        " -fx-font-family: 'Times New Roman'"  
             
);

Button button2 = new Button("Question B");
button2.setStyle(
    "-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;"+
        "-fx-padding: 10px;"+
        " -fx-font-family: 'Times New Roman'"
);



Button button3 = new Button("Question C");
button3.setStyle(
    "-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"+
        " -fx-font-family: 'Times New Roman'"
);


Button button4 = new Button("Question D");
button4.setStyle(
    "-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"+
       " -fx-font-family: 'Times New Roman'"
);


      
        button1.setOnAction(e -> this.drawParcel(arr1, this.result, layers.getValue())); 

     
        button2.setOnAction(e -> this.drawParcel(arr2, this.result, layers.getValue()));

        button3.setOnAction(e -> this.drawParcel(arr3, this.result, layers.getValue()));

        button4.setOnAction(e -> this.drawParcel(arr4, this.result, layers.getValue()));
        HBox layout1 = new HBox(20);   

        Label titleLabel = new Label("3D KNAPSACK");
        titleLabel.setStyle("fx-font-weight: bold;-fx-font-size: 40;-fx-font-family: 'Times New Roman'; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);
        layout1.setAlignment(Pos.CENTER);

        layout1.getChildren().addAll(button1, button2, button3, button4, layers);
        layout1.getChildren().add(titleLabel);
        BorderPane pane = new BorderPane();
        pane.setTop(layout1 );
        pane.setCenter(this.result);
        pane.setTop(layout1);
        pane.setCenter(this.result);
        pane.setTop(layout1);

        this.camera = new PerspectiveCamera();
        this.ambientLight = new AmbientLight(Color.WHITE);

        this.result.getChildren().add(this.ambientLight);
        pane.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene= new Scene(pane, WIDTH, HEIGHT);
        
        scene.setCamera(this.camera);
        stage.setScene(scene);
        stage.show();
        this.enableMouseInteraction(scene, this.result);
        this.createContainerOutlines();
        stage.setResizable(false);
    }

    private void enableMouseInteraction(Scene scene, Group group) {
        scene.setOnMousePressed(event -> {
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
            group.getTransforms().addAll(rotateX, rotateY);

            this.lastX = event.getSceneX();
            this.lastY = event.getSceneY();
        });
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

    private void drawParcel(int[][][] array, Group group, int layers) {

        group.getChildren().clear();

        for (int i = 0; i < layers; i++) {
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
                    group.getChildren().add(box);
                }
            }
        }
        this.createContainerOutlines();
    }

    public void createContainerOutlines() {
        int boxWidth = 75 * 2;
        int boxHeight = 495 * 2;
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
        this.result.getChildren().add(line);
    }

    public int[][][] QuestionA(){
        System.out.println("Question A Not fillable");
        return new int[8][33][5];
    }
    public int[][][] QuestionB(){
        GreedyAlgorithm greedy = new GreedyAlgorithm();
        greedy.fillTruck();
        greedy.printScore();

        return greedy.truck;
    }
    public int[][][] QuestionC(){
        BruteForceAlgorithm b = new BruteForceAlgorithm();
        b.search();

        int[][] arr = b.ans;
        int[][][] answer = new int[8][33][5];
        int parcel1 = 0;
        int parcel2 = 0;
        int parcel3 = 0;

        for(int z=0; z<answer.length; z++){
            for(int i=0; i<arr.length; i++){
                for(int j=0; j<arr[0].length; j++){
                    if(arr[i][j]==8){
                        answer[z][i][j] = 1;
                        parcel1++;
                    }
                    if(arr[i][j]==3){
                        answer[z][i][j] = 2;
                        parcel2++;
                    }
                    if(arr[i][j]==9){
                        answer[z][i][j] = 3;
                        parcel3++;
                    }
                    
                }
            }
        }

        System.out.println("Question C pentominoes used : " +  parcel1/5 + "," + parcel2/5 + " and " + parcel3/5);

        return answer;
    }
    public int[][][] QuestionD(){
        GreedyPentominoes greedy = new GreedyPentominoes();
        greedy.fillTruck();
        greedy.printScore();
        

        return greedy.truck;
    }

    public static void main(String args[]) {
        launch(args);
    }
}