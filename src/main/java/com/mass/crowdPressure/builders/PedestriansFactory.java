package com.mass.crowdPressure.builders;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.Configuration;
import com.mass.crowdPressure.Symulation;
import com.mass.crowdPressure.model.Environment;
import com.mass.crowdPressure.model.Position;
import com.mass.crowdPressure.model.pedestrian.Pedestrian;
import com.mass.crowdPressure.model.pedestrian.PedestrianInformation;
import com.mass.crowdPressure.model.pedestrian.StaticInformation;
import com.mass.crowdPressure.model.pedestrian.VariableInformation;

import java.util.Random;

public class PedestriansFactory {
    private static final COD cod = CODFactory.getCOD();
    private static int ID = 0;

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
        } else if (sym.equals(Symulation.SYM_PX_VS_PX_W0)) {
            addPedestrainsGroupAndOnePedestrian(environment);
        } else if (sym.equals(Symulation.SYM_P0_W1)) {
            // none
        }
    }

    private void addPedestrainsGroupAndOnePedestrian(Environment environment) {
        Position pointL = new Position(20, 50);
        Position pointR = new Position(80, 50);

        int groupCount = 20;
        addGroup(environment, pointL, pointR, groupCount);
        environment.getPedestrians().add(createPedestrian(ID++,environment,pointR,pointL));
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
            environment.getPedestrians().add(createPedestrian(ID++, environment, destinationPointL, positionR));

        }
    }


    private void addPedestrians1(Environment environment, int initNoPedestrians) {
        Position destinationPoint = new Position(5, 5);
        Position position = new Position(0, 0);
        for (int id = 0; id < initNoPedestrians; id++) {
            environment.getPedestrians().add(createPedestrian(ID++, environment, destinationPoint, position));
        }
    }

    public void addPedestrian(Environment environment, Position position, Position destiantionPosition) {
        if (destiantionPosition == null) {
            destiantionPosition = Configuration.DEFAULT_DESTINATION_POSITION;
        }
        environment.getPedestrians().add(createPedestrian(ID++, environment, destiantionPosition, position));
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
