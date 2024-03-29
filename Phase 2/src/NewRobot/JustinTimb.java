package NewRobot;

import javafx.geometry.Pos;
import robocode.Droid;
import robocode.HitRobotEvent;
import robocode.MessageEvent;
import robocode.TeamRobot;
import robocode.util.Utils;

import java.io.IOException;
import java.util.Random;

public class JustinTimb extends TeamRobot implements Droid {
    private double offsetX;
    private double offsetY;

    private String nameL;  // Nome do lider da equipa
    /**
     * run:  Droid's default behavior
     */
    public void run() {
        offsetX = getBattleFieldWidth() / 3;
        offsetY = getBattleFieldHeight() / 3;


        System.out.println("Droid ready.");

    }

    /**
     * onMessageReceived:  What to do when our leader sends a message
     */
    public void onMessageReceived(MessageEvent e) {
        System.out.println("Message: " + e.getClass().getName());
        // Fire at a point
        if (e.getMessage() instanceof DanceOrder) {
            System.out.println("Dance order");

            DanceOrder o = (DanceOrder) e.getMessage();

            System.out.println(o.getX() + ' ' + o.getY());

            //get angle do dance
            double danceAngle = o.getRotation();


            while (this.getX() != o.getX() && this.getY() != o.getY()) {
                goTo(o.getX(), o.getY());
            }

            if (this.getHeading() > 180) {
                this.turnRight(360 - this.getHeading() - danceAngle / 2);
            } else {
                this.turnLeft(this.getHeading() + danceAngle / 2);
            }

            while (this.getX() != o.getX() && this.getY() != o.getY()) {
                goTo(o.getX(), o.getY());
            }

            for (int j = 0; j < 20; j++) {
                doNothing();
            }

            DanceOrder order = new DanceOrder(this.getX(), this.getY(), o.getRotation());
            try {
                //inform team lider the this robot is ready to dance
                System.out.println("SENDING I'M ready");;
                sendMessage("NewRobot.NSYNC", order);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }




        } // Set our colors
        else if (e.getMessage() instanceof RobotColors) {
            RobotColors c = (RobotColors) e.getMessage();

            setBodyColor(c.bodyColor);
            setGunColor(c.gunColor);
            setRadarColor(c.radarColor);
            setScanColor(c.scanColor);
            setBulletColor(c.bulletColor);
        }

        else if (e.getMessage() instanceof Double || e.getMessage() instanceof Integer) {
            System.out.println("I'm going to dance!");
            double rotation = (Double) e.getMessage();
            dance(rotation);

            goTo(getNewPos().getX(), getNewPos().getY());
            turnRight(this.getHeading());
            try {
                sendMessage("NewRobot.NSYNC","ready");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        // Recebe o nome do seu lider
        else  if (e.getMessage() instanceof SendName) {
            System.out.println("[MSG] Recebi nome do Lider");
            SendName n = (SendName) e.getMessage();

            this.nameL = n.getName();

            // Enviar o seu nome ao lider.
            try {
                sendMessage(this.nameL, new SendName(getName()));
            } catch (IOException a) {
                a.printStackTrace();
            }
        }

    }

    public void dance(double danceAngle) {
        //Dance like the win dance
        for (int i = 0; i < 10; i++) {
            turnRight(danceAngle);
            turnLeft(danceAngle);
        }
    }

    public Position getNewPos(){

        double x = Math.floor(this.getX());
        double y = Math.floor(this.getY());
        double roundedOffsetX = Math.floor(offsetX);
        double roundedOffsetY = Math.floor(offsetY);

        Position newPos = new Position();

        Position pos1 = new Position(roundedOffsetX, roundedOffsetY);
        Position pos2 = new Position(Math.floor(getBattleFieldWidth() / 6), Math.floor(getBattleFieldHeight() / 2));
        Position pos3 = new Position(roundedOffsetX, roundedOffsetY * 2);
        Position pos4 = new Position(Math.floor(getBattleFieldWidth() / 2), Math.floor(getBattleFieldHeight() * 5 / 6));
        Position pos5 = new Position(roundedOffsetX * 2, roundedOffsetY * 2);
        Position pos6 = new Position(Math.floor(getBattleFieldWidth() * 5 / 6), Math.floor(getBattleFieldHeight() / 2));
        Position pos7 = new Position(roundedOffsetX * 2, roundedOffsetY);
        Position pos8 = new Position(Math.floor(getBattleFieldWidth() / 2), Math.floor(getBattleFieldHeight() / 6));


        if (x >= pos4.getX() - 20 && y >= pos4.getY() - 20) {
            System.out.println("go to pos 5");
            return pos5;
        }
        else if (x >= pos5.getX() - 20 && y >= pos5.getY() - 20) {
            System.out.println("go to pos 6");
            return pos6;
        }
        else if (x >= pos3.getX() - 20 && y >= pos3.getY() - 20) {
            System.out.println("go to pos 4");
            return pos4;
        }
        else if (x >= pos6.getX() - 20 && y >= pos6.getY() - 20) {
            System.out.println("go to pos 7");
            return pos7;
        }
        else if (x >= pos2.getX() && x < pos1.getX() && y >= pos2.getY()) {
            System.out.println("go to pos 3");
            return pos3;
        }
        else if (x >= pos7.getX() - 20 && y >= pos7.getY() - 20) {
            System.out.println("go to pos 8");
            return pos8;
        }
        else if (x >= pos1.getX() - 5 && y >= pos1.getY() - 5 && y < pos2.getY()) {
            System.out.println("go to pos 2");
            return pos2;
        }


        else if (x >= pos8.getX() - 20 && y >= pos8.getY() - 20) {
            System.out.println("go to pos 1");
            return pos1;
        }

        return newPos;
    }

    public void goTo(double newX, double newY) {
        double dx = newX - this.getX();
        double dy = newY - this.getY();
        // Calculate angle to face the robot's body to go to the destination
        double theta = Math.toDegrees(Math.atan2(dx, dy));

        // Turn body to the destination angle
        turnRight(Utils.normalRelativeAngleDegrees(theta - getGunHeading()));

        //calculate distance to move and go
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        ahead(distance);

        if(this.getX() != newX && this.getY() != newY){
            turnLeft(40);
            back(20);
        }
        turnLeft(this.getHeading());
    }

    public void onHitRobot(HitRobotEvent e) {
        //System.out.println("Ups! Colision with another robot! " + e.getName());
        turnLeft(10);
        back(100);

    }
}
