import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * This class is responsible for rendering the 3D GUI Graphics, displaying all algorithm.
 */
public class JavaFX1 extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    private PerspectiveCamera camera;
    private AmbientLight ambientLight;

    private double lastX;
    private double lastY;

    private final int BLOCK_SIZE = 30;
    private final int BLOCK_SPACE = 0; // Space bewteen blocks
    private Group result = new Group();

    /**
     * This method is called when the JavaFX application is launched.
     * It is responsible for the logic behind the GUI.
     * 
     * @param stage Top-level container for the GUI.
     * @throws Exception If an error occurs during the initialization or setup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Phase 3 Visualization");

        int[][][] arr1 = this.QuestionA();
        int[][][] arr2 = this.QuestionB();
        int[][][] arr3 = this.QuestionC();
        int[][][] arr4 = this.QuestionD();
        
        // Loads background image
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream("resources//backgroundSky.jpg"));
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                     new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
 
        // Translate the result group vertically
        this.result.translateYProperty().set(HEIGHT / 5);

        // CheckBox to select the amount of layers
        ChoiceBox<Integer> layers = new ChoiceBox<>();
        layers.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
        layers.setValue(8);
        layers.setStyle( "-fx-background-color: linear-gradient(to bottom, white, #708090);" +
        "-fx-border-color: white;" +          
        "-fx-border-width: 2px;" +            
        "-fx-border-radius: 0;" +          
        "-fx-padding: 5px;"  +
        " -fx-font-family: 'Times New Roman';" );

        Button button1 = new Button("Question A");
        button1.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +          
        "-fx-border-width: 2px;" +            
        "-fx-border-radius: 0;" +          
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"       +
        " -fx-font-family: 'Times New Roman'");

        Button button2 = new Button("Question B");
        button2.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;"+
        "-fx-padding: 10px;"+
        " -fx-font-family: 'Times New Roman'");

        Button button3 = new Button("Question C");
        button3.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"+
        " -fx-font-family: 'Times New Roman'");

        Button button4 = new Button("Question D");
        button4.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #000080);" +
        "-fx-border-color: white;" +
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 0;" +
        "-fx-text-fill: white;" +
        "-fx-padding: 10px;"+
        " -fx-font-family: 'Times New Roman'");
      
        button1.setOnAction(e -> this.drawParcel(arr1, this.result, layers.getValue(),false)); 
        button2.setOnAction(e -> this.drawParcel(arr2, this.result, layers.getValue(),true));
        button3.setOnAction(e -> this.drawParcel(arr3, this.result, layers.getValue(), false));
        button4.setOnAction(e -> this.drawParcel(arr4, this.result, layers.getValue(),false));
        HBox layout1 = new HBox(20);   

        Label titleLabel = new Label("3D KNAPSACK");
        titleLabel.setStyle("fx-font-weight: bold;-fx-font-size: 40;-fx-font-family: 'Times New Roman'; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);
        layout1.setAlignment(Pos.CENTER);

        layout1.getChildren().addAll(button1, button2, button3, button4, layers);
        layout1.getChildren().add(titleLabel);

        // BorderPane for the overall layout
        BorderPane pane = new BorderPane();
        pane.setTop(layout1 );
        pane.setCenter(this.result);
        pane.setTop(layout1);
        pane.setCenter(this.result);
        pane.setTop(layout1);

        this.camera = new PerspectiveCamera();
        this.ambientLight = new AmbientLight();

        pane.getChildren().add(this.ambientLight);
        pane.setBackground(new Background(background));

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setCamera(this.camera);
        stage.setScene(scene);
        stage.show();
        this.enableMouseInteraction(scene, this.result);
        this.createContainerOutlines(false);
        stage.setResizable(true);
    }

    /**
     * Enables mouse interaction to rotate 3D objects.
     * 
     * @param scene JavaFX scene.
     * @param group 3D group that containes the objects.
     */
    private void enableMouseInteraction(Scene scene, Group group) {

        scene.setOnMousePressed(event -> {
            this.lastX = event.getSceneX();
            this.lastY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            // Calculate rotation angles based on mouse movement
            double deltaX = event.getSceneX() - this.lastX;
            double deltaY = event.getSceneY() - this.lastY;

            double deltaXAngle = deltaX / scene.getWidth() * 360;
            double deltaYAngle = deltaY / scene.getHeight() * 360;

            // Create rotation transformations
            Rotate rotateX = new Rotate(deltaYAngle, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(deltaXAngle, Rotate.Y_AXIS);

            // Apply rotation transformations to the group (truck, parcels, and outlines)
            group.getTransforms().addAll(rotateX, rotateY);

            this.lastX = event.getSceneX();
            this.lastY = event.getSceneY();
        });
    }

    /**
     * Gets PhongMaterial representing a color based on the given value.
     * 
     * @param value Represents a parcel (1 - A; 2 - B; 3 - C)
     * @return PhongMaterial representing a color.
     */
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

    /**
     * Displays a 3D array in the GUI as 3D objects.
     * 
     * @param array The array that will be represented.
     * @param group The 3D group to which the parcels are added.
     * @param layers Number of parcel layers to consider.
     * @param B Flag to indicate question B.
     */
    private void drawParcel(int[][][] array, Group group, int layers, boolean B) {

        group.getChildren().clear();

        int arrayL = array[0][0].length;
        int array1L = layers;

        if (B) {
            arrayL = layers;
            array1L = array.length;
        }
        
        // If a position in the array is different than 0, a parcel is created
        for (int i = 0; i < array1L; i++) {
            for (int j = 0; j < 33 ; j++) {
                for (int k = 0; k < arrayL; k++) {
                    if (
                        array[i][j][k] == 0) {
                        continue;
                    }
                    
                    PhongMaterial color = this.getColor(array[i][j][k]);
                    Box box = new Box(30, 30, 30);
                    box.setMaterial(color);
                    box.setTranslateX((k * this.BLOCK_SIZE) + (k * this.BLOCK_SPACE));
                    box.setTranslateY((j * this.BLOCK_SIZE) + (j * this.BLOCK_SPACE));
                    box.setTranslateZ((i * this.BLOCK_SIZE) + (i * this.BLOCK_SPACE));
                    box.setMaterial(color);
                    group.getChildren().add(box);
                }
            }
        }
        this.createContainerOutlines(B);
        group.getChildren().add(new AmbientLight());
    }

    /**
     * Creates truck's outlines.
     * 
     * @param B Flag to indicate Question B.
     */
    public void createContainerOutlines(boolean B) {

        int boxWidth = 75 * 2;
        int boxHeight = 495 * 2;
        int boxDepth = 120 * 2;

        // If the question B is in place, the measurements are slightly different
        if (B) {
            boxWidth = 120 * 2;
            boxDepth = 75 * 2;
        }

        int space = -(this.BLOCK_SIZE / 2);
        Point3D p1 = new Point3D(space, space, space);
        Point3D p2 = new Point3D(boxWidth + space, space, space);
        Point3D p3 = new Point3D(space, boxHeight + space, space);
        Point3D p4 = new Point3D(boxWidth + space, boxHeight + space, space);
        Point3D p5 = new Point3D(space, space, boxDepth + space);
        Point3D p6 = new Point3D(boxWidth + space, space, boxDepth + space);
        Point3D p7 = new Point3D(space, boxHeight + space, boxDepth + space);
        Point3D p8 = new Point3D(boxWidth + space, boxHeight + space, boxDepth + space);

        // Create lines between the points
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

    /**
     * Create a line in 3D space between two points.
     *
     * @param origin The starting point of the line.
     * @param target The ending point of the line.
     */
    public void createLine(Point3D origin, Point3D target) {

        double width = 2.0;

        // Defines the y-axis direction
        Point3D yPoint = new Point3D(0, 1, 0);

        // Calculates the vector difference between the target and the origin
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        // Calculates and moves the line to the midpoint between origin and target
        Point3D mid = target.midpoint(origin);
        Translate moveToOriginCenter = new Translate(mid.getX(), mid.getY(), mid.getZ());

        // Calculate the rotation axis that is perpendicular to the line direction
        Point3D rAxis = diff.crossProduct(yPoint);
        double angle = Math.acos(diff.normalize().dotProduct(yPoint));

        // Rotation to align the line with the vector direction
        Rotate rotate = new Rotate(-Math.toDegrees(angle), rAxis);

        Cylinder line = new Cylinder(width, height);

        // Apply translation and rotation to the line
        line.getTransforms().addAll(moveToOriginCenter, rotate);
        this.result.getChildren().add(line);
    }

    /**
     * Answer to Question A (Not fillable).
     *
     * @return a 3D array representing the answer.
     */
    public int[][][] QuestionA() {
        System.out.println("Question A Not fillable");
        return new int[8][33][5];
    }

    /**
     * Answer to Question B using the Greedy Algorithm.
     *
     * @return a 3D array representing the answer.
     */
    public int[][][] QuestionB() {
        GreedyAlgorithm greedy = new GreedyAlgorithm();
        greedy.fillTruck();
        greedy.printScore();
        return greedy.truck;
    }

    /**
     * Answer to Question C using the Brute Force Algorithm.
     *
     * @return a 3D array representing the answer.
     */
    public int[][][] QuestionC() {
        BruteForceAlgorithm b = new BruteForceAlgorithm();
        b.search();

        int[][] arr = b.ans;
        int[][][] answer = new int[8][33][5];
        int parcel1 = 0;
        int parcel2 = 0;
        int parcel3 = 0;

        // Changes the pentominoes values from Phase 1 to the current parcel values
        for (int z = 0; z < answer.length; z++) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    if (arr[i][j] == 8) {
                        answer[z][i][j] = 1;
                        parcel1++;
                    }
                    if (arr[i][j] == 3) {
                        answer[z][i][j] = 2;
                        parcel2++;
                    }
                    if (arr[i][j] == 9) {
                        answer[z][i][j] = 3;
                        parcel3++;
                    }
                }
            }
        }
        System.out.println("Question C pentominoes used : " +  parcel1/5 + "," + parcel2/5 + " and " + parcel3/5);
        return answer;
    }

    /**
     * Answer to Question D using the Greedy Pentominoes Algorithm.
     *
     * @return a 3D array representing the answer.
     */
    public int[][][] QuestionD() {
        GreedyPentominoes greedy = new GreedyPentominoes();
        greedy.fillTruck();
        greedy.printScore();
        return greedy.truck;
    }

    /**
     * The main entry point for the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String args[]) {
        launch(args);
    }
}