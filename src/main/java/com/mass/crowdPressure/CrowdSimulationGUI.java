package com.mass.crowdPressure;

import com.mass.crowdPressure.calculators.Configuration;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;


public class CrowdSimulationGUI {

    private Group root;
    private Timeline simLoop;

    private int fps;
    private String windowTitle;
    private GraphicsContext gc;
    private Engine engine;
    private int windowWidth;
    private int windowHigh;


    public CrowdSimulationGUI(String title, int fps, int windowHigh, int windowWidth) {
        windowTitle = title;
        this.windowHigh = windowHigh;
        this.windowWidth = windowWidth;
        this.fps = fps;
        buildAndSetUpSimulationLoop();
    }

    private void buildAndSetUpSimulationLoop() {

        Duration duration = Duration.millis(1000 / (float) this.fps);

        KeyFrame frame = new KeyFrame(duration,
                actionEvent -> {
                    try {
                        //clearAll();
                        drawMap(engine.getEnvironment().getMap());
                        drawPedestrians(engine.getEnvironment().getPedestrians());
                        engine.nextState();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });

        simLoop = new Timeline();
        simLoop.setCycleCount(Configuration.STEPS);
        simLoop.getKeyFrames().add(frame);
    }

    public void clearAll() {
        gc.clearRect(0, 0, windowWidth, windowWidth);
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
            gc.fillArc(x, y, 8, 8, 0, 360, ArcType.OPEN);
        }
        gc.setFill(Color.BLACK);
    }

    private double scale(double value) {
        return value * 100;
    }


    public void initialize(Stage primaryStage) throws IOException {
        primaryStage.setTitle(windowTitle);
        root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(windowWidth, windowHigh);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0, 0, 1000, 5);
        gc.strokeLine(0, 0, 5, 1000);

        canvas.setLayoutY(50);
        canvas.setLayoutX(10);

        buildMenu();
        primaryStage.show();
    }


    private void buildMenu() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CrowdSimulationGUI.class.getResource("menu.fxml"));
        Parent menu = (Parent) fxmlLoader.load();

        MenuController controller = fxmlLoader.<MenuController>getController();
        controller.setGraphicContext(gc);
        controller.setSimLoop(simLoop);

        root.getChildren().add(menu);
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

}
