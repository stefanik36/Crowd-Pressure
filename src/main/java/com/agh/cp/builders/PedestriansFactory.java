package com.agh.cp.builders;

import com.agh.cp.model.Environment;
import com.agh.cp.model.pedestrian.Pedestrian;
import com.agh.cp.model.pedestrian.PedestrianInformation;
import com.agh.cp.model.pedestrian.StaticInformation;
import com.agh.cp.model.pedestrian.VariableInformation;
import com.app.COD;
import com.app.CODFactory;
import com.agh.cp.Configuration;
import com.agh.cp.Symulation;
import com.agh.cp.model.Position;

import java.util.Random;

public class PedestriansFactory {
    private static final COD cod = CODFactory.getCOD();
    private int id;

    public PedestriansFactory(){
        id = 0;
    }


    public void addPedestrians(Environment environment, Symulation sym) {
        if (sym.equals(Symulation.SYM_P1_W1)) {
            addPedestrians1(environment, 1);
        } else if (sym.equals(Symulation.SYM_P1_W2)) {
            addPedestrians2(environment, 1);
        } else if (sym.equals(Symulation.SYM_P2_W0)) {
            addPedestrian(environment, new Position(30.0, 5.0), null);
            addPedestrian(environment, new Position(30.5, 30.0), null);
        } else if (sym.equals(Symulation.SYM_PX_VS_PX_W0)) {
            addTwoPedestrainsGroups(environment);
        } else if (sym.equals(Symulation.SYM_PX_VS_P1_W0)) {
            addPedestrainsGroupAndOnePedestrian(environment);
        } else if (sym.equals(Symulation.SYM_PX_VS_PX_W2)) {
            addTwoPedestrainsGroups(environment);
        } else if (sym.equals(Symulation.SYM_P0_W1)) {
            // none
        }
    }

    private void addPedestrainsGroupAndOnePedestrian(Environment environment) {
        Position pointL = new Position(20, 50);
        Position pointR = new Position(80, 50);

        int groupCount = 20;
        addGroup(environment, pointL, pointR, groupCount);
        environment.getPedestrians().add(createPedestrian(id++,environment,pointL,pointR));
    }

    private void addTwoPedestrainsGroups(Environment environment) {
        Position destinationPointL = new Position(20, 50);
        Position destinationPointR = new Position(80, 50);

        int groupCount = 20;
        addGroup(environment, destinationPointL, destinationPointR, groupCount);
        addGroup(environment, destinationPointR, destinationPointL, groupCount);
    }

    private void addGroup(Environment environment, Position position, Position destinationPointL, int groupCount) {
        Random r = new Random();
        for (int i = 0; i < groupCount; i++) {
            int rx = r.nextInt(10) - 5;
            int ry = r.nextInt(10) - 5;

            Position positionR = new Position(position.getX() + rx, position.getY() + ry);
            environment.getPedestrians().add(createPedestrian(id++, environment, destinationPointL, positionR));

        }
    }


    private void addPedestrians1(Environment environment, int initNoPedestrians) {
        Position destinationPoint = new Position(5, 5);
        Position position = new Position(0, 0);
        for (int id = 0; id < initNoPedestrians; id++) {
            Pedestrian newP = createPedestrian(this.id++, environment, destinationPoint, position);
            environment.getPedestrians().add(newP);
        }
    }

    public Pedestrian addPedestrian(Environment environment, Position position, Position destiantionPosition) {
        if (destiantionPosition == null) {
            destiantionPosition = Configuration.DEFAULT_DESTINATION_POSITION;
        }
        Pedestrian newP = createPedestrian(id++, environment, destiantionPosition, position);
        environment.getPedestrians().add(newP);
        return newP;
    }

    public void addPedestrians2(Environment environment, int initNoPedestrians) {
        double visionCenter = 0.25;
        Position destinationPoint = new Position(23, 22);
        Position position = new Position(13, 5);
        for (int id = 0; id < initNoPedestrians; id++) {
            environment.getPedestrians()
                    .add(createPedestrian(id, environment, visionCenter, destinationPoint, position));
        }
    }

    private Pedestrian createPedestrian(int id, Environment environment, Position destinationPoint, Position position) {

        StaticInformation staticInformation = new StaticInformation(id, Configuration.DEFAULT_PEDESTRIAN_MASS,
                Configuration.DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, Configuration.DEFAULT_PEDESTRIAN_VISION_ANGLE,
                Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, Configuration.DEFAULT_PEDESTRIAN_RELAXATION_TIME);

        VariableInformation variableInformation = new VariableInformation(destinationPoint, position);

        PedestrianInformation pedestrianInformation = new PedestrianInformation(staticInformation, variableInformation);

        return new Pedestrian(pedestrianInformation, environment);
    }

    private Pedestrian createPedestrian(int id, Environment environment, double visionCenter, Position destinationPoint,
                                        Position position) {

        StaticInformation staticInformation = new StaticInformation(id, Configuration.DEFAULT_PEDESTRIAN_MASS,
                Configuration.DEFAULT_PEDESTRIAN_COMFORTABLE_SPEED, Configuration.DEFAULT_PEDESTRIAN_VISION_ANGLE,
                Configuration.DEFAULT_PEDESTRIAN_HORIZON_DISTANCE, Configuration.DEFAULT_PEDESTRIAN_RELAXATION_TIME);

        VariableInformation variableInformation = new VariableInformation(visionCenter, destinationPoint, position);

        PedestrianInformation pedestrianInformation = new PedestrianInformation(staticInformation, variableInformation);

        return new Pedestrian(pedestrianInformation, environment);
    }

}
