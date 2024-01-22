
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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

    private int layers = 8;

    private double lastX;
    private double lastY;

    private final int BLOCK_SIZE = 30;
    private final int OFFSET = 0;
    private Group result = new Group();
    private int[][][] curr;
    private boolean B= false;
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
        
        
        this.result.translateXProperty().set(WIDTH / 2);
        this.result.translateYProperty().set(HEIGHT / 2);
        this.camera = new PerspectiveCamera();

       Scene scene= new Scene(this.result, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {

                if(e.getCode() == KeyCode.A){
                    JavaFX1.this.drawParcel(arr1, JavaFX1.this.result, false);
                    JavaFX1.this.curr = arr1;
                    JavaFX1.this.B = false;
                    System.out.println("Displaying result question A");
                } 
                if(e.getCode() == KeyCode.B){
                    JavaFX1.this.drawParcel(arr2, JavaFX1.this.result, true);
                    JavaFX1.this.curr = arr2;
                    JavaFX1.this.B = true;
                    System.out.println("Displaying result question B");

                }
                if(e.getCode() == KeyCode.C){
                    JavaFX1.this.drawParcel(arr3, JavaFX1.this.result, false);
                    JavaFX1.this.curr = arr3;
                    JavaFX1.this.B = false;
                    System.out.println("Displaying result question C");
                }
                if(e.getCode() == KeyCode.D){
                    JavaFX1.this.drawParcel(arr4, JavaFX1.this.result, false);
                    JavaFX1.this.curr = arr4;
                    JavaFX1.this.B = false;
                    System.out.println("Displaying result question D");
                }

                if(e.getCode() == KeyCode.BACK_SPACE){
                    if(JavaFX1.this.layers != 0){
                        JavaFX1.this.layers--;
                        JavaFX1.this.drawParcel(JavaFX1.this.curr, JavaFX1.this.result, JavaFX1.this.B);
                    }
                    
                } 
                if(e.getCode() == KeyCode.L){
                    if(JavaFX1.this.layers != 8){
                        JavaFX1.this.layers++;
                        JavaFX1.this.drawParcel(JavaFX1.this.curr, JavaFX1.this.result, JavaFX1.this.B);
                    }
                    
                } 


            }
            
        });
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        scene.setCamera(this.camera);
        
        stage.show();
        this.enableMouseInteraction(scene, this.result);
        this.createContainerOutlines(false);
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

    private void drawParcel(int[][][] array, Group group, boolean B) {

        group.getChildren().clear();

        this.layers = this.layers;

        int arrayL = array[0][0].length;
        int array1L = this.layers;

        if(B){
            arrayL = this.layers;
            array1L = array.length;
        }
        

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
                    box.setTranslateX((k * this.BLOCK_SIZE) + (k * this.OFFSET));
                    box.setTranslateY((j * this.BLOCK_SIZE) + (j * this.OFFSET));
                    box.setTranslateZ((i * this.BLOCK_SIZE) + (i * this.OFFSET));
                    
                
                    
                    group.getChildren().add(box);
                }
            }
        }
        this.createContainerOutlines(B);
        group.getChildren().add(new AmbientLight());
    }

    public void createContainerOutlines(boolean B) {

        int boxWidth = 75 * 2;
        int boxHeight = 495 * 2;
        int boxDepth = 120 * 2;

        if(B){
            boxWidth = 120*2;
            boxDepth = 75*2;
        }

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
        this.result.getChildren().add(new AmbientLight());
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