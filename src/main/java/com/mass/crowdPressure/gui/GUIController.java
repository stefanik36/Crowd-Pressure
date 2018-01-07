package com.mass.crowdPressure.gui;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.Configuration;
import com.mass.crowdPressure.Engine;
import com.mass.crowdPressure.Initializer;
import com.mass.crowdPressure.builders.PedestriansFactory;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    private static final COD cod = CODFactory.getCOD();
    private static final double COLOR_OPACITY = 1.0;
    private static final double COLOR_BLUE = 0.0;
    private Timeline simLoop;
    private GraphicsContext gc;
    private Engine engine;
    private int windowWidth;
    private int windowHeight;


    @FXML   public Button pauseStartButton;
    @FXML   public ComboBox<String> actionComboBox;
    @FXML   public Slider fpsSlider;
    @FXML   public Label lblSliderVal;
            private int fps = 40;
    @FXML   public ScrollPane scrollPane;
    @FXML   public Canvas canvas;


    @FXML
    private void chooseAction(){
        System.out.println(actionComboBox.getValue());
    }

    @FXML
    private void pauseStartSim() {
        switch (simLoop.getStatus()) {
            case RUNNING:
                simLoop.pause();
                pauseStartButton.setText("Start");
                break;
            case PAUSED:
            case STOPPED:
                simLoop.play();
                pauseStartButton.setText("Pause");
                break;
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        engine = Initializer.createEngine(Configuration.SYMULATION_TYPE);

        actionComboBox.getItems().removeAll(actionComboBox.getItems());
        actionComboBox.getItems().addAll("Nothing", "Add wall", "Add Pedestrian");
        actionComboBox.getSelectionModel().select("Nothing");

        pauseStartButton.setText("Start");
        fpsSlider.setValue(this.fps);
        lblSliderVal.setText(String.format("%d", this.fps));

        initializeCanvas();
        setSliderListener();

        buildAndSetUpSimulationLoop(this.fps);

        drawCoordinateSystem();
        drawMap(engine.getEnvironment().getMap());
    }

    private void initializeCanvas() {
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(-50);
        canvas.setLayoutX(-10);
        canvas.setScaleX(1);
        canvas.setScaleY(-1);

        canvas.setOnMouseClicked(event -> {
            double posX = event.getX();
            double posY = event.getY();

            System.out.println(posX + " x " + posY);
            gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);
            new PedestriansFactory().addPedestrian(engine.getEnvironment(), new Position(descale(posX), descale(posY)), null);
        });
    }

    private void setSliderListener(){
        fpsSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            fpsSlider.setValue(new_val.intValue());
            lblSliderVal.setText(String.format("%d", new_val.intValue()));
            fps = (int) fpsSlider.getValue();
            changeFps(fps);
        });
    }

    public void changeFps(int fps){
        simLoop.stop();
        simLoop.getKeyFrames().clear();
        buildAndSetUpSimulationLoop(fps);
        simLoop.play();

    }

    private double descale(double value) {
        if (value == 0)
            return 0;
        return value / Configuration.SCALE_VALUE;
    }

    private void drawCoordinateSystem() {
        gc.strokeLine(0, 0, 1000, 5);
        gc.strokeLine(0, 0, 5, 1000);
    }

    private void drawMap(Map map) {
        for (Wall w : map.getWalls()) {
            Position start = ((StraightWall) w).getStartPosition();
            Position end = ((StraightWall) w).getEndPosition();
            gc.strokeLine(scale(start.getX()), scale(start.getY()), scale(end.getX()), scale(end.getY()));
        }
    }

    private double scale(double value) {
        return value * Configuration.SCALE_VALUE;
    }

    private void buildAndSetUpSimulationLoop(int fps) {
        Duration duration = Duration.millis(1000 / (float) fps);
        System.out.print("");
        KeyFrame frame = new KeyFrame(duration, actionEvent -> {
            try {
                System.out.print("");
                clearAll();
                drawCoordinateSystem();
                drawMap(engine.getEnvironment().getMap());
                drawPedestrians(engine.getEnvironment().getPedestrians());
                engine.nextState();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        simLoop = new Timeline();
        int cycleCount = Animation.INDEFINITE;
        simLoop.setCycleCount(cycleCount);
        simLoop.getKeyFrames().add(frame);
    }

    public void clearAll() {
        gc.clearRect(0, 0, windowWidth, windowHeight);
    }

    private void drawPedestrians(List<Pedestrian> pedestrians) {
        for (Pedestrian p : pedestrians) {
            double x = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getX());
            double y = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getY());
            double radius = scale(p.getPedestrianInformation().getStaticInformation().getRadius());

            gc.fillArc(x, y, 4, 3, 0, 360, ArcType.OPEN);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

            Color pedestrianColor = getPedestrianColor(
                    p.getPedestrianInformation().getVariableInformation().getCrowdPressure());

            gc.setFill(pedestrianColor);
        }
    }

    private Color getPedestrianColor(double x) {
        double red = (x > 0.5 ? 1.0 : 2*x/1.0);
        double green = (x > 0.5 ? 1-2*(x-0.5)/1.0 : 1.0);

        return new Color(red, green, COLOR_BLUE, COLOR_OPACITY);
    }
}
