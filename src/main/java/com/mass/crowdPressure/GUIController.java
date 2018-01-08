package com.mass.crowdPressure;

import com.mass.crowdPressure.builders.PedestriansFactory;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.map.Map;
import com.mass.crowdPressure.model.map.StraightWall;
import com.mass.crowdPressure.model.map.Wall;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class GUIController implements Initializable {


    private static final double COLOR_OPACITY = 1.0;
    private static final double COLOR_BLUE = 0.0;
    private Timeline simLoop;
    private GraphicsContext gc;
    private Engine engine;

    @FXML   public MenuItem menuNew;
    @FXML   public MenuItem menuQuit;
    @FXML   public Button btnPauseStart;
    @FXML   public Button btnNextStep;
    @FXML   public ComboBox<String> actionComboBox;
    @FXML   public Slider fpsSlider;
    @FXML   public Label lblSliderVal;
            private int fps = 40;
    @FXML   public ScrollPane scrollPane;
    @FXML   public Canvas canvas;


    public void initialize(URL url, ResourceBundle resourceBundle) {

        engine = Initializer.createEngine(Configuration.SYMULATION_TYPE);

        actionComboBox.getItems().removeAll(actionComboBox.getItems());
        actionComboBox.getItems().addAll("Add wall", "Add Pedestrian");
        actionComboBox.getSelectionModel().select("Add Pedestrian");

        btnPauseStart.setText("Start");
        fpsSlider.setValue(this.fps);
        lblSliderVal.setText(String.format("%d", this.fps));

        initializeCanvas();
        setSliderListener();

        buildAndSetUpSimulationLoop(this.fps);

        drawCoordinateSystem();
        drawMap(engine.getEnvironment().getMap());
    }

    @FXML
    private void chooseAction(){
        System.out.println(actionComboBox.getValue());
    }

    @FXML
    private void pauseStartSim() {
        switch (simLoop.getStatus()) {
            case RUNNING:
                simLoop.pause();
                btnPauseStart.setText("Start");
                btnNextStep.setDisable(false);
                break;
            case PAUSED:
            case STOPPED:
                simLoop.play();
                btnPauseStart.setText("Pause");
                btnNextStep.setDisable(true);
                break;
        }
    }

    @FXML
    public void getNextStep() {
        Duration duration = Duration.millis(1000 / (float) fps);
        KeyFrame frame = getNextFrame(duration);
        Timeline loop = new Timeline();
        int cycleCount = 1;
        loop.setCycleCount(cycleCount);
        loop.getKeyFrames().add(frame);
        loop.play();
    }

    @FXML
    public void exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) btnPauseStart.getScene().getWindow();
            stage.close();
        } else {
            e.consume();
        }
    }

    @FXML
    public void clearSym(ActionEvent e) {
        Parent root2 = null;
        try {
            root2 = FXMLLoader.load(Main.class.getResource("App.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        btnPauseStart.getScene().setRoot(root2);
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

            switch (actionComboBox.getSelectionModel().getSelectedIndex()){
                case 0:
                    //add wall
                    break;
                case 1:
                    gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);
                    new PedestriansFactory().addPedestrian(
                            engine.getEnvironment(), new Position(descale(posX), descale(posY)), null
                    );
                    break;
            }
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

    private void changeFps(int fps){
        Animation.Status status = simLoop.getStatus();
        simLoop.stop();
        simLoop.getKeyFrames().clear();
        buildAndSetUpSimulationLoop(fps);
        if(status == Animation.Status.RUNNING) {
            simLoop.play();
        }

    }

    private void buildAndSetUpSimulationLoop(int fps) {
        Duration duration = Duration.millis(1000 / (float) fps);
        KeyFrame frame = getNextFrame(duration);
        simLoop = new Timeline();
        int cycleCount = Animation.INDEFINITE;
        simLoop.setCycleCount(cycleCount);
        simLoop.getKeyFrames().add(frame);
    }

    private KeyFrame getNextFrame(Duration duration) {
        return new KeyFrame(duration, e -> {
            try {
                clearAll();
                drawCoordinateSystem();
                System.out.print("");
                drawMap(engine.getEnvironment().getMap());
                drawPedestrians(engine.getEnvironment().getPedestrians());
                engine.nextState();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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

    private void clearAll() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
