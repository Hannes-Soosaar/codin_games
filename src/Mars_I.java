import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * Need to copy-paste to coding Games to make it work.
 *
 **/

class Player {

    private final static String LOWER_SHIP = "0 0";
    private final static String LEVEL_SHIP = "0 4";

    public static boolean safeToLand(int vSpeed){
        return vSpeed < -39;
    }


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
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
            System.err.println( "vSpeed out of If " + vSpeed);
            // 2 integers: rotate power. rotate is the desired rotation angle (should be 0 for level 1), power is the desired thrust power (0 to 4).
            if(safeToLand(vSpeed)){
                System.out.println(LEVEL_SHIP);
            } else{
                System.out.println(LOWER_SHIP);
            }



        }
    }
}