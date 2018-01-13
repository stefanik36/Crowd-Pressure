package com.mass.crowdPressure.calculators;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

import com.app.COD;
import com.app.CODFactory;
import com.mass.crowdPressure.Configuration;
import com.mass.crowdPressure.calculators.figures.LinePointAngle;
import com.mass.crowdPressure.calculators.figures.LineTwoPoints;
import com.mass.crowdPressure.calculators.figures.Vector;
import com.mass.crowdPressure.calculators.figures.VectorXY;
import com.mass.crowdPressure.exceptions.AngleOutOfRangeException;
import com.mass.crowdPressure.model.Position;

public class GeometricCalculator {

    public static final BiFunction<LinePointAngle, LineTwoPoints, Optional<Position>> crossPointTwoLines = (
            LinePointAngle lpa, LineTwoPoints ltp) -> {

        // TODO alpha =0.5 or 1.5
        // TODO inf points
        double tan = Math.tan(lpa.getAngle() * Math.PI);
        double xi = lpa.getPoint().getX();
        double yi = lpa.getPoint().getY();
        double xp1 = ltp.getStartPosition().getX();
        double yp1 = ltp.getStartPosition().getY();
        double xp2 = ltp.getEndPosition().getX();
        double yp2 = ltp.getEndPosition().getY();

        double numerator = -xi * xp1 * tan + xi * xp2 * tan + yi * xp1 - yi * xp2 - xp1 * yp2 + yp1 * xp2;
        double denominator = -xp1 * tan + yp1 + xp2 * tan - yp2;
        double x = numerator / denominator;
        double y = tan * x + yi - tan * xi;
        // cod.i("ANGLE: " + lpa.getAngle() + " wall: POS: ", Arrays.asList(ltp, new
        // Position(x, y)));

        if (checkIfPointIsInBetween(new Position(x, y), new Position(xp1, yp1), new Position(xp2, yp2))) {
            // cod.i("HERE CROST POINT: " + lpa.getAngle(), new Position(x, y));
            // if (((x >= xp1 && x <= xp2) || (x >= xp2 && x <= xp1)) && ((y >= yp1 && y <=
            // yp2) || (y >= xp2 && y <= xp1))) {
            if ((lpa.getAngle() < 1 && y > yi) || (lpa.getAngle() > 1 && y < yi)
                    || (lpa.getAngle() == 1 && y == yi && x > xi) || (lpa.getAngle() == 0 && y == yi && x < xi))
                return Optional.of(new Position(x, y));
        }
        return Optional.empty();
    };
    private final static COD cod = CODFactory.setLevelOfDepression(2);
    public static BiFunction<Position, Position, Double> distance = (Position a, Position b) -> {
        return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
    };

    ;
    public static BiFunction<Position, Position, Double> calculateAngle = (Position pos, Position destinationPoint) -> {
        // cod.i("POSITIONS: ", Arrays.asList(pos, destinationPoint));
        double result = (pos.getY() - destinationPoint.getY()) / (pos.getX() - destinationPoint.getX());
        // cod.i("calcAng: ", result);
        result = Math.atan(result) / Math.PI;
        if (result < 0) {
            result = 1 + result;
        }
        if (pos.getY() >= destinationPoint.getY()) {
            if ((pos.getX() >= destinationPoint.getX()) || (result != 0)) {
                result = result + 1;
            }
        }
        return result;
    };

    ;
    public static BiFunction<Position, LineTwoPoints, Optional<Vector>> vectorStraightPoint = (Position p,
                                                                                               LineTwoPoints l) -> {
        double x1 = l.getStartPosition().getX();
        double y1 = l.getStartPosition().getY();
        double x2 = l.getEndPosition().getX();
        double y2 = l.getEndPosition().getY();
        double a = y2 - y1;
        double b = x1 - x2;
        double c = x2 * y1 - y2 * x1;
        double x0 = p.getX();
        double y0 = p.getY();
        double denominator = (Math.pow(a, 2) + Math.pow(b, 2));
        double x = (b * (b * x0 - a * y0) - a * c) / denominator;
        double y = (a * (-b * x0 + a * y0) - b * c) / denominator;
        // cod.i("poi: ",Arrays.asList(p,l));
        // cod.i("xy: ",Arrays.asList(x,y));

        if (checkIfPointIsInBetween(new Position(x, y), new Position(x1, y1), new Position(x2, y2))) {
            // double result = Math.abs(a * x0 + b * y0 + c) / Math.sqrt(denominator);
            Vector result = changeVector(vectorFromTwoPoints(new Position(x, y), new Position(x0, y0)));
            // cod.i(result);
            return Optional.of(result);
        }
        return Optional.empty();
    };
    public static BiFunction<Double, Double, Boolean> isBigger = (Double d1, Double d2) -> {
        return d1 < d2;
    };

