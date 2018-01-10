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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    private static final double COLOR_OPACITY = 1.0;
    private static final double COLOR_BLUE = 0.0;

    private int FPS = 40;
    private Symulation SIMULATION_TYPE;
    private int SCALE_VALUE = 10;
    private Position DESTINATION = Configuration.DEFAULT_DESTINATION_POSITION;

    private Timeline simLoop;
    private GraphicsContext gc;
    private Engine engine;
    private ArrayList<Double> wallPos = new ArrayList<>(2);

    @FXML
    public MenuItem menuNew;
    @FXML
    public MenuItem menuQuit;
    @FXML
    public Button btnPauseStart;
    @FXML
    public Button btnNextStep;
    @FXML
    public ComboBox<String> cbAction;
    @FXML
    public ComboBox<String> cbSym;
    @FXML
    public Slider fpsSlider;
    @FXML
    public Label lblSliderVal;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Canvas canvas;
    @FXML
    public Button btnPlus;
    @FXML
    public Button btnMinus;


    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbAction.getItems().removeAll(cbAction.getItems());
        cbAction.getItems().addAll("Add wall", "Add Pedestrian");
        cbAction.getSelectionModel().select("Add Pedestrian");

        cbSym.getItems().removeAll(cbSym.getItems());
        cbSym.getItems().addAll(
                "SYM_P0_W0",
                "SYM_P0_W1",
                "SYM_P2_W0",
                "SYM_P1_W1",
                "SYM_P1_W2",
                "SYM_ROOM",
                "SYM_ROOM_OBSTACLE1",
                "SYM_ROOM_PERP_WALL"
        );
        cbSym.getSelectionModel().select("SYM_ROOM_OBSTACLE1");
        SIMULATION_TYPE = Symulation.SYM_ROOM_OBSTACLE1;

        engine = Initializer.createEngine(SIMULATION_TYPE);

        btnPauseStart.setText("Start");
        fpsSlider.setValue(this.FPS);
        lblSliderVal.setText(String.format("%d", this.FPS));

        setSliderListener();
        setCbSymListener();
        setCanvasListener();

        drawCanvasSimulation();
    }

    @FXML
    public void chooseAction() {
        System.out.println(cbAction.getValue());
    }

    @FXML
    public void chooseSym() {
        System.out.println(cbSym.getValue());
    }

    @FXML
    public void pauseStartSim() {
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
        Duration duration = Duration.millis(1000 / (float) FPS);
        KeyFrame frame = getNextFrame(duration);
        Timeline loop = new Timeline();
        int cycleCount = 1;
        loop.setCycleCount(cycleCount);
        loop.getKeyFrames().add(frame);
        loop.play();
    }

    @FXML
    public void enlargeView() {
        SCALE_VALUE = SCALE_VALUE + 2;
        clearCanvas();
        drawCanvasSimulation();
    }

    @FXML
    public void lessenView() {
        SCALE_VALUE = SCALE_VALUE >= 4 ? SCALE_VALUE - 2 : SCALE_VALUE;
        clearCanvas();
        drawCanvasSimulation();
    }

    @FXML
    public void exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
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

    private void drawCanvasSimulation() {
        initializeCanvas();
        buildAndSetUpSimulationLoop(this.FPS);

        drawCoordinateSystem();
        drawMap(engine.getEnvironment().getMap());
        drawPedestrians(engine.getEnvironment().getPedestrians());
        drawDestination();
    }

    private void initializeCanvas() {
        canvas.setHeight(1000);
        canvas.setWidth(1000);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(-50);
        canvas.setLayoutX(-10);
        canvas.setScaleX(1);
        canvas.setScaleY(-1);
    }

    private void setCanvasListener() {
        canvas.setOnMouseClicked(event -> {
            double posX = event.getX();
            double posY = event.getY();
            if (wallPos.isEmpty()) {
                wallPos.add(0, posX);
                wallPos.add(1, posY);
            }
            double posX1 = wallPos.get(0);
            double posY1 = wallPos.get(1);

            System.out.println(posX + " x " + posY);

            switch (cbAction.getSelectionModel().getSelectedIndex()) {
                case 0:
                    if ((posX1 != posX) || (posY1 != posY)) {
                        setWall(new Position(posX, posY), new Position(posX1, posY1));
                        System.out.println("Wall created...");
                    }
                    System.out.println("Need another click to create wall...");
                    break;
                case 1:
                    setPedestrian(posX, posY);
                    break;
            }
        });
    }

    private void setWall(Position pos1, Position pos2) {

    }

    private void setPedestrian(double posX, double posY) {
        gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);
        new PedestriansFactory().addPedestrian(
                engine.getEnvironment(), new Position(descale(posX), descale(posY)), DESTINATION
        );
    }

    private void setSliderListener() {
        fpsSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            fpsSlider.setValue(new_val.intValue());
            lblSliderVal.setText(String.format("%d", new_val.intValue()));
            FPS = (int) fpsSlider.getValue();
            changeFps(FPS);
        });
    }

    private void changeFps(int fps) {
        Animation.Status status = simLoop.getStatus();
        simLoop.stop();
        simLoop.getKeyFrames().clear();
        buildAndSetUpSimulationLoop(fps);
        if (status == Animation.Status.RUNNING) {
            simLoop.play();
        }

    }

    private void setCbSymListener() {
        cbSym.valueProperty().addListener((ov, old_val, new_val) -> {
            switch (new_val) {
                case "SYM_P0_W0":
                    changeSymType(Symulation.SYM_P0_W0);
                    break;
                case "SYM_P0_W1":
                    changeSymType(Symulation.SYM_P0_W1);
                    break;
                case "SYM_P2_W0":
                    changeSymType(Symulation.SYM_P2_W0);
                    break;
                case "SYM_P1_W1":
                    changeSymType(Symulation.SYM_P1_W1);
                    break;
                case "SYM_P1_W2":
                    changeSymType(Symulation.SYM_P1_W2);
                    break;
                case "SYM_ROOM":
                    changeSymType(Symulation.SYM_ROOM);
                    break;
                case "SYM_ROOM_OBSTACLE1":
                    changeSymType(Symulation.SYM_ROOM_OBSTACLE1);
                    break;
                case "SYM_ROOM_PERP_WALL":
                    changeSymType(Symulation.SYM_ROOM_PERP_WALL);
                    break;
            }
        });
    }

    private void changeSymType(Symulation symType) {
        simLoop.pause();
        btnPauseStart.setText("Start");
        btnNextStep.setDisable(false);

        this.SIMULATION_TYPE = symType;
        engine = Initializer.createEngine(this.SIMULATION_TYPE);

        clearCanvas();
        drawCanvasSimulation();
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
                clearCanvas();
                drawCoordinateSystem();
                System.out.print("");
                drawMap(engine.getEnvironment().getMap());
                drawPedestrians(engine.getEnvironment().getPedestrians());
                drawDestination();
                engine.nextState();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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

    private double descale(double value) {
        if (value == 0)
            return 0;
        return value / SCALE_VALUE;
    }

    private double scale(double value) {
        return value * SCALE_VALUE;
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawPedestrians(List<Pedestrian> pedestrians) {
        for (Pedestrian p : pedestrians) {

            Color pedestrianColor = getPedestrianColor(
                    p.getPedestrianInformation().getVariableInformation().getCrowdPressure()
            );

            gc.setFill(pedestrianColor);
            double x = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getX());
            double y = scale(p.getPedestrianInformation().getVariableInformation().getPosition().getY());
            double radius = scale(p.getPedestrianInformation().getStaticInformation().getRadius());

            gc.fillArc(x, y, 4, 3, 0, 360, ArcType.OPEN);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    private Color getPedestrianColor(double x) {
        double red = (x > 0.5 ? 1.0 : 2 * x / 1.0);
        double green = (x > 0.5 ? 1 - 2 * (x - 0.5) / 1.0 : 1.0);

        return new Color(red, green, COLOR_BLUE, COLOR_OPACITY);
    }

    private void drawDestination () {
        double x = scale(DESTINATION.getX());
        double y = scale(DESTINATION.getY());
        gc.setFill(Color.BLACK);
        gc.fillArc(x, y, 5, 5, 0, 360, ArcType.ROUND);
    }

}
