import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Player {
    private static int landingZoneLeft = 0;
    private static int landingZoneRight = 0;
    private static int decelerate = 45;

    private static int landingZoneAltitude;
    private static boolean overLandingZone = false;

    private static boolean inApproachParameters() {
        return true;
    }




    //check for the highest peak
    //check for  the height of the landing
    //hover logic
    //


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

                System.err.println(landingZoneLeft);
                System.err.println(landingZoneRight);
            }

            landXn = landX;
            landYn = landY; // landYn is the height of the LZ
            landingZoneAltitude = landYn;

        }


        // game loop

        while (true) {


            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            int horSpeed = Math.abs(hSpeed); // they were using negative speed, might come in handy.

            determineDirection(hSpeed); // Do not know where we might need this

            if (X < landingZoneRight && X > landingZoneLeft) {
                System.err.println(" in landing mode ");

                if (horSpeed > 7) {
                    System.err.println(" else in while loop" + direction);
                    int newDirection = -1 * decelerate;
                    decelerate = newDirection;
                    System.out.println(newDirection + " " + adjustThrust(vSpeed));   // updates the horSpeed
                }

                if (horSpeed <= 7) {
                    direction = 0;
                    System.err.println(" landing horSpeed " + horSpeed);
                    System.out.println(direction + " " + adjustThrust(vSpeed));
                }

            } else if (X > landingZoneRight) {

                if (horSpeed <= 20) {
                    direction = 20;
                    System.out.println(direction + " " + adjustThrust(vSpeed));
                    System.err.println("right approaching lz " + direction);
                } else {
                    int decelerate = -35;
                    System.out.println(decelerate + " " + adjustThrust(vSpeed));
                    System.err.println("deceleration  " + direction);
                }

            } else if (X < landingZoneLeft) {

                if (horSpeed <= 20) {
                    direction = -20;
                    System.err.println("left approaching lz " + adjustThrust(vSpeed));
                    System.out.println(direction + " " + adjustThrust(vSpeed));

                } else {
                    int decelerate = 35;
                    System.err.println("left correcting " + direction);
                    System.out.println(decelerate + " " + adjustThrust(vSpeed));
                }
            }
        }
    }


    private static void determineDirection(int hSpeed) {
        if (hSpeed > 0) {
            System.err.println("moving right");
        } else {
            System.err.println("moving left");
        }
    }

    private static int adjustThrust(int vSpeed) {
        if (Math.abs(vSpeed) > 37) {
            return 4;
        } else {
            return 3;
        }
    }

    private static void getLandingZoneAltitude()




}