    public static final VectorXY changeVector(Vector v) throws AngleOutOfRangeException {
        Double angle = v.getAngle();
        double pi = Math.PI;
        Double alpha = angle * Math.PI;
        double value = v.getValue();

        //TODO
        if (angle == 2.0) {
            angle = 0.0;
        }
        if (angle < 0.5) {
            return new VectorXY(value * Math.cos(alpha), value * Math.sin(alpha));
        } else if (angle == 0.5) {
            return new VectorXY(0, value);
        } else if (angle <= 1.0) {
            return new VectorXY(-value * Math.cos(pi - alpha), value * Math.sin(pi - alpha));
        } else if (angle <= 1.5) {
            return new VectorXY(-value * Math.cos(alpha - pi), -value * Math.sin(alpha - pi));
        } else if (angle == 1.5) {
            return new VectorXY(0, -value);
        } else if (angle < 2.0) {
            return new VectorXY(value * Math.cos(2 * pi - alpha), -value * Math.sin(2 * pi - alpha));
        } else if (angle.isNaN()) {
            return new VectorXY(0, 0);
        }
        // cod.i("CH vector:",v);
        throw new AngleOutOfRangeException();
    }

    public static final Vector changeVector(VectorXY v) {
        double x = v.getX();
        double y = v.getY();
        return new Vector(calculateAngle.apply(new Position(0, 0), new Position(x, y)),
                Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));

    }

    // public static double AngleThreepoints(Position a, Position b, Position c) {
    //
    // }

    public static VectorXY vectorFromTwoPoints(Position start, Position end) {
        return new VectorXY(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public static boolean checkIfPointIsInBetween(Position checking, Position pA, Position pB) {
        double x = checking.getX();
        double y = checking.getY();
        double x1 = pA.getX();
        double y1 = pA.getY();
        double x2 = pB.getX();
        double y2 = pB.getY();

        return (((x1 - Configuration.PRECISION_OF_CALCULATIONS <= x
                && x <= x2 + Configuration.PRECISION_OF_CALCULATIONS)
                || (x2 - Configuration.PRECISION_OF_CALCULATIONS <= x
                && x <= x1 + Configuration.PRECISION_OF_CALCULATIONS))
                && ((y1 - Configuration.PRECISION_OF_CALCULATIONS <= y
                && y <= y2 + Configuration.PRECISION_OF_CALCULATIONS)
                || (y2 - Configuration.PRECISION_OF_CALCULATIONS <= y
                && y <= y1 + Configuration.PRECISION_OF_CALCULATIONS)));
    }

    public static VectorXY subtractVectors(VectorXY v1, VectorXY v2) {
        // cod.i("SUBTRACT ", Arrays.asList(v1, v2));
        return new VectorXY(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    ;

    public static Vector subtractVectors(Vector v1, Vector v2) throws AngleOutOfRangeException {
        // cod.i(Arrays.asList(v1,v2));
        return changeVector(subtractVectors(changeVector(v1), changeVector(v2)));
    }

    ;

    public static VectorXY addVectors(VectorXY v1, VectorXY v2) {
        return new VectorXY(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    ;

    public static Vector addVectors(Vector v1, Vector v2) throws AngleOutOfRangeException {
        // cod.i("addVectors: ",Arrays.asList(v1,v2));
        return changeVector(addVectors(changeVector(v1), changeVector(v2)));
    }

    ;

}
