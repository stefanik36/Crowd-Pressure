package com.mass.crowdPressure;

import com.app.COD;
import com.app.CODFactory;
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
import java.util.*;


public class GUIController implements Initializable {

    private static final double COLOR_OPACITY = 1.0;
    private static final double COLOR_BLUE = 0.0;
    private static final COD cod = CODFactory.getCOD();

    private int fps;
    private Symulation simulationType;
    private int scaleValue;
    private Position destination;
    private Map map;

    private Timeline simLoop;
    private GraphicsContext gc;
    private Engine engine;
    private ArrayList<Double> wallPos = new ArrayList<>(2);
    private PedestriansFactory pedestriansFactory;


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
        pedestriansFactory = new PedestriansFactory();

        simulationType = Configuration.SYMULATION_TYPE;
        engine = Initializer.createEngine(simulationType,pedestriansFactory);
        map = new Map(engine.getEnvironment().getMap().getWalls());

        wallPos.clear();
        destination = Configuration.DEFAULT_DESTINATION_POSITION;
        scaleValue = 10;
        fps = 100;

        cbAction.getItems().removeAll(cbAction.getItems());
        cbAction.getItems().addAll("Add wall", "Add Pedestrian");
        cbAction.getSelectionModel().select("Add Pedestrian");

        cbSym.getItems().removeAll(cbSym.getItems());

        for(Symulation s : Symulation.values()){
            cbSym.getItems().add(s.toString());
        }
//        cbSym.getItems().addAll(
//                Symulation.SYM_P0_W0.toString(),
//                Symulation.SYM_P0_W1.toString(),
//                Symulation.SYM_P2_W0.toString(),
//                Symulation.SYM_P1_W1.toString(),
//                Symulation.SYM_P1_W2.toString(),
//                Symulation.SYM_ROOM.toString(),
//                Symulation.SYM_ROOM_OBSTACLE1.toString(),
//                Symulation.SYM_ROOM_PERP_WALL.toString(),
//                Symulation.SYM_PX_VS_P1_W0.toString(),
//                Symulation.SYM_PX_VS_PX_W0.toString(),
//                Symulation.SYM_PX_VS_PX_W2.toString()
//        );
        cbSym.getSelectionModel().select(simulationType.toString());

