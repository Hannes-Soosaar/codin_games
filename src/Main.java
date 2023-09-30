import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * tick is 1s
 * <p>
 * thrust level 4 is 4m/s2 meaning every second the speed is reduced by 0.289 m/2
 * thrust level 3 is 3 m/2 meaning every second the speed is increase by 0.711 m/2
 * thrust  level 2 is 2 m/m2 meaning every second the speed is increased by 1.711m/2
 * thrust level 1 is 1m/s2 meaning every second the speed is increased by 2.711 m2
 * thrust level 0 is free fall meaning every second the speed is increased by 3.711 m/2
 * <p>
 * to calculate the h component of take the  cos of the rotation  multiplied by the thrust.
 * to calculate  the  vertical component take the sin of to rotation angle  and multiply it by the thrust.
 **/


class Player {
    private static int landingZoneLeft = 0;
    private static int landingZoneRight = 0;
    private static final int APPROACH = 0, LANDING = 1, COUNTER_BURN = 2;
    private static final int MIN_APPROACH_ALTITUDE = 2000;
    private static int highestPeak = 0;
    private static int locationHighestPeak = 0;
    private static int landingZoneAltitude = 0;

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        int landXn = 0;
        int landYn = 0;

        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            if (landYn - landY == 0 && i != 0) {
                landingZoneLeft = landXn;
                landingZoneRight = landX;
                landingZoneAltitude = landYn;
                System.err.println(landingZoneLeft);
                System.err.println(landingZoneRight);
            }
            if (landY > landYn) {
                highestPeak = landY;
                locationHighestPeak = landX;
                System.err.println("Highest Peak" + highestPeak);
            }
            landXn = landX;
            landYn = landY;
        }


        // game loop

        while (true) {

            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt();  // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt();  // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt();    // the quantity of remaining fuel in liters.
            int rotate = in.nextInt();  // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt();   // the thrust power (0 to 4).

            switch (evaluateFlightStage(X, Y, fuel, vSpeed, hSpeed)) {

                case (APPROACH):
                    System.err.println("Approach stage");
                    System.err.println(adjustApproachRotation(X, vSpeed, hSpeed));   // to be removed from prod.
                    System.err.println(adjustApproachThrust(vSpeed, Y, X));          // to be removed from prod.
                    System.out.println(adjustApproachRotation(X, vSpeed, hSpeed) + " " + adjustApproachThrust(vSpeed, Y, X));
                    break;
                case (COUNTER_BURN):
                    System.err.println("counter stage");
                    System.out.println(counterRotation(Y, hSpeed, X) + " " + adjustApproachThrust(vSpeed, Y, X));   //
                    // rename Y
                    break;
                case (LANDING):
                    System.err.println("Landing stage");
                    System.err.println(adjustLandingThrust(vSpeed));
                    System.out.println(0 + " " + adjustLandingThrust(vSpeed));
                    break;
                default:
                    break;
            }
        }
    }

    private static int counterRotation(int Y, int hSpeed, int X) {

        int directionOfMovement = determineDirection(hSpeed);

        if (Y > determineAltitudeToReachLandingZone(X)) {
            return -35 * directionOfMovement;
        } else if (Y < determineAltitudeToReachLandingZone(X)) {
            return -19 * directionOfMovement;
        } else if (Math.abs(hSpeed) < 7) {
            return 0;
        }
        return 0;
    }

    private static int adjustApproachRotation(int X, int vSpeed, int hSpeed) {


        if (X > landingZoneRight) {
            if (Math.abs(vSpeed) <= 39 && Math.abs(hSpeed) < 20) {
                return 19;
            }
        } else if (X < landingZoneLeft) {
            if (Math.abs(vSpeed) <= 39 && Math.abs(hSpeed) < 20) {
                return -19;
            }
        }
        return 0;
    }

    private static Object adjustApproachThrust(int vSpeed, int shipAltitude, int X) {
        if (Math.abs(vSpeed) > 39 || shipAltitude < determineAltitudeToReachLandingZone(X)) {
            return 4;
        }
        return 3;
    }

    private static int determineDirection(int hSpeed) {
        if (hSpeed > 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private static int adjustLandingThrust(int vSpeed) {
        if (Math.abs(vSpeed) > 38) {
            return 4;
        } else return 3;
    }

    private static int evaluateFlightStage(int shipPositionX, int shipAltitudeY, int fuel, int vSpeed, int hSpeed) {
        System.err.println("Evaluate Flight Stage");
        System.err.println("vSpeed: " + vSpeed);
        System.err.println("hSpeed: " + hSpeed);
        System.err.println(safeToLand(vSpeed, hSpeed));
        if (shipPositionX < landingZoneRight && shipPositionX > landingZoneLeft && safeToLand(vSpeed, hSpeed)) { // main
            return LANDING;
        } else if (shipPositionX < landingZoneLeft && Math.abs(hSpeed) < 20
                || shipPositionX > landingZoneRight && Math.abs(hSpeed) < 20) {
            return APPROACH;
        }
        return COUNTER_BURN;
    }

    private static boolean safeApproach(int vSpeed, int shipAltitudeY, int shipPositionX, int hSpeed) {

        int requiredAltitudeToReachLandingZone = determineAltitudeToReachLandingZone(shipPositionX);

        return (Math.abs(vSpeed) < 40 && shipAltitudeY >= requiredAltitudeToReachLandingZone && Math.abs(hSpeed) < 20);
    }

    private static int determineAltitudeToReachLandingZone(int shipPositionX) {

        if (locationHighestPeak > landingZoneRight && shipPositionX > locationHighestPeak) {
            return highestPeak + MIN_APPROACH_ALTITUDE;
        } else if (locationHighestPeak < landingZoneLeft && shipPositionX < locationHighestPeak) {
            return highestPeak + MIN_APPROACH_ALTITUDE;
        }
        return MIN_APPROACH_ALTITUDE + landingZoneAltitude;
    }

    private static boolean safeToLand(int vSpeed, int hSpeed) {
        return Math.abs(vSpeed) < 40 && Math.abs(hSpeed) < 7;
    }
}
