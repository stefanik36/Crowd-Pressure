package com.mass.crowdPressure.gui;

import com.mass.crowdPressure.Engine;
import com.mass.crowdPressure.builders.PedestriansFactory;
import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;


public class CrowdSimulationGUI {
    private int cycleCount = Animation.INDEFINITE;
    private Group root;
    private Timeline simLoop;
    private MenuController menu;
    private int fps;
    private String windowTitle;
    private GraphicsContext gc;
    private Engine engine;
    private int windowWidth;
    private int windowHigh;


    public CrowdSimulationGUI(String title, int fps, int windowHigh, int windowWidth) {
        this.windowTitle = title;
        this.windowHigh = windowHigh;
        this.windowWidth = windowWidth;
        this.fps = fps;
        buildAndSetUpSimulationLoop();
    }

    public CrowdSimulationGUI(String title, int fps, int windowWidth, int windowHigh, int cycleCount){
        this(title, fps, windowHigh, windowWidth);
        this.cycleCount = cycleCount;
    }

    private void buildAndSetUpSimulationLoop() {

        Duration duration = Duration.millis(1000 / (float) this.fps);

        KeyFrame frame = new KeyFrame(duration,
                actionEvent -> {
                    try {
                        //clearAll();
                        drawCoordinateSystem();
                        drawMap(engine.getEnvironment().getMap());
                        drawPedestrians(engine.getEnvironment().getPedestrians());
                        engine.nextState();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        simLoop = new Timeline();
        simLoop.setCycleCount(cycleCount);
        simLoop.getKeyFrames().add(frame);
    }

    public void clearAll() {
        gc.clearRect(0, 0, windowWidth, windowWidth);
    }

    private void drawCoordinateSystem(){
        gc.strokeLine(0, 0, 1000, 5);
        gc.strokeLine(0, 0, 5, 1000);
    }

    private void drawMap(Map map) {
        for (Wall w : map.getWalls()) {
            Position start = ((StraightWall) w).getStartPosition();
            Position end = ((StraightWall) w).getEndPosition();
            gc.strokeLine(scale(start.getX()), scale(start.getY()),
                    scale(end.getX()), scale(end.getY()));
        }
    }

    private void drawPedestrians(List<Pedestrian> pedestrians) {
        gc.setFill(Color.RED);
        for (Pedestrian p : pedestrians) {
            double x = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getX());
            double y = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getY());
            gc.fillArc(x, y, 4, 3, 0, 360, ArcType.OPEN);
        }
        gc.setFill(Color.BLACK);
    }

    private double descale(double value){
        if (value == 0) return 0;
        return value / 100;
    }

    private double scale(double value) {
        return value * 100;
    }


    public void initialize(Stage primaryStage) throws IOException, InterruptedException {
        primaryStage.setTitle(windowTitle);
        root = new Group();
        Scene theScene = new Scene(root, windowWidth, windowHigh);
        primaryStage.setScene(theScene);

        initializeCanvas();
        initializeMenu();
        primaryStage.show();

    }

    private void initializeCanvas(){
        Canvas canvas = new Canvas(windowWidth-10, windowHigh-50);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(50);
        canvas.setLayoutX(10);

        canvas.setOnMouseClicked(event->{
            try {
                double posX = descale(event.getSceneX() - canvas.getLayoutX());
                double posY = descale(event.getSceneY() - canvas.getLayoutY());

                System.out.println((event.getSceneX() - canvas.getLayoutX()) + " " + (event.getSceneY() - canvas.getLayoutY()));
                gc.fillArc(event.getSceneX() - canvas.getLayoutX(), event.getSceneY() - canvas.getLayoutY(), 5, 5, 0, 360, ArcType.OPEN);
                new PedestriansFactory().addPedestrian(engine.getEnvironment(),posX,posY);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(CrowdSimulationGUI.class.getResource("pedestrian.fxml"));
                PedestrianCreationController controller = fxmlLoader.getController();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//                Stage stage = new Stage();
//                stage.setTitle("Pedestrian Creator");
//                stage.setScene(scene);
//                stage.show();
            } catch (IOException e) {
//                Logger logger = Logger.getLogger(getClass().getName());
//                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });
    }

    private void initializeMenu() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CrowdSimulationGUI.class.getResource("menu.fxml"));
        Parent menuP = fxmlLoader.load();
        menu = fxmlLoader.getController();
        menu.setGraphicContext(gc);
        menu.setSimLoop(simLoop);
        menu.setRoot(root);

        root.getChildren().add(menuP);
    }


    public void start() {
        simLoop.play();
    }

    public void stop() {
        simLoop.stop();
    }


    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }
}