        btnPauseStart.setText("Start");
        fpsSlider.setValue(this.fps);
        lblSliderVal.setText(String.format("%d", this.fps));

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
        Duration duration = Duration.millis(1000 / (float) fps);
        KeyFrame frame = getNextFrame(duration);
        Timeline loop = new Timeline();
        int cycleCount = 1;
        loop.setCycleCount(cycleCount);
        loop.getKeyFrames().add(frame);
        loop.play();
    }

    @FXML
    public void enlargeView() {
        scaleValue = scaleValue + 2;
        clearCanvas();
        drawCanvasSimulation();
    }

    @FXML
    public void lessenView() {
        scaleValue = scaleValue >= 4 ? scaleValue - 2 : scaleValue;
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
        buildAndSetUpSimulationLoop(this.fps);

        drawCoordinateSystem();
        drawMap(map);
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

            System.out.println(posX + " x " + posY);

            switch (cbAction.getSelectionModel().getSelectedIndex()) {
                case 0:
                    if (wallPos.isEmpty()) {
                        wallPos.add(0, posX);
                        wallPos.add(1, posY);
                    }
                    double posX1 = wallPos.get(0);
                    double posY1 = wallPos.get(1);
                    System.out.println(posX1 + " x " + posY1);
                    if ((posX1 != posX) || (posY1 != posY)) {
                        setWall(new Position(descale(posX), descale(posY)), new Position(descale(posX1), descale(posY1)));
                    } else {
                        System.out.println("Need another click to create wall...");
                    }
                    break;
                case 1:
                    setPedestrian(posX, posY);
                    break;
            }
        });
    }

    //FIXME
    private void setWall(Position pos1, Position pos2) {
        Wall newWall = new StraightWall(pos1, pos2);
        List<Wall> currentWalls = map.getWalls();
        currentWalls.add(newWall);
        map = new Map(currentWalls);
        drawMap(map);
        System.out.println("Wall created...");
        wallPos.clear();
    }

    private void setPedestrian(double posX, double posY) {
        gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);

        Pedestrian newP = pedestriansFactory.addPedestrian(
                engine.getEnvironment(), new Position(descale(posX), descale(posY)), destination
        );

        cod.i("new Pedestrian (ID, Position, Destination): " + newP.getPedestrianInformation().getStaticInformation().getId(),
                Arrays.asList(
                        newP.getPedestrianInformation().getVariableInformation().getPosition(),
                        newP.getPedestrianInformation().getVariableInformation().getDestinationPoint())
        );
    }

    private void setSliderListener() {
        fpsSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            fpsSlider.setValue(new_val.intValue());
            lblSliderVal.setText(String.format("%d", new_val.intValue()));
            fps = (int) fpsSlider.getValue();
            changeFps(fps);
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
            for (Symulation s : Symulation.values()) {
//                cod.i("s, n", Arrays.asList(s.toString(),new_val));
                if (s.toString().equals(new_val)) {
                    changeSymType(s);
                    pedestriansFactory = new PedestriansFactory();
                    break;
                }
            }

//            switch (new_val) {
//                case sSYM_P0_W0:
//                    changeSymType(Symulation.SYM_P0_W0);
//                    break;
//                case Symulation.SYM_P0_W1.toString():
//                    changeSymType(Symulation.SYM_P0_W1);
//                    break;
//                case Symulation.SYM_P2_W0.toString():
//                    changeSymType(Symulation.SYM_P2_W0);
//                    break;
//                case Symulation.SYM_P1_W1.toString():
//                    changeSymType(Symulation.SYM_P1_W1);
//                    break;
//                case Symulation.SYM_P1_W2.toString():
//                    changeSymType(Symulation.SYM_P1_W2);
//                    break;
//                case Symulation.SYM_ROOM.toString():
//                    changeSymType(Symulation.SYM_ROOM);
//                    break;
//                case Symulation.SYM_ROOM_OBSTACLE1.toString():
//                    changeSymType(Symulation.SYM_ROOM_OBSTACLE1);
//                    break;
//                case Symulation.SYM_ROOM_PERP_WALL.toString():
//                    changeSymType(Symulation.SYM_ROOM_PERP_WALL);
//                    break;
//                case Symulation.SYM_PX_VS_P1_W0.toString():
//                    changeSymType(Symulation.SYM_PX_VS_P1_W0);
//                    break;
//                case Symulation.SYM_PX_VS_PX_W0.toString():
//                    changeSymType(Symulation.SYM_PX_VS_PX_W0);
//                    break;
//                case Symulation.SYM_PX_VS_PX_W2.toString():
//                    changeSymType(Symulation.SYM_PX_VS_PX_W2);
//                    break;
//            }
        });
    }

    private void changeSymType(Symulation symType) {
        simLoop.pause();
        btnPauseStart.setText("Start");
        btnNextStep.setDisable(false);

        this.simulationType = symType;
        engine = Initializer.createEngine(this.simulationType, pedestriansFactory);
        map = engine.getEnvironment().getMap();
        List<Pedestrian> pedestrians = engine.getEnvironment().getPedestrians();
        if (pedestrians.size() > 0) {
            destination = engine.getEnvironment().getPedestrians().get(0).getPedestrianInformation().getVariableInformation().getDestinationPoint();
        } else {
            destination = Configuration.DEFAULT_DESTINATION_POSITION;
        }
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
//                drawMap(engine.getEnvironment().getMap());
                drawMap(map);
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
        return value / scaleValue;
    }

    private double scale(double value) {
        return value * scaleValue;
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

    private void drawDestination() {
        double x = scale(destination.getX());
        double y = scale(destination.getY());
        gc.setFill(Color.BLACK);
        gc.fillArc(x, y, 5, 5, 0, 360, ArcType.ROUND);
    }

}
