import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Visualizer extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    private Box truck;
    private PerspectiveCamera camera;

    private double lastX;
    private double lastY;

    private double scale;
    private Group group;
    private Group parcelsGroup;
    private Box newBox;

    @Override
    public void start(Stage stage) throws Exception {
        // Drawing a box to represent the Truck
        int b = 55;
        
        int[][][] parcelB = new int[4][4][8];


        truck = new Box();
        int[][][] truckArray = new int[33][8][5];
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 8; j++) {
                for (int r = 0; r < 5; r++) {
                    if (truckArray[i][j][r] == 0) {
                        addParcel(i, j, r);
                    }
                }
            }
        }

        // Setting the properties of the Truck
        scale = 70.0;
        truck.setWidth(16.5 * scale);
        truck.setHeight(4.0 * scale);
        truck.setDepth(2.5 * scale);

        PhongMaterial glassMaterial = new PhongMaterial(Color.color(1, 1, 1, 0.6));
        truck.setMaterial(glassMaterial);

        // Creating a Group object
        group = new Group();
        parcelsGroup = new Group();
        group.getChildren().addAll(truck, parcelsGroup);

        // Creating a scene object colored in black
        camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        // Set truck's position to the center of the GUI 
        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);

        drawParcel(3, 0, 0, 0);
        drawParcel(5, 70, 0, 0);
        drawParcel(4, 140, 0, 0);
        double height = newBox.getHeight();
        System.out.println(height);

        // Setting title to the Stage
        stage.setTitle("Truck Visualizer");
        // Adding scene to the stage
        stage.setScene(scene);
        // Displaying the contents of the stage
        stage.show();
        // Enable mouse interaction for rotating the box
        enableMouseInteraction(scene);
    }

    private void enableMouseInteraction(Scene scene) {
        scene.setOnMousePressed(event -> {
            lastX = event.getSceneX();
            lastY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastX;
            double deltaY = event.getSceneY() - lastY;

            double deltaXAngle = deltaX / scene.getWidth() * 360;
            double deltaYAngle = deltaY / scene.getHeight() * 360;

            Rotate rotateX = new Rotate(deltaYAngle, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(deltaXAngle, Rotate.Y_AXIS);

            truck.getTransforms().addAll(rotateX, rotateY);
            parcelsGroup.getTransforms().addAll(rotateX, rotateY);

            lastX = event.getSceneX();
            lastY = event.getSceneY();
        });
    }

    private javafx.scene.paint.PhongMaterial createMaterial(Color color) {
        javafx.scene.paint.PhongMaterial material = new javafx.scene.paint.PhongMaterial();
        material.setDiffuseColor(color);
        return material;
    }

    public void drawParcel(int value, double xCoordinate, double yCoordinate, double zCoordinate) {
        newBox = createParcelBox(value, scale);
        newBox.setTranslateX(xCoordinate);
        newBox.setTranslateY(yCoordinate);
        newBox.setTranslateZ(zCoordinate);
        parcelsGroup.getChildren().add(newBox);
    }

    private Box createParcelBox(int value, double scale) {
        Box parcelBox;
        switch (value) {
            case 3:
                parcelBox = new Box(scale, scale, 2 * scale);
                parcelBox.setMaterial(createMaterial(Color.RED));
                break;
            case 4:
                parcelBox = new Box(scale, 1.5 * scale, 2 * scale);
                parcelBox.setMaterial(createMaterial(Color.GREEN));
                break;
            case 5:
                parcelBox = new Box(1.5 * scale, 1.5 * scale, 1.5 * scale);
                parcelBox.setMaterial(createMaterial(Color.PINK));
                break;
            default:
                parcelBox = new Box(scale, scale, scale);
                parcelBox.setMaterial(createMaterial(Color.BLACK));
        }
        return parcelBox;
    }

    public void addParcel(int x, int y, int z){
        for(int i=x; i<33; i++){
            for(int j=y; j<8; j++){
                for(int r=z; r<5; r++){

                }
            }
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}
