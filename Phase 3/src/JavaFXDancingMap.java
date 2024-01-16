import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import DancingMap.Dancing3DMap;
import SuperParcels.ParcelF;

public class JavaFXDancingMap extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    private Group group; // Move this line to the top of the class
    private PerspectiveCamera camera;
    private AmbientLight ambientLight;

    private double lastX;
    private double lastY;

    private final int BLOCK_SIZE = 30;
    private final int OFFSET = 0;
    //private final int width = 75; // Move these declarations above their usage
    //private final int height = 120;
    //private final int depth = 495;

    @Override
    public void start(Stage stage) throws Exception {

        group = new Group();

        // Creating a scene object colored in black
        camera = new PerspectiveCamera();
        ambientLight = new AmbientLight(Color.WHITE);
        group.getChildren().add(ambientLight);

        Scene scene = new Scene(group, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.WHITE);
        scene.setCamera(camera);

        // Set truck's position to the center of the GUI
        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);

        /*
        GreedyAlgorithm greedy = new GreedyAlgorithm();
        greedy.fillTruck();
        drawParcel(greedy.truck);
        createContainerOutlines();
        System.out.println(greedy.score);
        */
        Dancing3DMap map = new Dancing3DMap(5, 33, 8);
        ParcelF newParcelF = new ParcelF();
        newParcelF.rotate();
        newParcelF.rotate();
        newParcelF.attemptToPlace(map.getRoot().getRight());
        drawParcel(map.toTruckIntArray());
        createContainerOutlines();

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

            // Apply rotation transformations to the group (truck, parcels, and outlines)
            group.getTransforms().addAll(rotateX, rotateY);

            lastX = event.getSceneX();
            lastY = event.getSceneY();
        });
    }

    private PhongMaterial getColor(int value) {
        final PhongMaterial material = new PhongMaterial();

        switch(value) {
            case -1:
                material.setDiffuseColor(new Color(0.2, 0.6, 0.5, 0.2));
                break;
            case 1:
                material.setDiffuseColor(Color.RED);
                break;
            case 2:
                material.setDiffuseColor(Color.BLUE);
                break;
            case 3:
                material.setDiffuseColor(Color.PURPLE);
                break;
            case 7:
                material.setDiffuseColor(Color.GOLD);
                break;
            default:
                material.setDiffuseColor(Color.GREEN);
        }
        return material;
    }

    private void drawParcel(int[][][] array) {

        group.getChildren().clear();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int k = 0; k < array[i][j].length; k++) {
                    if (array[i][j][k] == 0)
                        continue;

                    PhongMaterial color = getColor(array[i][j][k]);
                    Box box = new Box(30, 30, 30);
                    box.setMaterial(color);
                    box.setTranslateX((k * BLOCK_SIZE) + (k * OFFSET));
                    box.setTranslateY((j * BLOCK_SIZE) + (j * OFFSET));
                    box.setTranslateZ((i * BLOCK_SIZE) + (i * OFFSET));
                    group.getChildren().add(box);
                }
            }
        }
    }

    public void createContainerOutlines() {

        int boxWidth = 120 * 2;
        int boxHeight = 495 * 2;
        int boxDepth = 75 * 2;
        int offset = -(BLOCK_SIZE/2);
        Point3D p1 = new Point3D(offset, offset, offset);
        Point3D p2 = new Point3D(boxWidth + offset, offset, offset);
        Point3D p3 = new Point3D(offset, boxHeight + offset, offset);
        Point3D p4 = new Point3D(boxWidth + offset, boxHeight + offset, offset);
        Point3D p5 = new Point3D(offset, offset, boxDepth + offset);
        Point3D p6 = new Point3D(boxWidth + offset, offset, boxDepth + offset);
        Point3D p7 = new Point3D(offset, boxHeight + offset, boxDepth + offset);
        Point3D p8 = new Point3D(boxWidth + offset, boxHeight + offset, boxDepth + offset);

        createLine(p1, p2);
        createLine(p1, p3);
        createLine(p1, p5);
        createLine(p2, p6);
        createLine(p2, p4);
        createLine(p3, p4);
        createLine(p3, p7);
        createLine(p4, p8);
        createLine(p5, p6);
        createLine(p5, p7);
        createLine(p6, p8);
        createLine(p7, p8);
    }

    public void createLine(Point3D origin, Point3D target) { // Math taken from math stackexchange question #42984225

        double width = 0.5;

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
        group.getChildren().add(line);
    }

    public static void main(String args[]) {
        launch(args);
    }
}