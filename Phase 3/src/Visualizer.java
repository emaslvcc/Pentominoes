import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
    private Box newBox;

    @Override
    public void start(Stage stage) throws Exception {
        // Drawing a box to represent the Truck
        truck = new Box();

        // Setting the properties of the Truck
        scale = 70.0;
        truck.setWidth(16.5 * scale);
        truck.setHeight(4.0 * scale);
        truck.setDepth(2.5 * scale);

        PhongMaterial glassMaterial = new PhongMaterial(Color.color(1, 1, 1, 0.6));
        truck.setMaterial(glassMaterial);

        // Creating a single Group object for parcels and outlines
        group = new Group();
        group.getChildren().add(truck);

        // Creating a scene object colored in black
        camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        // Set truck's position to the center of the GUI
        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);

        // Draw parcels without outlines
        drawParcel(3, 0, 0, 0);
        drawParcel(5, 70, 0, 0);
        drawParcel(4, 140, 0, 0);

        // Draw outlines for the truck container
        createOutlines(truck, group);

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

    private javafx.scene.paint.PhongMaterial createMaterial(Color color) {
        javafx.scene.paint.PhongMaterial material = new javafx.scene.paint.PhongMaterial();
        material.setDiffuseColor(color);
        return material;
    }

    private void drawParcel(int value, double xCoordinate, double yCoordinate, double zCoordinate) {
        newBox = createParcelBox(value, scale);
        newBox.setTranslateX(xCoordinate);
        newBox.setTranslateY(yCoordinate);
        newBox.setTranslateZ(zCoordinate);

        group.getChildren().add(newBox);
    }

    private Box createParcelBox(int value, double scale) {
        Box parcelBox;
        switch (value) {
            case 3:
                parcelBox = new Box(scale, scale, 2 * scale);
                parcelBox.setMaterial(createMaterial(Color.RED)); // Fully solid without transparency
                break;
            case 4:
                parcelBox = new Box(scale, 1.5 * scale, 2 * scale);
                parcelBox.setMaterial(createMaterial(Color.GREEN)); // Fully solid without transparency
                break;
            case 5:
                parcelBox = new Box(1.5 * scale, 1.5 * scale, 1.5 * scale);
                parcelBox.setMaterial(createMaterial(Color.PINK)); // Fully solid without transparency
                break;
            default:
                parcelBox = new Box(scale, scale, scale);
                parcelBox.setMaterial(createMaterial(Color.BLACK)); // Fully solid without transparency
        }
        return parcelBox;
    }

    private void createOutlines(Box box, Group group) {
        double width = 0.2; // Adjust the width as needed

        double x = box.getTranslateX();
        double y = box.getTranslateY();
        double z = box.getTranslateZ();
        double w = box.getWidth();
        double h = box.getHeight();
        double d = box.getDepth();

        createLine(group, new Point3D(x - w / 2, y - h / 2, z - d / 2), new Point3D(x + w / 2, y - h / 2, z - d / 2), width);
        createLine(group, new Point3D(x - w / 2, y + h / 2, z - d / 2), new Point3D(x + w / 2, y + h / 2, z - d / 2), width);
        createLine(group, new Point3D(x - w / 2, y - h / 2, z + d / 2), new Point3D(x + w / 2, y - h / 2, z + d / 2), width);
        createLine(group, new Point3D(x - w / 2, y + h / 2, z + d / 2), new Point3D(x + w / 2, y + h / 2, z + d / 2), width);

        createLine(group, new Point3D(x - w / 2, y - h / 2, z - d / 2), new Point3D(x - w / 2, y + h / 2, z - d / 2), width);
        createLine(group, new Point3D(x + w / 2, y - h / 2, z - d / 2), new Point3D(x + w / 2, y + h / 2, z - d / 2), width);
        createLine(group, new Point3D(x - w / 2, y - h / 2, z + d / 2), new Point3D(x - w / 2, y + h / 2, z + d / 2), width);
        createLine(group, new Point3D(x + w / 2, y - h / 2, z + d / 2), new Point3D(x + w / 2, y + h / 2, z + d / 2), width);

        createLine(group, new Point3D(x - w / 2, y - h / 2, z - d / 2), new Point3D(x - w / 2, y - h / 2, z + d / 2), width);
        createLine(group, new Point3D(x + w / 2, y - h / 2, z - d / 2), new Point3D(x + w / 2, y - h / 2, z + d / 2), width);
        createLine(group, new Point3D(x - w / 2, y + h / 2, z - d / 2), new Point3D(x - w / 2, y + h / 2, z + d / 2), width);
        createLine(group, new Point3D(x + w / 2, y + h / 2, z - d / 2), new Point3D(x + w / 2, y + h / 2, z + d / 2), width);
    }

    private void createLine(Group group, Point3D origin, Point3D target, double width) {
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
