import java.util.*;
import java.lang.Math.*;
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
    private static int decelerate = 45;

    private static final int APPROACH = 0, LANDING = 1, COUNTER_BURN = 2;
    private static final int MIN_APPROACH_ALTITUDE = 300, MIN_DISTANCE_IN_LANDINGZONE = 500, COUNTER_BURN = 2;

    private static int highestPeak = 0;
    private static int locationHighestPeak = 0;

    private static int landingZoneAltitude;

    private static boolean inApproachParameters() {
        return true;
    }


    // todo check for the highest peak
    // todo check for  the height of the landing
    // todo  hover logic
    // todo


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        int landXn = 0;
        int landYn = 0;
        int direction = 0;
        int thrust = 4;

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
                System.err.println(highestPeak);
                System.err.println(locationHighestPeak);
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

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            int horSpeed = Math.abs(hSpeed); // they were using negative speed, might come in handy.

            determineDirection(hSpeed); // Do not know where we might need this (

            switch (evaluateFlightStage(X, Y, fuel, vSpeed, hSpeed)) {

                case (APPROACH):

                    System.out.println(adjustApproachRotation(X, vSpeed) + " " + adjustApproachThrust(vSpeed));
                    break;
                case (COUNTER_BURN):


                    break;
                case (LANDING):
                    System.out.println(0 + " " + adjustThrust(vSpeed));
                    break;
                default:
                    break;


            }

        }
//
//            if (X < landingZoneRight && X > landingZoneLeft) { // main  control module
//                System.err.println(" in landing mode ");
//
//                if (horSpeed > 7) {
//                    System.err.println(" else in while loop" + direction);
//                    int newDirection = -1 * decelerate;
//                    decelerate = newDirection;
//                    System.out.println(newDirection + " " + adjustThrust(vSpeed));   // updates the horSpeed
//                }
//
//                if (horSpeed <= 7) {
//                    direction = 0;
//                    System.err.println(" landing horSpeed " + horSpeed);
//                    System.out.println(direction + " " + adjustThrust(vSpeed));
////                }
//
//        } else if (X > landingZoneRight) {
//
//            if (horSpeed <= 20) {
//                direction = 45;
//                System.out.println(direction + " " + adjustThrust(vSpeed));
//                System.err.println("right approaching lz " + direction);
//            } else {
//                int decelerate = 0;
//                System.out.println(decelerate + " " + adjustThrust(vSpeed));
//                System.err.println("deceleration  " + direction);
//            }
//
//        } else if (X < landingZoneLeft) { // if the speed has been reached and the distance i
//
//            if (horSpeed <= 20) {
//                direction = -45;
//                System.err.println("left approaching lz " + adjustThrust(vSpeed));
//                System.out.println(direction + " " + adjustThrust(vSpeed));
//
//            } else {
//                int decelerate = 0;
//                System.err.println("left correcting " + direction);
//                System.out.println(decelerate + " " + adjustThrust(vSpeed));
//            }
//        }
//    }
//

    }

    private static int adjustApproachRotation(int X,int vSpeed ) {
        if (X > landingZoneRight) {

            if (Math.abs(vSpeed) <= 20) {
               return 45;
            } else {
              return 0;
            }
        } else if (X < landingZoneLeft) {
            if (Math.abs(vSpeed) <= 20) {
              return -45;
            } else {
            return 0;
            }
        }


        return 0;
    }

    private static Object adjustApproachThrust(int vSpeed) {
        return 4;
    }


    private static void determineDirection(int hSpeed) {
        if (hSpeed > 0) {
            System.err.println("moving right");
        } else {
            System.err.println("moving left");
        }
    }

    private static int adjustThrust(int vSpeed) {
        if (vSpeed > 39) {
            return 4;
        }
        return 3;
    }

    private static int evaluateFlightStage(int shipPositionX, int shipAltitudeY, int fuel, int vSpeed, int hSpeed) {

        if (shipPositionX < landingZoneRight && shipPositionX > landingZoneLeft && safeToLand(vSpeed, hSpeed)) { // main
            return LANDING;
        } else if (shipPositionX < landingZoneLeft || shipPositionX > landingZoneRight && safeApproach(vSpeed,
                shipAltitudeY, shipPositionX, fuel)) {
            return APPROACH;
        }
        return COUNTER_BURN;
    }

    private static boolean safeApproach(int vSpeed, int shipAltitudeY, int shipPositionX, int fuel) {
        int requiredMinimumVerticalSpeed = determineMinimumVerticalSpeed(shipPositionX, fuel);
        int requiredAltitudeToReachLandingZone = determineAltitudeToReachLandingZone();
        return (vSpeed >= requiredMinimumVerticalSpeed) && (shipAltitudeY >= requiredAltitudeToReachLandingZone);
    }

    private static int determineMinimumVerticalSpeed(int shipPositionX, int fuel) {
        int flightTimeWithMaximumTrust = fuel / 4;
        return (landingZoneLeft + ((landingZoneRight - landingZoneLeft) / 2) - shipPositionX) / flightTimeWithMaximumTrust;
    }

    private static int determineAltitudeToReachLandingZone() {
        return highestPeak + MIN_APPROACH_ALTITUDE;
    }

    private static boolean safeToLand(int vSpeed, int hSpeed) {
        return Math.abs(vSpeed) < 39 && Math.abs(hSpeed) < 7;
    }
}
